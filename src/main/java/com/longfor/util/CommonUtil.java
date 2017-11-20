package com.longfor.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mac on 17/4/25.
 */
public class CommonUtil {
    public static ExecutorService fixedThreadPool =null;

    public static ExecutorService getThreadPool(){
        if(CommonUtil.fixedThreadPool==null){
            CommonUtil.fixedThreadPool = Executors.newFixedThreadPool(10);
        }
        return CommonUtil.fixedThreadPool;
    }

    /**
     * GSON工具类，用于把对象转为JSON。
     */
    static final Gson GSON;

    static {
        GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
    }


    /**
     * 返回json。
     *
     * @param response
     * @param src
     */
    public static void writerJson(HttpServletResponse response, Object src) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain");
        try {
            PrintWriter out = response.getWriter();
            out.write(GSON.toJson(src));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 将实体或集合转换成json。
     *
     * @param src
     * @return
     */
    public static String toJson(Object src) {
        return GSON.toJson(src);
    }

    /**
     * json转list map
     *
     * @param jsonStr
     * @return
     */
    public static List<Map<String, String>> parseJSON2List(String jsonStr) throws Exception {
        JSONArray jsonArr = JSONArray.fromObject(jsonStr);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Iterator<JSONObject> it = jsonArr.iterator();
        while (it.hasNext()) {
            JSONObject json2 = it.next();
            list.add(fromJson(json2.toString()));
        }
        return list;
    }

    /**
     * json转为map。
     *
     * @param json
     * @return
     */
    public static Map<String, String> fromJson(String json) {
        Map<String, String> jsonMap = null;
        json = json.replaceAll("\r", "").replaceAll("\n", "");
        JSONArray jsonArray = JSONArray.fromObject("[" + json + "]");
        jsonMap = new HashMap<String, String>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
                String key = (String) iter.next();
                String value = jsonObject.get(key).toString();
                jsonMap.put(key, value);
            }
        }
        return jsonMap;
    }

    /**
     * 将json转化为实体POJO
     *
     * @param jsonStr
     * @param obj
     * @return
     */
    public static <T> Object jsonToObject(String jsonStr, Class<T> obj)throws Exception {
        T t = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        t = objectMapper.readValue(jsonStr, obj);
        return t;
    }

    /**
     * 将实体POJO转化为JSON
     *
     * @param obj
     * @return
     * @throws org.json.JSONException
     */
    public static <T> org.json.JSONObject objectToJson(T obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        // Convert object to JSON string
        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return new org.json.JSONObject(jsonStr);
    }

    /**
     * 将json格式封装的列表数据转换成java的List数据
     *
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> obj) {
        try {
            return JSONArray.toList(JSONArray.fromObject(json), obj.newInstance(), new JsonConfig());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回错误信息。
     *
     * @param e
     * @return
     */
    public static String getPrintStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        return str;
    }

    /**
     * 判断字符串是否为空。
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        if (null != str && !"".equals(str.trim())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 读取配置文件。
     *
     * @param inputStream
     * @return
     */
    public static Map<String, String> readProperties(InputStream inputStream) {
        // 生成输入流
        InputStreamReader is = null;
        // 生成properties对象
        Properties properties = new Properties();
        try {
            is = new InputStreamReader(inputStream, "UTF-8");
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String, String> resultMap = new HashMap<String, String>();
        Enumeration en = properties.keys();
        while (en.hasMoreElements()) {
            String key = en.nextElement().toString().trim();
            resultMap.put(key, properties.getProperty(key).trim());
        }
        return resultMap;
    }

    /**
     * 将一个base64转换成图片保存在服务器上。
     *
     * @param base64
     * @param path
     *            是一个文件夹路径
     * @param imgName
     *            图片名字
     * @throws Exception
     */
    public static void decodeBase64ToImage(String base64, String path, String imgName) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            FileOutputStream write = new FileOutputStream(new File(path + imgName));
            byte[] decoderBytes = decoder.decodeBuffer(base64.replace("data:image/jpeg;base64,", ""));
            write.write(decoderBytes);
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成随机名称。
     *
     * @return
     */
    public static String getRandomName() {
        // 生成随机名称
        String nowDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int random = (int) ((Math.random() * 9 + 1) * 100000);
        return nowDate + random;
    }

    /**
     * 算出两个日期里的天数。
     * @param startDate
     * @param endDate
     * @return
     */
    public static long betWeenDays(Date startDate, Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(endDate);
        long time2 = cal2.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return between_days;
    }

    public static Object urlconnector(String urlsrc,String jsonstr){
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        HttpURLConnection connection = null;
        StringBuffer sb = null;
        try {
            // http://192.168.10.14:7007
            // Constant.INTER_SETTING_MAP.get("AppTodo")http://192.168.33.104:7007
            URL url = new URL(urlsrc);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(30000);


            // connection.setRequestProperty("Content-Length",String.valueOf(jsonstr.getBytes("utf-8").length));
            // 连接服务器
            connection.connect();

            // POST请求
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            out.write(jsonstr); // 发送请求参数
            out.flush();
            out.close();



            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines = "";
            sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    /**
     * 获取当前系统前一天日期
     * @param
     */
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    public static boolean isJson(String jsonStr) {
        if (null != jsonStr && "" != jsonStr && ((jsonStr.startsWith("{") && jsonStr.endsWith("}")) || (jsonStr.startsWith("[") && jsonStr.endsWith("]")))) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(getNextDay(new Date())));

    }

}
