import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import sql.MysqlDatabase;
/**
 * InsertWaitingList Servlet Class
 * Insert a new record to waiting_list table in database
 * @param userid user_id from Client side
 * @param movieid movie_id from client side
 * @author GawainGuo
 * Last modify time 2014/12/1
 *
 */

public class InsertWaitinglist extends HttpServlet{
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String user_id=request.getParameter("userid");
		String movie_id=request.getParameter("movieid");
		PrintWriter outprint=response.getWriter();
		MysqlDatabase mDatabase=new MysqlDatabase();
		try {
			JSONArray array= mDatabase.insertWaitingList(user_id, movie_id);
			outprint.println(array.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(outprint);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(outprint);
		}
		
	}
	
}
