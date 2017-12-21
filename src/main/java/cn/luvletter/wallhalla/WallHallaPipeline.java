package cn.luvletter.wallhalla;

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

        StringBuffer resultBuff=new StringBuffer();

        for(WallHallaImg wallHallaImg : imgList){

            resultBuff.append(wallHallaImg.toString());

            resultBuff.append("\n");

        }

        try {

            FileOutputStream fileOutputStream=new FileOutputStream(filePath,true);

            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fileOutputStream,"UTF-8");

            outputStreamWriter.write(resultBuff.toString());

            outputStreamWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
