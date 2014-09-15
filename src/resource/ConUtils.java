package resource;

public class ConUtils {

	
	public static String default_username = "liuhaodong";
	
	public static String default_password = "liuhaodong";
	/**
	 * 服务器默认IP地址 
	 */
	private static String default_ip = "192.168.3.16";
	/**
	 * 服务器默认端口号
	 */
	private static String default_port = "8080";
	/**
	 * 服务器默认地址
	 */
	private static String default_project = "MyServer/";
	/**
	 * 注册 url
	 */
	public static String register_url= "http://"+default_ip+":"+default_port+"/"+default_project+"myregister";
	
	/**
	 * 登陆 url
	 */
	public static String login_url = "http://"+default_ip+":"+default_port+"/"+default_project+"TestServlet";
	/**
	 * 当前活动信息 url
	 */
	public static String presentInfo_url = "http://"+default_ip+":"+default_port+"/"+default_project+"presentinfo!present";
	
	/**
	 * 以往活动信息 url
	 */
	public static String backInfo_url = "http://"+default_ip+":"+default_port+"/"+default_project+"backinfo!back";
	
	/**
	 * 通知 url
	 */
	public static String MsgInfo_url = "http://"+default_ip+":"+default_port+"/"+default_project+"msginfo!msgInfo";
	
	/**
	 * 地图功能 url
	 */
	public static String MapInfo_url = "http://"+default_ip+":"+default_port+"/"+default_project+"mapinfo!map";
	/**
	 * 上传照片 url
	 */
	public static String upload_image_url = "http://"+default_ip+":"+default_port+"/"+default_project+"file!upload";
	/**
	 * 添加新活动的url
	 */
	public static String AddNewActivity_url = "http://"+default_ip+":"+default_port+"/"+default_project+"createActivity";
	/**
	 * 注册  数据传输的标签
	 */
	public static String REGISTER_DATA_TAG = "ActivityDesigner_reg";
	
	public static String Ip = "192.168.1.102";
	
	public static String ProjectName = "android_servlet";
	
	public static String UpLoadServer = "pic";
	public static String upload_image_TAG = "img";
	
	public static String UpLoadUrl = "http://"+Ip+":8080/"+
						ProjectName+"/"+UpLoadServer;
	 
}
