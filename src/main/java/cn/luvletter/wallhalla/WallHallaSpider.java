package cn.luvletter.wallhalla;

import cn.luvletter.common.DownloadImage;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author Zephyr Ji
 * @ Description: TODO
 * @ Date 2017/12/21
 */
public class WallHallaSpider implements PageProcessor{

    private static final Logger log= Logger.getLogger(WallHallaSpider.class);

    private static String filePath;

    private static final AtomicInteger ID=new AtomicInteger(0);

    private static final AtomicInteger PAGE=new AtomicInteger(1);

    private static final String ROOTURL = "http://wallhalla.com";


    private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(500).setTimeOut(3 * 60 * 1000)
            .setDomain("www.wallhalla.com")

            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0")

            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

            //.addCookie("__cfduid","d53ecc059fb8992dd14ffe8489a5144981513949700")

            //.addCookie("PHPSESSID","uepkls2ohplb6ugk1focepabb1")

            //.addCookie("settings","{\"qf\":[\"anime\"],\"thpp\":32,\"infscroll\":0,\"winline\":1}")

            //.addHeader("Accept-Encoding","gzip, deflate, br")

            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")

            .addHeader("Host","wallhalla.com")

            .addHeader("Referer","https://wallhalla.com/")

            .addHeader("Connection","keep-alive")

            .setCharset("UTF-8");


    @Override
    public void process(Page page) {
        //<-----------------------------下载图片---------------------------------->
        if(page.getUrl().toString().contains("alpha.wallhaven.cc")){
            String imgRealSrc = page.getHtml().xpath("img[@class='wallpaper']/@src").toString();

            if (!"".equals(imgRealSrc)) {
                try {
                    DownloadImage.download(imgRealSrc, getLinkNode(imgRealSrc), filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if(page.getUrl().toString().contains("wall.alphacoders.com")) {
            page.addTargetRequest(page.getHtml().xpath("../img[@class='img-responsive']/@href").toString());

            String imgRealSrc = page.getHtml().xpath("img/@src").toString();

            if (!"".equals(imgRealSrc)) {
                try {
                    DownloadImage.download(imgRealSrc, getLinkNode(imgRealSrc), filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //<-----------------------------壁纸详情：各种尺寸---------------------------------->
        List<Selectable> imgDetailSe = page.getHtml()
                .xpath("div[@class='wall-sources-wrap']/")
                .nodes();
        List<String> imgDetailHrefList = new ArrayList<>();
        //壁纸大小详情
        for(Selectable selectable : imgDetailSe){

            String imgDetailHref = selectable.xpath("/a/@href").toString();

            if(imgDetailHref==null||"".equals(imgDetailHref)){
                continue;
            }

            String imgDetailId = selectable.xpath("/a/@data-id").toString();
            String imgSize = selectable.xpath("div[@class='info-reso']/text()").toString();
            WallHallaImgDetail wallHallaImgDetail=new WallHallaImgDetail();
            wallHallaImgDetail
                    .setImgDetailId(imgDetailId)
                    //imgId:zqZXpw1OFzeM
                    .setImgId(getImgId(page.getUrl().toString()))
                    .setImgSize(imgSize);
            //壁纸详情下载页面链接，如：2560*1440 的壁纸 /out/zqZXpw1OFzeM_GETJ7XK
            imgDetailHrefList.add(imgDetailHref);
        }

        page.addTargetRequests(imgDetailHrefList);


        List<Selectable> nodes = page.getHtml().xpath("div[@class='thumb-wrap']").nodes();

        List<WallHallaImg> wallHallaImgList=new ArrayList<>();
        List<String> imgHrefList = new ArrayList<>();
        for(Selectable selectable : nodes){
            String imgId = selectable.xpath("/div/@data-id").toString();

            if(imgId==null||"".equals(imgId)){
                continue;
            }

            String imgSrc = ROOTURL+selectable.xpath("img[@class='thumb-img lazyload']/@data-src").toString();

            String imgColor = selectable.xpath("/div/@data-colors").toString();

            String imgSource = selectable.xpath("/div/@data-sources").toString();

            String imgTags = selectable.xpath("/div/@data-tags").toString();

            String imgWH = selectable.xpath("/div/@data-wh").toString();

            String imgHref = ROOTURL+selectable.xpath("a[@class='thumb-a']/@href").toString();
            //添加壁纸详情的链接：http://wallhalla.com/wallpaper/zqZXpw1OFzeM
            imgHrefList.add(imgHref);
            //壁纸列表
            WallHallaImg wallHallaImg=new WallHallaImg()
                    .setId(String.valueOf(ID.addAndGet(1)))
                    .setImgId(imgId)
                    .setImgColor(imgColor)
                    .setImgSources(imgSource)
                    .setImgTags(imgTags)
                    .setImgWH(imgWH)
                    .setImgHref(imgHref)
                    .setImgSrc(imgSrc);
            log.info("[Read WallhallaImg INFO]:"+wallHallaImg.toString());

            try {

                DownloadImage.download(imgSrc,imgId+".jpg",filePath);

                log.info("Downloaded IMG:"+imgId);

            } catch (Exception e) {

                e.printStackTrace();

                log.error("[Downloaded Error.imgId]:"+imgId+"imgSrc:"+imgSrc+"\n"+e.getMessage());

            }

            wallHallaImgList.add(wallHallaImg);
        }

        page.putField("imgList",wallHallaImgList);

        if(imgHrefList.size()!=0){
            page.addTargetRequests(imgHrefList);
        }

        if(imgDetailHrefList.size()==0&&imgHrefList.size()==0){
            //下一页
            page.addTargetRequest("/best&page="+PAGE.addAndGet(1));
        }

    }
    private String getImgId(String url){
        if("".equals(url)){
            return "";
        }
        String[] detail = url.replace("/out/", "").split("_");
        if(detail.length==0){
            return "";
        }
        return detail[detail.length-1];
    }
    private String getLinkNode(String imgRealSrc) {
        String[] linkNodes = imgRealSrc.replace("https://", "").split("/");

        if(linkNodes.length==0){
            return null;
        }

        return linkNodes[linkNodes.length-1];
    }


    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {

        //Scanner sc=new Scanner(System.in);

        //filePath=sc.next();

        filePath="D:\\webmagic";

        String txtName=filePath+"\\"+"wallhalla.txt";

        File txtFile=new File(txtName);
        if(!txtFile.exists()){

            try {
                txtFile.createNewFile();

                log.info("[Create File]:"+txtFile.getAbsolutePath());

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        filePath+="\\img";

        File imgFile=new File(filePath);
        if(!imgFile.exists()){

            imgFile.mkdirs();

            log.info("[Create File ]:"+imgFile.getAbsolutePath());

        }

        Spider.create(new WallHallaSpider())
                .addUrl("https://wallhalla.com/best&page=1")
                .addPipeline(new WallHallaPipeline(txtName))
                .thread(5)
                .run();
    }
}
