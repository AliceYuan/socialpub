package org.geometerplus.zlibrary.core.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public final class ZJSONParser {
	static InputStream is = null;
    static JSONArray jArray = null;
    static String json = "";
    
    //JSON Node Names
    static final String TAG_USERID = "userid";
    static final String TAG_COMMENT = "comment";
    static final String TAG_PAGE = "page";
    static final String TAG_START_POS = "start_pos";
    static final String TAG_END_POS = "end_pos";
    
    public ZJSONParser() {}
    
    public void sendJSONToServer(String url, JSONObject json) {    	
    	// Making HTTP request
        try {
        	StringEntity se = new StringEntity(json.toString());
	    	se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);        	
            httpPost.setEntity(se); 
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            String log = EntityUtils.toString(httpEntity);
            Log.i("tag", log);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public JSONArray getJSONFromUrl(String url, List<NameValuePair> params) {
 
    	String paramString = new String();
    	if(params.size() > 0) {
    		url += "?";
        	paramString = URLEncodedUtils.format(params, "iso-8859-1");
    	}
    	
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url + paramString);
 
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();          
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        // try parse the string to a JSON array
        try {
        	jArray = new JSONArray(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
 
        // return JSON String
        return jArray; 
    }
}
