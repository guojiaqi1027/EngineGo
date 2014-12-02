package cf;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
/**
 * UserRecommender Abstract Class
 * General User Recommender Class defines getRecommend() methods
 * Extends to SpearmanCorrelationUserRecommender and PearsonUserRecommender
 * @author GawainGuo
 * Last modify time 2014/12/1
 */
public abstract class UserRecommender {
	JDBCDataModel model;
	UserSimilarity similarity;
	UserNeighborhood neighborhood;
	
	/**
	 * Get Recommendation with NearestNUserNeighborhood 
 	 * @param id User_id from input
	 * @param n Amount of neighbors
	 * @param recommend_amount Amount of recommending result
	 * @return
	 * @throws TasteException
	 */
	public ArrayList<Long> getRecommend(String id, int n,int recommend_amount) throws TasteException{
		initialModel();
		initialSimilarity();
		initialNeighborhood(n);
		Recommender recommender=new GenericUserBasedRecommender(model,neighborhood,similarity);
		List<RecommendedItem> list=recommender.recommend(Long.valueOf(id), recommend_amount);
		ArrayList<Long> result=new ArrayList<Long>();
		for(int i=0;i<list.size();i++){
			result.add(list.get(i).getItemID());
		}
		return result;
		
	}
	/**
	 * Get Recommendation with ThresholdUserNeighborhood 
	 * @param id User_id from input
	 * @param value Distance of neighbor range
	 * @param recommend_amount Amount of recommending result
	 * @return
	 * @throws TasteException
	 */
	public ArrayList<Long> getRecommend(String id, float value,int recommend_amount) throws TasteException{
		initialModel();
		initialSimilarity();
		initialNeighborhood(value);
		Recommender recommender=new GenericUserBasedRecommender(model,neighborhood,similarity);
		List<RecommendedItem> list=recommender.recommend(Long.valueOf(id), recommend_amount);
		ArrayList<Long> result=new ArrayList<Long>();
		for(int i=0;i<list.size();i++){
			result.add(list.get(i).getItemID());
		}
		return result;
		
	}
	
	/**
	 * Initial Data model with JDBC
	 */
	public void initialModel(){
		MysqlDataSource dataSource=new MysqlDataSource();
		dataSource.setServerName("localhost");
		dataSource.setUser("root");
		dataSource.setPassword("welcome");
		dataSource.setDatabaseName("movie_rec");
		model=new MySQLJDBCDataModel(dataSource,"Rating","User_id","Movie_id","Rating_value", null);
	}
	public void initialSimilarity() throws TasteException{
		
	}
	public void initialNeighborhood(int n) throws TasteException{
		
	}
	public void initialNeighborhood(float value) throws TasteException{
		
	}
}
