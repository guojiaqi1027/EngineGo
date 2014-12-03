import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sql.MysqlDatabase;
/**
 * Login Servlet
 * Receive login request from client with parameter username and password
 * Connecting to mysql database and verify the username and password
 * Return the verification trought JSON
 * @author JIAQI GUO
 * Last modify time 2014/12/1
 */


public class Login extends HttpServlet{
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter outprint=response.getWriter();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		MysqlDatabase mDatabase=new MysqlDatabase();
		try {
			JSONArray result=mDatabase.verifyLogin(username, password);
			outprint.println(result.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			PrintWriter out=response.getWriter();
			out.println("Database fail");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(outprint);
		}
	}
}
