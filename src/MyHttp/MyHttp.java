package MyHttp;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class MyHttp
{
	private MyThread timeThread;
	private static String default_ip = "192.168.1.103";
	private static String default_serverAddr = "/android_servlet/test";
	private static String url="http://10.0.2.2:8080/PDAServer/login.action";
	HttpClient client;
	public static String TAG = "uploadFile"; //上传文件
	private static final int TIME_OUT = 10 * 10000;	//超时时间
	private static final int REQUEST_TIMEOUT = 10*1000;//设置请求超时10秒钟 
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟   
	private static final String CHARSET = "utf-8";//编码方式
	public static final String SUCCESS = "1"; 
	public static final String FAILURE = "0";
	private boolean timeEnd = false;
	public MyHttp()
	{
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
		client = new DefaultHttpClient(httpParams);
		  
     }
	
	 /**
	 * 进行登陆验证
	 * @param user 用户名
	 * @param pass 密码
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public boolean loginJudge(String user,String pass) throws ClientProtocolException, IOException
	 {
		Log.i("MyHttp", "loginJudge invoke success!");
		//创建一个请求头的字段，比如content-type,text/plain
		BasicNameValuePair pair1 = new BasicNameValuePair("Username",user);
		BasicNameValuePair pair2 = new BasicNameValuePair("Password",pass);
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(pair1);
		parameters.add(pair2);

		//对自定义请求头进行URL编码
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,HTTP.UTF_8);
		HttpPost post = new HttpPost("http://"+default_ip+":8080"+default_serverAddr);//此处的URL为http://..../path
		post.setEntity(entity);
		timeThread.start();
		String page = client.execute(post,new BasicResponseHandler());
		HttpResponse response = client.execute(post);
		Log.i("MyHttp", "loginJudge process success!");
		int code = response.getStatusLine().getStatusCode();
		InputStream in = response.getEntity().getContent();//服务器返回的数据
		return true;
	 }
	
	/**
	 * 向服务器发送请求
	 * 进行注册 
	 * @param user 用户名
	 * @param pass 密码
	 * @param mail 邮箱地址
	 * @param phone 电话号码
	 * @param que 密码提示问题
	 * @param ans 密码提示问题答案
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public boolean registerProcess(String user,String pass,String mail,String phone,String que,String ans) throws ClientProtocolException, IOException
	{
		Log.i("MyHttp", "registerProcess invoke success!");
		BasicNameValuePair pair1 = new BasicNameValuePair("RegUsername",user);
		BasicNameValuePair pair2 = new BasicNameValuePair("RegPassword",pass);
		BasicNameValuePair pair3 = new BasicNameValuePair("RegMailAddr",mail);
		BasicNameValuePair pair4 = new BasicNameValuePair("RegPhoneNum",phone);
		BasicNameValuePair pair5 = new BasicNameValuePair("RegPassQue",que);
		BasicNameValuePair pair6 = new BasicNameValuePair("RegPassQueAns",ans);
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(pair1);
		parameters.add(pair2);
		parameters.add(pair3);
		parameters.add(pair4);
		parameters.add(pair5);
		parameters.add(pair6);
		//对自定义请求头进行URL编码
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,HTTP.UTF_8);
		HttpPost post = new HttpPost("http://"+default_ip+":8080"+default_serverAddr);//此处的URL为http://..../path
		post.setEntity(entity);
		//进行连接超时的处理
		HttpResponse response = client.execute(post);
		Log.i("MyHttp", "registerProcess process success!");
		int code = response.getStatusLine().getStatusCode();
		InputStream in = response.getEntity().getContent();//服务器返回的数据
		return true;
	}
	 

	
	
	
	/**
	 * 用于上传文件
	 * @param file
	 * @return
	 */
	public void test()
	{
		url+="?username=123456";
		client = new DefaultHttpClient();
	    HttpPost request; 
		 try
		 {
		 request = new HttpPost(new URI(url));
	     HttpResponse response=client.execute(request);
	     //判断请求是否成功
	      if(response.getStatusLine().getStatusCode()==200)
	      {
	    	  HttpEntity  entity=response.getEntity();
	      
	    	  if(entity!=null)
	    	  {
	    		  String out=EntityUtils.toString(entity);
	    	     
	    	  }
	      }
	 }catch (URISyntaxException e) {
		   e.printStackTrace();
	  }
	     catch (ClientProtocolException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }    
	}
	
	
	public static String upLoadFile(File file) 
	{
		String BOUNDARY = UUID.randomUUID().toString();//边界标示 随机产生
		String PREFIX = "--",LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/from-data";//内容类型
		String RequestURL  = "";
		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setDoInput(true);//允许输入流
			conn.setDoOutput(true);//允许输出流
			conn.setUseCaches(true);//不允许使用缓存
			conn.setRequestMethod("POST");//请求方式
			conn.setRequestProperty("Charset",CHARSET);//设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE+
					";boundary="+BOUNDARY);
			if(file != null)
			{
				/*
				 * 如果文件不为空 把文件包装并且上传
				 */
				OutputStream outputStream = conn.getOutputStream();
				DataOutputStream dos = new DataOutputStream(outputStream);
				StringBuffer sb  = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				 /* 
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 
                 * filename是文件的名字，包含后缀名的 比如:abc.png 
                 */ 
				sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""  
                        + file.getName() + "\"" + LINE_END);  
                sb.append("Content-Type: application/octet-stream; charset="  
                        + CHARSET + LINE_END); 
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes  = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1)
                {
                	dos.write(bytes,0,len);
				}

                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /*
                 *获取响应码 200=成功 当响应成功,获取响应的流 
                 */
                int res = conn.getResponseCode();
                if(res == 200)
                {
                	return SUCCESS;
                }
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return FAILURE;
	}
	
	
	
	class MyThread extends Thread
	{
		int count = 1;
		
		public void run() {
		
			for (int i = 0; i < 10; i++) {
				
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
			}
			timeEnd = true;

		}
		
	}
}
