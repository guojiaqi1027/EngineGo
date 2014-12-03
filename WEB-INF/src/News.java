import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * News Servlet Class
 * Return the News titles and image URL as JSONArray
 * Read News from server disk(news1.txt,news2.txt. ...)
 * Initial the news image from server disk
 * @author GawainGuo
 * Last modify time 2014/12/1
 */

public class News extends HttpServlet{
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter outprint=response.getWriter();
		JSONArray array=new JSONArray();
		
		BufferedReader br = new BufferedReader(new FileReader("/Library/Tomcat/webapps/EngineGo/news/news1.txt"));
		StringBuilder sb=new StringBuilder();
		String line=br.readLine();
		while(line!=null){
			sb.append(line);
			sb.append(System.lineSeparator());
			line=br.readLine();
		}
		String result=sb.toString();
		br.close();
		
		JSONObject jsonObj=new JSONObject();
		
		try {
			jsonObj.put("Content", result);
			jsonObj.put("Picture", ":8080/EngineGo/news/news1.jpg");
			array.put(jsonObj);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(outprint);
		}
		
		BufferedReader br2 = new BufferedReader(new FileReader("/Library/Tomcat/webapps/EngineGo/news/news2.txt"));
		StringBuilder sb2=new StringBuilder();
		String line2=br2.readLine();
		while(line2!=null){
			sb2.append(line2);
			sb2.append(System.lineSeparator());
			line2=br2.readLine();
		}
		String result2=sb2.toString();
		br2.close();
		
		JSONObject jsonObj2=new JSONObject();
		
		try {
			jsonObj2.put("Content", result2);
			jsonObj2.put("Picture", ":8080/EngineGo/news/news2.jpg");
			array.put(jsonObj2);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(outprint);
		}
		
		BufferedReader br3 = new BufferedReader(new FileReader("/Library/Tomcat/webapps/EngineGo/news/news3.txt"));
		StringBuilder sb3=new StringBuilder();
		String line3=br3.readLine();
		while(line3!=null){
			sb3.append(line3);
			sb3.append(System.lineSeparator());
			line3=br3.readLine();
		}
		String result3=sb3.toString();
		br3.close();
		
		JSONObject jsonObj3=new JSONObject();
		
		try {
			jsonObj3.put("Content", result3);
			jsonObj3.put("Picture", ":8080/EngineGo/news/news3.jpg");
			array.put(jsonObj3);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(outprint);
		}
		
		outprint.println(array.toString());
	}
}
