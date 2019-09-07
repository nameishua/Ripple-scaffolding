
package top.coderak.core.utils;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 *HttpClient工具类
 */
public class HttpUtil
{
	/**
	 * 发送get请求
	 * @param url 请求
	 * @return 返回结果
	 */
	public static String doGet(String url) throws Exception
	{

		try
		{
			// 创建HttpUtil.java对象
			HttpClient client = new DefaultHttpClient();
			
			// 组织get请求
			HttpGet requset = new HttpGet(url);
			
			// 执行请求
			HttpResponse response = client.execute(requset);

			// 请求发送成功，并得到响应 
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				// 读取服务器返回数据 
				String strResult = EntityUtils.toString(response.getEntity());

				// 返回结果
				return strResult;
			}
			else 
			{
				// 抛出道common层异常
				throw new Exception("ERROR-REQUEST");
			}
		}
		catch (IOException e)
		{

			// 抛出道common层异常
			throw new Exception("ERROR-IO");
		}
	}

	/**
	 * post请求(用于key-value格式的参数)
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, Map params)
	{

		BufferedReader in = null;

		try
		{

			// 定义HttpClient
			HttpClient client = new DefaultHttpClient();

			// 实例化HTTP方法
			HttpPost request = new HttpPost();

			request.setURI(new URI(url));

			// 设置参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			for (Iterator iter = params.keySet().iterator(); iter.hasNext();)
			{

				String name = (String) iter.next();

				String value = String.valueOf(params.get(name));

				nvps.add(new BasicNameValuePair(name, value));

			}

			request.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			HttpResponse response = client.execute(request);

			int code = response.getStatusLine().getStatusCode();

			if (code == 200)
			{
				// 请求成功
				in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

				StringBuffer sb = new StringBuffer("");

				String line = "";

				String NL = System.getProperty("line.separator");

				while ((line = in.readLine()) != null)
				{
					sb.append(line + NL);
				}

				in.close();

				return sb.toString();
			}
			else
			{ //
				System.out.println("状态码：" + code);
				return null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * post请求（用于请求json格式的参数）
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, String params) throws Exception
	{

		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		// 创建httpPost
		HttpPost httpPost = new HttpPost(url);
		
		httpPost.setHeader("Accept", "application/json");
		
		httpPost.setHeader("Content-Type", "application/json");
		
		String charSet = "UTF-8";
		
		StringEntity entity = new StringEntity(params, charSet);
		
		httpPost.setEntity(entity);
		
		CloseableHttpResponse response = null;

		try
		{

			response = httpclient.execute(httpPost);
			
			StatusLine status = response.getStatusLine();
			
			int state = status.getStatusCode();
			
			if (state == HttpStatus.SC_OK)
			{
				HttpEntity responseEntity = response.getEntity();
				
				String jsonString = EntityUtils.toString(responseEntity);
				
				return jsonString;
			}
		}
		finally
		{
			
			if (response != null)
			{
				try
				{
					
					response.close();
				}
				catch (IOException e)
				{
					
//					LogHelper.getInstance().log(LogConstants.LOG_LEVEL_ERROR, e.getMessage(), e);
				}
			}
			try
			{
				
				httpclient.close();
			}
			catch (IOException e)
			{
				
//				LogHelper.getInstance().log(LogConstants.LOG_LEVEL_ERROR, e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * post请求（用于请求json格式的参数）
	 * @param url
	 * @param params
	 * @param token
	 * @return
	 */
	public static String doPost(String url, String params, String tokenKey,String token) throws Exception
	{

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader(tokenKey, token);
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(params, charSet);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;

		try
		{

			response = httpclient.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			if (state == HttpStatus.SC_OK)
			{
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity);
				return jsonString;
			}
		}
		finally
		{
			if (response != null)
			{
				try
				{
					response.close();
				}
				catch (IOException e)
				{
//					LogHelper.getInstance().log(LogConstants.LOG_LEVEL_ERROR, e.getMessage(), e);
				}
			}
			try
			{
				httpclient.close();
			}
			catch (IOException e)
			{
//				LogHelper.getInstance().log(LogConstants.LOG_LEVEL_ERROR, e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * post请求（用于请求json格式的参数）
	 * @param url
	 * @param params
	 * @param token
	 * @return
	 */
	public static String doPost(String url, String params, String tokenKey,String token,String contentType) throws Exception
	{

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", contentType);
		httpPost.setHeader(tokenKey, token);
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(params, charSet);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;

		try
		{

			response = httpclient.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			if (state == HttpStatus.SC_OK)
			{
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity);
				return jsonString;
			}
		}
		finally
		{
			if (response != null)
			{
				try
				{
					response.close();
				}
				catch (IOException e)
				{
//					LogHelper.getInstance().log(LogConstants.LOG_LEVEL_ERROR, e.getMessage(), e);
				}
			}
			try
			{
				httpclient.close();
			}
			catch (IOException e)
			{
//				LogHelper.getInstance().log(LogConstants.LOG_LEVEL_ERROR, e.getMessage(), e);
			}
		}
		return null;
	}
	
	/**
	 * post请求（用于请求json格式的参数）
	 * @param url
	 * @param params
	 * @param token
	 * @return
	 */
	public static String doFormPost(String url, String params, String tokenKey,String token) throws Exception
	{

		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		// 创建httpPost
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/x-www-form-urlencoded");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		
		httpPost.setHeader(tokenKey, token);
		
		String charSet = "UTF-8";
		
		StringEntity entity = new StringEntity(params, charSet);
		
		httpPost.setEntity(entity);
		
		CloseableHttpResponse response = null;

		try
		{

			response = httpclient.execute(httpPost);
			
			StatusLine status = response.getStatusLine();
			
			int state = status.getStatusCode();
			
			if (state == HttpStatus.SC_OK)
			{
				HttpEntity responseEntity = response.getEntity();
				
				String jsonString = EntityUtils.toString(responseEntity);
				
				return jsonString;
			}
		}
		finally
		{
			if (response != null)
			{
				try
				{
					response.close();
				}
				catch (IOException e)
				{
//					LogHelper.getInstance().log(LogConstants.LOG_LEVEL_ERROR, e.getMessage(), e);
				}
			}
			try
			{
				httpclient.close();
			}
			catch (IOException e)
			{
//				LogHelper.getInstance().log(LogConstants.LOG_LEVEL_ERROR, e.getMessage(), e);
			}
		}
		return null;
	}

	public static String httpUploadFile(File file, String url)
	{

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try
		{
			HttpPost httppost = new HttpPost(url);

			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
			
			httppost.setConfig(requestConfig);

			
			FileBody bin = new FileBody(file);
			
			StringBody comment = new StringBody("This is comment", ContentType.TEXT_PLAIN);
			
			HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532).addPart("file", bin).addPart("comment", comment).build();

			httppost.setEntity(reqEntity);

			System.out.println("executing request " + httppost.getRequestLine());
			
			CloseableHttpResponse response = httpclient.execute(httppost);
			
			try
			{
				
				System.out.println(response.getStatusLine());
				
				HttpEntity resEntity = response.getEntity();
				
				if (resEntity != null)
				{
					
					String responseEntityStr = EntityUtils.toString(response.getEntity());
					
					return responseEntityStr;
				}
				EntityUtils.consume(resEntity);
			}
			finally
			{
				response.close();
			}
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				httpclient.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String httpUploadFiles(File[] files, String url)
	{

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try
		{
			HttpPost httppost = new HttpPost(url);

			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
			
			httppost.setConfig(requestConfig);
			
			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
			
			for (int i = 0; i < files.length; i++)
			{
				FileBody bin = new FileBody(files[i]);
				entityBuilder.addPart("file", bin);
			}

			StringBody comment = new StringBody("This is comment", ContentType.TEXT_PLAIN);
			entityBuilder.addPart("comment",comment);
			httppost.setEntity(entityBuilder.build());

			System.out.println("executing request " + httppost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try
			{
				System.out.println(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null)
				{
					String responseEntityStr = EntityUtils.toString(response.getEntity());
					return responseEntityStr;
				}
				EntityUtils.consume(resEntity);
			}
			finally
			{
				response.close();
			}
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				httpclient.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void inputStreamUpload(InputStream inputStream, String url)
	{

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		try
		{
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.addBinaryBody("uploadFile", inputStream);
			StringBody stringBody = new StringBody("this is type", ContentType.MULTIPART_FORM_DATA);
			builder.addPart("id", stringBody);
			HttpEntity entity = builder.build();
			post.setEntity(entity);
			// 发送请求
			HttpResponse response = client.execute(post);
			entity = response.getEntity();
			if (entity != null)
			{
				inputStream = entity.getContent();
				// 转换为字节输入流
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Consts.UTF_8));
				String body = null;
				while ((body = br.readLine()) != null)
				{
					System.out.println(body);
				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (inputStream != null)
			{
				try
				{
					inputStream.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {

		String param = "ver:\"3.0\"\n" +
				"expr\n" +
				"\"siteSparkAppLoad({ nav:\\\"ids:\\\\\\\"p:beadhouse:r:22dad63c-ade34e40\\\\\\\"\\\", view:\\\"table\\\", dates:\\\"2018-06-01,2018-06-07\\\", rules:\\\"ids:\\\\\\\"default\\\\\\\" opts:\\\\\\\"auxFilter:\\\\\\\\\\\\\\\"not disabled\\\\\\\\\\\\\\\"\\\\\\\"\\\", rollup:\\\"dur\\\", points:\\\"\\\" })\"";

		String result = doPost("http://211.103.208.186:8080/api/beadhouse/eval",param,"Cookie","skyarc-auth-8080=s-QzK4h9P_Tq3e5EwMRINxT6CdeFI9njia2r5gEYfWDYc-6d1","text/zinc; charset=UTF-8");

		System.out.println(result);

	}
}
