package org.geometerplus.zlibrary.core.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.geometerplus.android.fbreader.ResultObject;
import org.geometerplus.zlibrary.text.view.ZLTextPosition;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public final class ZJSONParser {
	static InputStream is = null;
    static JSONArray jArray = null;
    static String json = "";
    
    //JSON Node Names
    static final String TAG_USERID = "userId";
    static final String TAG_BOOKID = "bookId";
    static final String TAG_COMMENT = "comment";
    static final String TAG_PARASTARTINDEX = "paragraphStartIndex";
    static final String TAG_PARAENDINDEX = "paragraphEndIndex";
    static final String TAG_ELEMSTARTINDEX = "elementStartIndex";
    static final String TAG_ELEMENDINDEX = "elementEndIndex";
    static final String TAG_CHARSTARTINDEX = "charStartIndex";
    static final String TAG_CHARENDINDEX = "charEndIndex";
    static final String TAG_USERURL = "userUrl";
    static final String TAG_TIMESTAMP = "timestamp";
    static final String TAG_TITLE = "title";
    static final String TAG_OWNERNAME = "ownerName";
    
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
    
    public void addComment(ResultObject comment, ZLTextPosition startPos, ZLTextPosition endPos) {
    	// URL to get JSON Array
        String url = "http://mduan.com:5000/comment";
        
        JSONObject json = new JSONObject();
        try {
			json.put(TAG_USERID, Integer.parseInt(comment.getUserId()));
	        json.put(TAG_COMMENT, comment.getQuery());
	        json.put(TAG_PARASTARTINDEX, startPos.getParagraphIndex());
	        json.put(TAG_PARAENDINDEX, endPos.getParagraphIndex());
	        json.put(TAG_ELEMSTARTINDEX, startPos.getElementIndex());
	        json.put(TAG_ELEMENDINDEX, endPos.getElementIndex());	        
	        json.put(TAG_CHARSTARTINDEX, startPos.getCharIndex());
	        json.put(TAG_CHARENDINDEX, endPos.getCharIndex());
	        json.put(TAG_USERURL, comment.getImageUrl());
	        json.put(TAG_TIMESTAMP, comment.getDatetaken());
	        json.put(TAG_TITLE, comment.getTitle());
	        json.put(TAG_OWNERNAME, comment.getOwnerName());
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
    	sendJSONToServer(url, json);
    }
    
    public ResultObject[] fetchComments(String bookId, ZLTextPosition startPos, ZLTextPosition endPos) {
    	// URL to get JSON Array
        String url = "http://mduan.com:5000/getcomments";
        
        // Generating the params
        List<NameValuePair> params = new LinkedList<NameValuePair>();
        params.add(new BasicNameValuePair(TAG_BOOKID, bookId));
        params.add(new BasicNameValuePair(TAG_PARASTARTINDEX, Integer.toString(startPos.getParagraphIndex())));
        params.add(new BasicNameValuePair(TAG_PARAENDINDEX, Integer.toString(endPos.getParagraphIndex())));
        params.add(new BasicNameValuePair(TAG_ELEMSTARTINDEX, Integer.toString(startPos.getElementIndex())));
        params.add(new BasicNameValuePair(TAG_ELEMENDINDEX, Integer.toString(endPos.getElementIndex())));
        params.add(new BasicNameValuePair(TAG_CHARSTARTINDEX, Integer.toString(startPos.getCharIndex())));
        params.add(new BasicNameValuePair(TAG_CHARENDINDEX, Integer.toString(endPos.getCharIndex())));
        
        // Getting JSON from URLj
        JSONArray jArray = getJSONFromUrl(url, params);
        
        ResultObject results[] = new ResultObject[jArray.length()];
        for (int i=0; i<jArray.length(); i++) {
        	try {
				JSONObject jObj = jArray.getJSONObject(i);
				ResultObject rObj = new ResultObject(
						jObj.getString(TAG_COMMENT),
						Integer.toString(jObj.getInt(TAG_BOOKID)),
						jObj.optString(TAG_TITLE),
						jObj.optString(TAG_OWNERNAME),
						jObj.getString(TAG_TIMESTAMP),
						jObj.getString(TAG_USERURL),
						Integer.toString(jObj.getInt(TAG_USERID)));
				rObj.setRange(
						jObj.getInt(TAG_PARASTARTINDEX), 
						jObj.getInt(TAG_PARAENDINDEX),
						jObj.getInt(TAG_ELEMSTARTINDEX),
						jObj.getInt(TAG_ELEMENDINDEX),
						jObj.getInt(TAG_CHARSTARTINDEX),
						jObj.getInt(TAG_CHARENDINDEX));
				results[i] = rObj;
			} catch (JSONException e) {
				e.printStackTrace();
			}        			
        }
        
        return results;
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
