package MyHttp;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSON {

	
	
	public String[] getJSON(String webContent,String[] key)
	{
		Log.i("JSONcon", webContent);
		int size =  key.length;
		String[] s = new String[size];
		try {
			JSONObject jsonObject = new JSONObject(webContent);
			Log.i("jsonObject", jsonObject.toString());
			for (int i = 0; i < size; i++) 
			{
				s[i] = jsonObject.getString(key[i]);
				Log.i("JSON", "getJSON:  "+key[i]+"===string===" 
				+jsonObject.getString(key[i]));
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("JSON", "error");
			s = null;
		}
		return s;
	}
	
	public ArrayList<HashMap<String,Object>> getJSONArray(String webContent,String[] key,String jsonName)
	{
		ArrayList<HashMap<String, Object>>list;
		JSONArray jsonArray;
		try {
			jsonArray = new JSONObject(webContent).getJSONArray(jsonName);
			list = new ArrayList<HashMap<String,Object>>();
			for (int i = 0; i < jsonArray.length(); i++) 
			{
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				HashMap<String, Object>map = new HashMap<String, Object>();
				for (int j = 0; j < key.length; j++) 
				{  
				   map.put(key[j], jsonObject.getString(key[j]));  
				   Log.i("JSON","getJSON:  "+key[j] + "===" + jsonObject.getString(key[j]));  
			    }  
				 list.add(map);
				 
			}  
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			list = null;
		}
		return list;  
	}

	public ArrayList<HashMap<String, Object>>getArrayList(String webContent,String[] key)
	{
		ArrayList<HashMap<String, Object>>list;
		try {
			JSONArray jsonArray = new JSONArray(webContent);
			list = new ArrayList<HashMap<String,Object>>();
			String a[] = key;
			for (int i = 0; i < jsonArray.length(); i++) 
			{
				JSONObject jsonObject  = jsonArray.getJSONObject(i);
				HashMap<String, Object>map = new HashMap<String, Object>();
				for (int j = 0; j < key.length; j++) 
				{
					map.put(key[j], jsonObject.getString(key[j]));  
					Log.i("JSON","getJSON:  "+key[j] + "===" + jsonObject.getString(key[j]));  
				}
				 list.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			list = null;
		}
		return list;  
	}
}
