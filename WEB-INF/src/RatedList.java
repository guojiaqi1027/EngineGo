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
 * RatedList Servlet
 * Get Rated movie list of user by user_id
 * Print a JSONArray of movie list with name, id and description
 * @param userid from user
 * @author GawainGuo
 * Last modify time 2014/12/1
 */

public class RatedList extends HttpServlet{
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		PrintWriter outprint=response.getWriter();
		String userid=request.getParameter("userid");
		MysqlDatabase mDatabase=new MysqlDatabase();
		JSONArray array=new JSONArray();
		try {
			array=mDatabase.getRatingList(userid);
			outprint.println(array.toString());
		} catch (JSONException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(outprint);
		}
	}
}
