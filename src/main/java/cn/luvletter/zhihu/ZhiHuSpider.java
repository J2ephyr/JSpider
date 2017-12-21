package cn.luvletter.zhihu;

import cn.luvletter.common.DownloadImage;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.*;
import java.util.*;

/**
 * @author Zephyr Ji
 * @ Description: TODO
 * @ Date 2017/12/20
 */
public class ZhiHuSpider implements PageProcessor{

    private static final Logger log=Logger.getLogger(ZhiHuSpider.class);

    private Set<String> nameSet=new HashSet<String>();

    private static String filePath;


    private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(500).setTimeOut(3 * 60 * 1000)

            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36")

            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")

            .setCharset("UTF-8");


    @Override
    public void process(Page page) {
        log.info("Start DownloadPage:"+page.getUrl().toString());

        String name=page.getHtml().xpath("span[@class='ProfileHeader-name']/text()").toString();

        if(name==null||"".equals(name)){
            page.setSkip(true);
            log.info("Skip this page:"+page.getUrl().toString());
        }

        if(!nameSet.contains(name)){

            nameSet.add(name);

            page.putField("name",name);

            log.info("Append Name:-------"+name);

            String headImg=page.getHtml().xpath("img[@class='Avatar Avatar--large UserAvatar-inner']/@src").toString();

            page.putField("headImg",headImg);

            log.info("Append headImg:-----"+headImg);

            page.putField("url",page.getUrl().toString());

            try {
                DownloadImage.download(headImg,name+".jpg",filePath);

                log.info(name+"  Has been downloaded!");
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Download img error，name:"+name);
            }

        }

        page.addTargetRequests(page.getHtml().xpath("a[@class='Button NumberBoard-item Button--plain']/@href").all());
        page.addTargetRequests(page.getHtml().xpath("a[@class='UserLink-link']/@href").all());

    }


    @Override
    public Site getSite() {

        return site;

    }



    public static void main(String[] args) {

        System.out.println("Please enter the save path :");

        Scanner scanner=new Scanner(System.in);
        filePath=scanner.next();
        String txtName=filePath+"\\"+"zhihu.txt";

        File file=new File(txtName);

        if(!file.exists()){
            try {
                file.createNewFile();
                log.info("Create File:"+file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        log.info("----------ZhiHuSpider Start----------");

        Spider.create(new ZhiHuSpider()).

                addUrl("https://www.zhihu.com/people/zephyr-51-53/activities").

                addPipeline(new ZhiHuPipeline(txtName)).

                thread(5).

                run();
    }
}
