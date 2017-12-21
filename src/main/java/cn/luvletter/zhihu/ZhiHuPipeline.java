package cn.luvletter.zhihu;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * @author Zephyr Ji
 * @ Description: TODO
 * @ Date 2017/12/21
 */
public class ZhiHuPipeline implements Pipeline {
    private String file;

    public ZhiHuPipeline(String file) {
        this.file = file;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String name=resultItems.get("name");
        String headImg=resultItems.get("headImg");
        String url=resultItems.get("url");
        StringBuffer resultBuff=new StringBuffer();
        if(name==null||"".equals(name)){
            return ;
        }
        resultBuff.append("name:")
                .append(name)
                .append(";")
                .append("headImg:")
                .append(headImg)
                .append(";")
                .append("url:")
                .append(url)
                .append("\n");
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file,true);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(fileOutputStream,"UTF-8");
            outputStreamWriter.write(resultBuff.toString());
            outputStreamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
