import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.mahout.cf.taste.common.TasteException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cf.RecommenderFactory;
import cf.UserRecommender;
import sql.MysqlDatabase;

/**
 * Recommend Servlet Class
 * Return a recommendation movie from CF algorithm and database
 * Recommend 5 movies from recommender
 * Filter movies to get the first non-common element with waiting_list
 * @param userid user_id from client
 * @author GawainGuo
 * Last modify time 2014/12/1
 */

public class Recommend extends HttpServlet{

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		PrintWriter outprint=response.getWriter();
		String userid=request.getParameter("userid");
		MysqlDatabase mDatabase=new MysqlDatabase();
		JSONArray array=new JSONArray();
		try {
			ArrayList<String> waitingList=mDatabase.getWaitingListString(userid);
			int ratedList=mDatabase.getRatingList(userid).length();
			UserRecommender mRecommender=RecommenderFactory.getRecommender(RecommenderFactory.SPEARMAN_RECOMMENDER);
			/*
			 * Get n neighbors and 5 movies
			 */
			int n=3;
			ArrayList<Long> recommendList=mRecommender.getRecommend(userid,3,5);
			int length=ratedList+waitingList.size();
			if(length>=100){
				JSONObject jsonObj=new JSONObject();
				jsonObj.put("result", "false");
				jsonObj.put("reason", "Cannot get recommendation");
				array.put(jsonObj);
				outprint.println(array.toString());
				return;
			}
			while(length!=100&&recommendList.size()<5){
				recommendList=mRecommender.getRecommend(userid,n++,5);
				if(n>=100) break;
			}
			
			
			/*
			 * If recommend_list has no element as no recommendation can be get
			 * Return false Json Object
			 */
			if(recommendList.size()==0){
				JSONObject jsonObj=new JSONObject();
				jsonObj.put("result", "false");
				jsonObj.put("reason", "Cannot get recommendation");
				array.put(jsonObj);
			}
			
			/*
			 * Get recommend id if waiting_list is smaller than recommend_list
			 */
			else if(waitingList.size()<recommendList.size()){
				if(recommendList.size()<=0){
					JSONObject jsonObj=new JSONObject();
					jsonObj.put("result", "false");
					array.put(jsonObj);
					outprint.println(array.toString());
					return;
				}
				String recommend_id=null;
				/*
				 * Find the first common element in waiting_list and recommend_list
				 */
				for(int i=0;i<waitingList.size();i++){
					if(!waitingList.contains(recommendList.get(i).toString())){
						recommend_id=recommendList.get(i).toString();
						break;
					}
				}
				if(recommend_id==null){
					recommend_id=recommendList.get(waitingList.size()).toString();
				}
				JSONObject jsonObj=mDatabase.getMovie(recommend_id);
				jsonObj.put("result", "true");
				jsonObj.put("movie_id", recommend_id);
				array.put(jsonObj);
			}
			
			/*
			 * If recommend list is smaller than waiting list, then there will be empty common list
			 * Set result to false
			 */
			else{
				JSONObject jsonObj=new JSONObject();
				jsonObj.put("result", "false");
				array.put(jsonObj);
			}
			outprint.println(array.toString());
		} catch (JSONException | SQLException e) {
			e.printStackTrace(outprint);
		} catch (TasteException e) {
			e.printStackTrace(outprint);
		}
	}
}
