package congjun.wang.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PojoUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(PojoUtils.class);

    private static final String YYYYMMddHHmmss = "YYYYMMddHHmmss";
    private static final String YYYY_MM_dd_HH_mm_ss = "YYYY-MM-dd HH:mm:ss";
    private static final String HH_mm_ss = "HH:mm:ss";
    private static final String YYYY_MM_dd = "YYYY-MM-dd";

    public static String date2String(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, pattern);
    }

    public static String date2String(Date date) {
        return date2String(date, YYYY_MM_dd_HH_mm_ss);
    }


    public static Map<String, String> transObj2Map(Object obj) throws Exception {
        Map<String, String> result = new HashMap<>();
        if(obj==null){
            return result;
        }
        Class objClass = obj.getClass();
        result.put("class", objClass.getCanonicalName());
        Field[] fields = objClass.getDeclaredFields();
        for (Field f : fields) {
            String fName = f.getName();
            String fType = f.getType().getCanonicalName();
            Object getResult = invokeGet(obj, fName);
            switch (fType) {
                case "java.lang.String": {
                    if (!StringUtils.isEmpty(getResult))
                        result.put(fName, "'" + getResult.toString() + "'");
                    break;
                }
                case "java.util.Date": {
                    if (!StringUtils.isEmpty(getResult)) {
                        Date date = (Date) getResult;
                        result.put(fName, "'" + date2String(date) + "'");
                    }
                    break;
                }
                default: {
                    if (!StringUtils.isEmpty(getResult))
                        result.put(fName, getResult.toString());
                    break;
                }
            }
        }
        return result;
    }

    public static Object invokeGet(Object obj, String fieldName) throws Exception {
        Class objClass = obj.getClass();
        String mName = "get" + upCaseFirst(fieldName);
        LOGGER.debug("方法名称：" + mName);
        Method declaredMethod = objClass.getDeclaredMethod(mName);
        return declaredMethod.invoke(obj);
    }

    public static String upCaseFirst(String str) {
        String s = str.substring(0, 1).toUpperCase();
        return s + str.substring(1);
    }
}
