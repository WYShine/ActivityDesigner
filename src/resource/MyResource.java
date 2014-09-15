package resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.R.string;
import android.view.View;

public class MyResource {
	
	
	public static View onCreateCellView;
	/**
	 * 登陆状态 0为未登陆 1为已经登陆
	 */
	public static int loginState = 0;
	/**
	 * 发起人名字
	 */
	public static String sponName = "name"; 
	/**
	 * 活动名字
	 */
	public static String acName = "acName";
	/**
	 * 活动日期
	 */
	public static String acDate = "acDate";
	/**
	 * 活动开始时间
	 */
	public static String acBeginTime = "acBeginTime";
	/**
	 * 活动结束时间
	 */
	public static String acEndTime = "acEndTime";
	/**
	 * 活动地点
	 */
	public static String acLocation = "acLocation";
	/**
	 * 活动被邀请人
	 */
	public static String acInvited = "invited:";
	/**
	 * 信息链接
	 */
	public static String MsgLink = "MsgLink";
	/**
	 * 活动备注
	 */
	public static String acNotes = "acNotes";
	/**
	 * 活动内容
	 */
	public static String MsgIn = 
			"您好！活动管理助手提示您：由" + sponName + "创建的" + acName +"活动"
			+"将在"+acBeginTime+""+ acLocation + "开展"+",您收到了邀请，请尽快回复."
			+"点击此链接进行回复:     " + MsgLink+"活动备注：  "+acNotes;
	
	
	/*                         注册                                                                   */
	/** 注册名**/
	public static String regUser = "regUser";
	/** 注册密码**/
	public static String regPass = "regPass";
	/** 注册再次输入密码**/
	public static String regPassConfirm = "regPassConfirm";
	/** 注册邮箱**/
	public static String regMail = "regMail";
	/** 注册电话号码**/
	public static String regPhoneNum = "0";
	/** 注册密码问题**/
	public static String regPassQue = "regPassQue";
	/** 注册密码问题答案**/
	public static String regPassQueAns = "regPassQueAns";
	
	
	/*                         登陆                                                                    */
	/**登陆用户名**/
	public static String Log_username = "liuhaodong";
	/**登陆用户密码**/
	public static String LogPass = "";
	
	
	/*                        当前活动                                                                    */
	/**当前活动 内容概括**/
	public static String preActivitycontent = "";
	/**当前活动 索引及内容概括**/
	public static Map<Integer,String> preActivity = new HashMap<Integer, String>();
	/**
	 * 当前活动 内容的一个List 元素为HashMap
	 * HashMap元素为 1.活动的索引 2.活动的简单概括
	 */
	public List<HashMap> preActivities = new ArrayList<HashMap>();
	
	/*                         以往活动                                                                    */
	/** 以往活动 内容概括**/
	public static String bacActivitycontent = "";
	/** 以往活动 索引及内容概括**/
	public static Map<Integer,String> bacActivity = new HashMap<Integer, String>();
	/**
	 * 以往活动 内容的一个List 元素为HashMap
	 * HashMap元素为 1.活动的索引 2.活动的简单概括
	 */
	public List<HashMap> bacActivities = new ArrayList<HashMap>();
	
	/*                         通知                                                                    */
	
	public static  Map<String, Boolean> presentActivityInfo; 
	
	public static void clean()
	{
		sponName = "name"; 
	    acName = "acName";
		acDate = "acDate";
		acBeginTime = "acBeginTime";
	    acEndTime = "acEndTime";
		acLocation = "acLocation";
		acInvited = "acInvited";
		acNotes = "acNotes";
	}
	MyResource()
	{
		
	}
	
}
