package congjun.wang.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class CodeUtils {

    private static String UTF8 = "UTF-8";

    /**
     * 编码规则中的数据
     * @param str
     * @param codeFormat
     * @return
     */
    public static String rulerEncode(String str,String codeFormat){
        if(str==null){
            return null;
        }
        String encode = null;
        try {
            encode = URLEncoder.encode(str, codeFormat);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }

    /**
     * 编码规则中的数据 以UTF-8编码
     * @param str
     * @return
     */
    public static String rulerEncodeUtf8(String str){
        String encode = null;
        try {
            encode = URLEncoder.encode(str, UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }

    /**
     * 解码规则中的数据
     * @param str
     * @param codeFormat
     * @return
     */
    public static String rulerDecode(String str,String codeFormat){
        if(str==null){
            return null;
        }
        String decode = null;
        try {
            decode = URLDecoder.decode(str, codeFormat);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decode;
    }

    /**
     * 解码规则中的数据 以UTF-8
     * @param str
     * @return
     */
    public static String rulerDecodeUtf8(String str){
        String decode = null;
        try {
            decode = URLDecoder.decode(str, UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decode;
    }

    /**
     * 编码map中的数据
     * @param map
     * @param codeFormat
     * @return
     */
    public static Map<String,String> rulerEncodeMap(Map<String,String> map, String codeFormat){
        Map<String,String> result = new HashMap<>();
        map.forEach((k,v)->result.put(rulerEncode(k,codeFormat),rulerEncode(v,codeFormat)));
        return result;
    }

    /**
     * 以UTF-8编码map中的数据
     * @param map
     * @return
     */
    public static Map<String,String> rulerEncodeMap(Map<String,String> map){
        Map<String,String> result = new HashMap<>();
        map.forEach((k,v)->result.put(rulerEncode(k,UTF8),rulerEncode(v,UTF8)));
        return result;
    }

    /**
     * 解码map中的数据
     * @param map
     * @param codeFormat
     * @return
     */
    public static Map<String,String> rulerDecodeMap(Map<String,String> map, String codeFormat){
        Map<String,String> result = new HashMap<>();
        map.forEach((k,v)->result.put(rulerDecode(k,codeFormat),rulerDecode(v,codeFormat)));
        return result;
    }

    /**
     * 以UTF-8解码map中的数据
     * @param map
     * @return
     */
    public static Map<String,String> rulerDecodeMap(Map<String,String> map){
        Map<String,String> result = new HashMap<>();
        map.forEach((k,v)->result.put(rulerDecode(k,UTF8),rulerDecode(v,UTF8)));
        return result;
    }

    /**
     * 编码jsonMap数据
     * @param jsonMap
     * @param codeFormat
     * @return
     */
    public static String rulerEncodeJson(String jsonMap, String codeFormat){
        Map<String ,String> map = JsonUtils.jsonToPojo(jsonMap,HashMap.class);
        Map<String,String> result = rulerEncodeMap(map,codeFormat);
        return JsonUtils.objectToJson(result);
    }

    /**
     * 以UTF-8编码json数据
     * @param jsonMap
     * @return
     */
    public static String rulerEncodeJson(String jsonMap){
        Map<String ,String> map = JsonUtils.jsonToPojo(jsonMap,HashMap.class);
        Map<String,String> result = rulerEncodeMap(map);
        return JsonUtils.objectToJson(result);
    }

    /**
     * 解码json数据
     * @param json
     * @param codeFormat
     * @return
     */
    public static String rulerDecodeJson(String json, String codeFormat){
        Map<String,String> map = JsonUtils.jsonToPojo(json, HashMap.class);
        Map<String,String> result = rulerDecodeMap(map,codeFormat);
        return JsonUtils.objectToJson(result);
    }

    /**
     * 以UTF-8解码json数据
     * @param json
     * @return
     */
    public static String rulerDecodeJson(String json){
        Map<String,String> map = JsonUtils.jsonToPojo(json, HashMap.class);
        Map<String,String> result = rulerDecodeMap(map);
        return JsonUtils.objectToJson(result);
    }


}
