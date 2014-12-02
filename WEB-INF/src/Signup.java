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
 * Signup Servlet
 * Using the username and password to insert a new user
 * ID can be assigned automatically
 * Email can be null
 * @param Username login name from user
 * @param password 
 * @param email can be null
 * @author GawainGuo
 *
 */

public class Signup extends HttpServlet{
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		PrintWriter out=response.getWriter();
		MysqlDatabase mDatabase=new MysqlDatabase();
		try {
				out.println(mDatabase.insertUser(username, password, email).toString());
		} catch (SQLException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(out);
		}
	}
}
