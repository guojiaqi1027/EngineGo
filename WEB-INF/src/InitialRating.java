import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import sql.MysqlDatabase;


public class InitialRating extends HttpServlet{
	final int[] initialList={10000,10011,10019,10028,10007,10016,10057,10066,10055,10080};
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter outprint=response.getWriter();
		MysqlDatabase mDatabase=new MysqlDatabase();
		JSONArray array=new JSONArray();
		for(int i=0;i<initialList.length;i++){
			try {
				array.put(mDatabase.getMovie(String.valueOf(initialList[i])));
			} catch (JSONException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(outprint);
			}
		}
		outprint.println(array.toString());
	}
}
