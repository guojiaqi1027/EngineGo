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
 * Rating Servlet
 * Update or insert a new rating from user
 * If this movie is already exist in table, update 
 * If new, insert
 * Using insertOrUpdate method instead of insert method to avoid string error with '
 * @param userid user_id from user
 * @param movieid movie_id the user want to rate
 * @param rate the rate value of this movie
 * @author JIAQI GUO
 *
 */
public class Rate extends HttpServlet{
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		
		String user_id=request.getParameter("userid");
		String movie_id=request.getParameter("movieid");
		String rate=request.getParameter("rate");
		MysqlDatabase mDatabase=new MysqlDatabase();
		JSONArray array=new JSONArray();
		try {
			array=mDatabase.insertRatingRecord(user_id, movie_id, rate);
			out.println(array.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(out);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(out);
		}
		
	}
}
