package cn.luvletter.wallhalla;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @author Zephyr Ji
 * @ Description: TODO
 * @ Date 2017/12/21
 */
public class WallHallaPipeline implements Pipeline{
    private static final Logger log = Logger.getLogger(WallHallaPipeline.class);

    private String filePath;

    public WallHallaPipeline(String filePath) {
        this.filePath=filePath;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<WallHallaImg> imgList=resultItems.get("imgList");

        if(imgList == null || imgList.size() == 0){
            return ;
        }

        String imgJson=JSON.toJSONString(imgList);

        try {

            FileOutputStream fileOutputStream=new FileOutputStream(filePath,true);

            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fileOutputStream,"UTF-8");

            outputStreamWriter.write(imgJson);

            outputStreamWriter.close();

            log.info("[WRITE IN IMG INFO]");

        } catch (Exception e) {

            log.error(e.getMessage());

            e.printStackTrace();
        }

    }
}
