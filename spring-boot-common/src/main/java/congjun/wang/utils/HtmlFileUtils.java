package congjun.wang.utils;

import java.io.File;

public class HtmlFileUtils {
    public static void creatDir(String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
