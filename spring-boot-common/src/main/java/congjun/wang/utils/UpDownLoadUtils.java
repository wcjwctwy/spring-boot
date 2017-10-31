package congjun.wang.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class UpDownLoadUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(UpDownLoadUtils.class);
    public static void upload(){

    }

    /**
     * 下载文件
     * @param filepath 文件磁盘路径
     * @param filename 文件名称 后缀要带上
     * @param res HttpServletResponse
     */
    public static void download(String filepath, String filename, HttpServletResponse res,boolean isDel){

        if(filename.split("\\.").length!=2){
            LOGGER.error(filename+"文件名不正确有且只有一个后缀名");
        }

        if(filepath.endsWith("/")){
            filepath.substring(0,filepath.length()-2);
        }

        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename="+filename);
        try {
            OutputStream os = res.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            File oriFile = new File(filepath);
            LOGGER.debug("文件大小："+oriFile.length());
            res.setContentLength((int)(oriFile.length()));
            FileInputStream is = new FileInputStream(oriFile);
            BufferedInputStream bis = new BufferedInputStream(is);

            int length = 0;
            byte[] temp = new byte[1 * 1024 * 10];

            while ((length = bis.read(temp)) != -1) {
                bos.write(temp, 0, length);
            }
            bos.flush();
            bis.close();
            bos.close();
            is.close();
            if(isDel){
                oriFile.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("出错了！！");
        }
    }

}
