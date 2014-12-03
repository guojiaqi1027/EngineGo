package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Mysql Database connection class
 * Connecting by JDBC and Connecter of MYSQL
 * Query the information from mysql database
 * @author JIAQI GUO
 * Last modify time 2014/12/1
 */
public class MysqlDatabase {
	private Connection mConnection;
	private Statement st;
	
	/**
	 * @throws JSONException 
	 * Verify Login Information
	 * @param username	username from request
	 * @param password	password from request
	 * @return JSONArray including result and user_id
	 * @throws SQLException
	 * @throws  
	 */
	public JSONArray verifyLogin(String username, String password) throws SQLException, JSONException {
		if(username==""||password==""){
			JSONObject json=new JSONObject();
			json.put("result", "false");
			JSONArray array = new JSONArray();
			array.put(json);
			return array;
		}
		mConnection=getConnection();
		st = (Statement) mConnection.createStatement();
		String query="select User_password,User_id from User where User_name="+"\""+username+"\"";
		ResultSet rs = st.executeQuery(query);
		String result="";
		String user_id="";
		while(rs.next()){
			result+=rs.getString("User_password");
			user_id=rs.getString("User_id");
			
		}
		if(result.equals(password)){
			JSONObject json=new JSONObject();
			json.put("result", "true");
			json.put("id", user_id);
			JSONArray array = new JSONArray();
			array.put(json);
			return array;
		}
		mConnection.close();
		JSONObject json=new JSONObject();
		json.put("result", "false");
		json.put("id", user_id);
		JSONArray array = new JSONArray();
		array.put(json);
		return array;
		
	}
	
	/**
	 * Insert a new user record to User table
	 * @param username username from client
	 * @param password password from client
	 * @param email email from client
	 * @return true if success
	 * @return false if exist
	 * @throws SQLException
	 * @throws JSONException 
	 */
	public JSONArray insertUser(String username,String password,String email) throws SQLException, JSONException{
		if(username==null||password==null){
			JSONObject json=new JSONObject();
			json.put("result", "false");
			JSONArray array = new JSONArray();
			array.put(json);
			return array;
		}
		mConnection=getConnection();
		st = (Statement) mConnection.createStatement();
		String query="select count(*) as count from User where User_name="+"\""+username+"\"";
		ResultSet rs=st.executeQuery(query);
		int num=0;
		while(rs.next()){
			num=rs.getInt("count");
		}
		if(num!=0){
			JSONObject json=new JSONObject();
			json.put("result", "false");
			JSONArray array = new JSONArray();
			array.put(json);
			return array;
		}
		query="select max(user_id) as id from User";
		rs=st.executeQuery(query);
		int id=0;
		while(rs.next()){
			id=rs.getInt("id")+1;
		}
		query="insert into User value('"+id+"','"+username+"','"+password+"','"+email+"')";
		st.execute(query);
		query="select user_id from User where user_name='"+username+"'";
		rs=st.executeQuery(query);
		String user_id="";
		while(rs.next()){
			user_id=rs.getString("user_id");
		}
		mConnection.close();
		JSONObject json=new JSONObject();
		json.put("result", "true");
		json.put("id", user_id);
		JSONArray array = new JSONArray();
		array.put(json);
		return array;
	}
	
	/**
	 * Insert or update a rating from user_id and movie_id
	 * Instead of movie_name and user_name to avoid string error
	 * @param movie_id 
	 * @param user_id
	 * @param rate
	 * @return JSONArray "insert" if insert a new rating; "update" if update a existed rating
	 * @throws SQLException 
	 * @throws JSONException 
	 */
	public JSONArray insertOrUpdateRating(String user_id,String movie_id,String rate) throws SQLException, JSONException{
		mConnection=getConnection();
		st = (Statement) mConnection.createStatement();
		String query="select count(*) as count from Rating where User_id='"+user_id+"' and Movie_id='"+movie_id+"'";
		ResultSet rs=st.executeQuery(query);
		int count=0;
		while(rs.next()){
			count=rs.getInt("count");
		}
		if(count==0){
			String sql="insert into Rating value('"+user_id+"','"+movie_id+"','"+rate+"')";
			st.execute(sql);
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("result", "insert");
			JSONArray array=new JSONArray();
			array.put(jsonObj);
			return array;
		}
		else{
			String sql="update Rating set Rating_value='"+rate+"' where User_id='"+user_id+"'and Movie_id='"+movie_id+"'";
			st.execute(sql);
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("result", "update");
			JSONArray array=new JSONArray();
			array.put(jsonObj);
			return array;
		}
	}
	
	/**
	 * 
	 * @param user_id
	 * @param movie_id
	 * @param rate_value
	 * @return
	 * @throws SQLException 
	 * @throws JSONException 
	 */
	public JSONArray insertRatingRecord(String user_id,String movie_id,String rate_value) throws SQLException, JSONException{
		mConnection=getConnection();
		st=(Statement) mConnection.createStatement();
		JSONArray array=new JSONArray();
		JSONObject jsonObj=new JSONObject();
		String query="select count(*) as Count from Waiting_list where User_id="+user_id+" and movie_id="+movie_id;
		ResultSet rs=st.executeQuery(query);
		int waitingcount=0;
		while(rs.next()){
			waitingcount=rs.getInt("Count");
		}
		if(waitingcount==1){
			query="delete from Waiting_list where User_id="+user_id+" and movie_id="+movie_id;
			st.execute(query);
		}
		query="select count(*) as Count from Rating where User_id="+user_id+" and movie_id="+movie_id;
		rs=st.executeQuery(query);
		int count=0;
		while(rs.next()){
			count=rs.getInt("Count");
		}
		if(count==0){
			query="insert into Rating value("+user_id+","+movie_id+","+rate_value+")";
			st.execute(query);
		}
		else{
			query="update Rating set Rating_value="+rate_value+" where User_id="+user_id+" and movie_id="+movie_id;
			st.execute(query);
		}
		jsonObj.put("result", "true");
		array.put(jsonObj);
		return array;
	}
	
	/**
	 * Insert or update a record into Rating table
	 * @param username Username from client
	 * @param moviename Movie_name from client
	 * @param rate Rate_value fomr client
	 * @return 
	 * @throws SQLException
	 */
	public boolean insertRating(String username, String moviename,String rate) throws SQLException{
		mConnection=getConnection();
			
				st = (Statement) mConnection.createStatement();
				String query="select User_id from User where User_name='"+username+"'";
				int user_id=0;
				int movie_id=0;
				ResultSet rs=st.executeQuery(query);
				while(rs.next()){
					user_id=rs.getInt("User_id");
				}
				query="select Movie_id from Movie where Movie_name='"+moviename+"'";
				rs=st.executeQuery(query);
				while(rs.next()){
					movie_id=rs.getInt("Movie_id");
				}
				query="select count(*) as count from Rating where User_id='"+user_id+"' and movie_id='"+movie_id+"'";
				rs=st.executeQuery(query);
				int count=0;
				while(rs.next()){
					count=rs.getInt("count");
				}
				if(count==0){
					query="insert into Rating value('"+user_id+"','"+movie_id+"','"+rate+"')";
					st.execute(query);
				}
				else{
					query="update Rating set Rating_value='"+rate+"' where User_id='"+user_id+"' and movie_id='"+movie_id+"'";
					st.execute(query);
				}
				return true;
	}
	
	/**
	 * Get User rated list by user_id from Rating table
	 * @param userid User_id
	 * @return JSONArray of rated list
	 * @throws JSONException 
	 * @throws SQLException 
	 */
	public JSONArray getRatingList(String userid) throws JSONException, SQLException{
		mConnection=getConnection();
		JSONArray array=new JSONArray();
		st = (Statement) mConnection.createStatement();
		String query="select * from Rating as R, movie as m where m.Movie_id=R.Movie_id and R.User_id="+userid;
		ResultSet rs=st.executeQuery(query);
		while(rs.next()){
				String movie_id=rs.getString("movie_id");
				String movie_description=rs.getString("movie_description");
				String movie_name=rs.getString("movie_name");
				String rating_value=rs.getString("Rating_value");
				JSONObject jsonObj=new JSONObject();
				jsonObj.put("id", movie_id);
				jsonObj.put("name", movie_name);
				jsonObj.put("description", movie_description);
				jsonObj.put("rating", rating_value);
				array.put(jsonObj);
		}
		
		return array;
		
	}
	/**
	 * 
	 * @param userid
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */
	public JSONArray getWaitingList(String userid) throws SQLException, JSONException{
		mConnection=getConnection();
		JSONArray array=new JSONArray();
		st = (Statement) mConnection.createStatement();
		String query="select * from Waiting_list as W, movie as m where m.Movie_id=W.Movie_id and W.User_id="+userid;
		ResultSet rs=st.executeQuery(query);
		while(rs.next()){
				String movie_id=rs.getString("movie_id");
				String movie_description=rs.getString("movie_description");
				String movie_name=rs.getString("movie_name");
				JSONObject jsonObj=new JSONObject();
				jsonObj.put("id", movie_id);
				jsonObj.put("name", movie_name);
				jsonObj.put("description", movie_description);
				array.put(jsonObj);
		}
		
		return array;
	}
	
	/**
	 * 
	 * @param userid
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */
	public ArrayList<String> getWaitingListString(String userid) throws SQLException, JSONException{
		mConnection=getConnection();
		st = (Statement) mConnection.createStatement();
		String query="select movie_id from Waiting_list where user_id="+userid;
		ResultSet rs=st.executeQuery(query);
		ArrayList<String> result=new ArrayList<String>();
		while(rs.next()){
				String movie_id=rs.getString("movie_id");
				result.add(movie_id);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param userid
	 * @return
	 * @throws JSONException
	 * @throws SQLException
	 */
	public JSONObject getMovie(String movieid) throws JSONException, SQLException{
		mConnection=getConnection();
		JSONArray array=new JSONArray();
		st = (Statement) mConnection.createStatement();
		String query="select * from movie where movie_id="+movieid;
		ResultSet rs=st.executeQuery(query);
		JSONObject jsonObj=new JSONObject();
		while(rs.next()){
				String movie_id=rs.getString("movie_id");
				String movie_description=rs.getString("movie_description");
				String movie_name=rs.getString("movie_name");
				jsonObj.put("id", movie_id);
				jsonObj.put("name", movie_name);
				jsonObj.put("description", movie_description);
		}
		
		return jsonObj;
		
	}
	/**
	 * 
	 * @param user_id
	 * @param movie_id
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */
	public JSONArray insertWaitingList(String user_id,String movie_id) throws SQLException, JSONException{
		mConnection=getConnection();
		JSONArray array=new JSONArray();
		st = (Statement) mConnection.createStatement();
		String query="insert into Waiting_List value("+user_id+","+movie_id+")";
		st.execute(query);
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("result", true);
		array.put(jsonObj);
		return array;
	}
	
	/**
	 * Build up connection to mysql database
	 * @return
	 */
	public Connection getConnection(){
		Connection con = null;
        try {  
            Class.forName("com.mysql.jdbc.Driver");
              
            con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/movie_rec", "root", "welcome");
              
        } catch (Exception e) {  
        	
        }  
        return con;
	}

}
