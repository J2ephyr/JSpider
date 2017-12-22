package cn.luvletter.wallhalla;

import cn.luvletter.common.DownloadImage;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * @author Zephyr Ji
 * @ Description: TODO
 * @ Date 2017/12/21
 */
public class WallHallaSpider implements PageProcessor{

    private static final Logger log= Logger.getLogger(WallHallaSpider.class);

    private Set<String> nameSet=new HashSet<String>();

    private static String filePath;


    private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(500).setTimeOut(3 * 60 * 1000)

            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36")

            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")

            .addHeader("Accept-Language", "zh-CN,zh;q=0.9")

            .setCharset("UTF-8");


    @Override
    public void process(Page page) {

        List<Selectable> nodes = page.getHtml().xpath("div[@class='thumb-wrap]").nodes();

        List<WallHallaImg> wallHallaImgList=new ArrayList<>();

        for(Selectable selectable : nodes){
            String imgId = selectable.xpath("/div/@data-id").toString();

            if(imgId==null||"".equals(imgId)){
                continue;
            }

            String imgSrc = selectable.xpath("img[@class='thumb-img lazyload']/@src").toString();

            String imgHref = selectable.xpath("a[@class='thumb-a']/@data-src").toString();

            WallHallaImg wallHallaImg=new WallHallaImg()
                    .setImgId(imgId)
                    .setImgHref(imgHref)
                    .setImgSrc(imgSrc);

            log.info("wallhallaImg:"+wallHallaImg.toString());

            try {

                DownloadImage.download(imgSrc,imgId,filePath+"/img");

                log.info("Downloaded:"+imgId);

            } catch (Exception e) {
                e.printStackTrace();
            }

            wallHallaImgList.add(wallHallaImg);
        }

        page.putField("imgList",wallHallaImgList);

        page.addTargetRequest("/random");
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

                log.info("Create File"+txtFile.getAbsolutePath());
            } catch (IOException e) {

                e.printStackTrace();
            }


        }
        Spider.create(new WallHallaSpider())
                .addUrl("https://wallhalla.com")
                .addPipeline(new WallHallaPipeline(txtName))
                .thread(5)
                .run();
    }
}
