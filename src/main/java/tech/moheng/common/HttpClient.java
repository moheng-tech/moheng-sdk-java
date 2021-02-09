package tech.moheng.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author yyl
 * @date 2021年1月25日
 */
public class HttpClient {
	private static final String ENCODING = "UTF-8";

	
	public static String post(String location,String method, Object params) {
		HttpURLConnection conn = null;
		PrintWriter out = null;
		BufferedReader br = null;
		JSONObject data = new JSONObject();
		data.put("jsonrpc", "2.0");
		data.put("id", 0);
		data.put("method", method);
		data.put("params", params);
		try {
			URL url = new URL(location+"/rpc");
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.connect();
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), ENCODING));
			out.write(data.toJSONString());
			out.flush();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), ENCODING));
			String lines;
			StringBuffer sb = new StringBuffer();
			while ((lines = br.readLine()) != null)
				sb.append(lines);
			JSONObject obj = JSONObject.parseObject(sb.toString());
			if(StringHelper.isNotBlank(obj.getString("error"))){
				return JSONObject.parseObject(obj.getString("error")).getString("message");
			}else{
				return obj.getString("result");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null)
				conn.disconnect();
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

}