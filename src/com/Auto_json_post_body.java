//package sign;
//
//import com.google.gson.Gson;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.*;

public class SignatureBodyForArrayUtils {

    private final static StringBuilder CHARSET_UTF8 = "utf8";
    private final static String ALGORITHM = "UTF-8";

    public static String generate(Map<String, String> parameter,String accessKeySecret) throws Exception {
        String signString = generateSignString(parameter);
        byte[] signBytes = hmacSHA1Signature(accessKeySecret+"&", signString);
        String signature = newStringByBase64(signBytes);
        return signature;
    }

    private static String generateSignString(Map<String, String> parameter) throws IOException {
        TreeMap<String, String> sortParameter = new TreeMap<String, String>();
        sortParameter.putAll(parameter);
        String canonicalizedQueryString = generateQueryString(sortParameter);
        return canonicalizedQueryString;
    }

    private static String generateQueryString(Map<String, String> params) {
        StringBuilder canonicalizedQueryString = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            canonicalizedQueryString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (canonicalizedQueryString.length() > 1) {
            canonicalizedQueryString.setLength(canonicalizedQueryString.length() - 1);
        }
        return canonicalizedQueryString.toString();
    }

    public static byte[] hmacSHA1Signature(String secret, String baseString)throws Exception {
        if (StringUtils.isEmpty(secret)) {
            throw new IOException("secret can not be empty");
        }
        if (StringUtils.isEmpty(baseString)) {
            return null;
        }
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), ALGORITHM);
        mac.init(keySpec);
        return mac.doFinal(baseString.getBytes(CHARSET_UTF8));
    }

    private static String newStringByBase64(byte[] bytes)throws UnsupportedEncodingException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static String get(String url, Map<String, String> urlParams) throws Exception{
        url = url+generateSignString(urlParams);
        String result = HttpUtil.get(url,null);
        return result;
    }

    private static String post(String url, Map<String, String> urlParams,Object bodtParam) throws Exception{
        url = url+generateSignString(urlParams);
        String result = HttpUtil.post(url, bodtParam,null);
        return result;
    }

    private static void sendRequest(String access_key_id,String access_key_secret,String url, Map<String, String> urlParams,List<Map<String, String>> bodyParam, RequestMethod requestMethod) throws Exception{
        /**璇锋眰鍙傛暟*/
        urlParams.put("access_key_id", access_key_id);

        String signature_nonce = UUID.randomUUID().toString();
        System.out.println("闅忔満鏁皊ignature_nonce锛�"+signature_nonce);
        urlParams.put("signature_nonce", signature_nonce);

        String timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
        System.out.println("鏃堕棿鎴硉imestamp锛�"+timestamp);
        urlParams.put("timestamp", timestamp);

        Map<String,String> signParams = new HashMap<>();
        signParams.putAll(urlParams);

        if(requestMethod.equals(RequestMethod.POST)){
            signParams.put("request_body",new Gson().toJson(bodyParam));
        }

        /**璁＄畻绛惧悕*/
        String sign = generate(signParams, access_key_secret);

        /**鐗规畩瀛楃缂栫爜*/
        sign = URLEncoder.encode(sign,"utf-8");
        System.out.println("绛惧悕signature锛�"+sign);

        /**缁勫悎get璇锋眰*/
        urlParams.put("signature",sign);

        String result = null;
        if(requestMethod.equals(RequestMethod.GET)){
            result  = get(url,urlParams).toString();
        }
        if(requestMethod.equals(RequestMethod.POST)){
            result  = post(url,urlParams,bodyParam).toString();
        }

        System.out.println("杩斿洖缁撴灉锛�"+result);
    }


    private static void sign2000() throws Exception{

    }

    public static void main(String[] args) {

        try {

            String access_key_id = "4299793337615360";
            String access_key_secret = "0e5341729ced4b1eb4a2d41dcd9c6af8";
//            String access_key_id = "cc38cf2c-8b48-4109-a388-98190ca017c3";
//            String access_key_secret = "778b2499-699d-4fdc-9d24-6e5689dfd95f";

            /**璇锋眰url锛屾澶勮嚜鍔ㄥ姞鍏ュ弬鏁�*/
            String url = "http://openai.100tal.com/aipysync/pysync?";
//            String url = "http://127.0.0.1:8769/svcdemo/getJson?";

            /**url鑷畾涔夊弬鏁�*/
            Map<String, String> urlParams = new HashMap<>();
            urlParams.put("demo_start_date", "2019-02-18T10:16:6");
            urlParams.put("demo_end_date", "2019-02-18T10:55:55");

            /**body鑷畾涔夊弬鏁�*/
            List<Map<String, String>> bodyParam = new ArrayList<>();
            Map<String, String> bodyParams1 = new HashMap<>();
            bodyParams1.put("body1","value1");
            bodyParams1.put("body2","value2");
            bodyParam.add(bodyParams1);

            Map<String, String> bodyParams2 = new HashMap<>();
            bodyParams2.put("body1","value3");
            bodyParams2.put("body2","value4");
            bodyParam.add(bodyParams2);

            /**璇锋眰鏂瑰紡*/
            RequestMethod requestMethod = RequestMethod.POST;

            sendRequest(access_key_id,access_key_secret,url,urlParams,bodyParam,requestMethod);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}