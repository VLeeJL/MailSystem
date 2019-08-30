package sign;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;

public class HttpUtil {
    /*系统中编码*/
    public static final String CHARSET="UTF-8";
	
	public static String post(String url,Object param,String authorization) 
			throws Exception {
		HttpResponse response = post(url,param,authorization,"application/json");
		try {
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			return null;
		}
	}
	
	public static HttpResponse post(String url,Object param,String authorization,String contentType) 
			throws Exception{
		//参数+解决中文乱码问题
		String jsonParam = new Gson().toJson(param);
        StringEntity entity = new StringEntity(jsonParam,CHARSET);
        entity.setContentEncoding(CHARSET);
        if(contentType != null)
        	entity.setContentType(contentType);
		
        HttpPost post = new HttpPost(url);
		post.setHeader("Authorization", authorization);
		post.setEntity(entity);
		HttpClient client = HttpClients.createDefault();
		HttpResponse result = client.execute(post);
		if(result.getStatusLine().getStatusCode() == 200)
			return result;
		else{
			String errorJson =  EntityUtils.toString(result.getEntity(), Charset.defaultCharset());
			System.out.println(errorJson);
			throw new Exception(errorJson);
		}
	}
	
	public static String get(String url,String authorization) throws Exception {
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		if(authorization != null)
			get.setHeader("Authorization", authorization);
		HttpResponse result = client.execute(get);
		if(result.getStatusLine().getStatusCode() == 200){
			 String resposeJson = EntityUtils.toString(result.getEntity());
			 if("[]".equals(resposeJson) ||"".equals(resposeJson) )/*为兼容图片服务器返回[]bug*/
				 resposeJson = "{}";
			return resposeJson;
		}else{
			String errorJson =  EntityUtils.toString(result.getEntity(), Charset.forName("GBK"));
			System.out.println(errorJson);
			throw new Exception(errorJson);
		}
	}
	
	
	
	
	
	
}
