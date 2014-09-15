package MyHttp;

import android.telephony.SmsManager;
import android.util.Log;

public class SendMsg {

  
   public static void Send(String [] phone,String [] personName,int phoneIndex,String MSG_content)
   {
    	String temp = MSG_content;
    	for(int i = 0; i != phoneIndex;++i)
    	{
    		SmsManager smsManager = SmsManager.getDefault();
    	    smsManager.sendTextMessage(phone[i], null, temp, null, null);
    	    Log.d("SendMsg"," "+personName[i]+" "+phone[i]+" "+ temp);
    	}
    		
    }
}
