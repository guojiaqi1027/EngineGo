package cf;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.mahout.cf.taste.common.NoSuchItemException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.ByValueRecommendedItemComparator;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.ItemAverageRecommender;
import org.apache.mahout.cf.taste.impl.recommender.TopItems;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.IDRescorer;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class CFTest {
	public static void main(String[] args){
		double start=System.currentTimeMillis();
		MysqlDataSource dataSource=new MysqlDataSource();
		dataSource.setServerName("localhost");
		dataSource.setUser("root");
		dataSource.setPassword("welcome");
		dataSource.setDatabaseName("movie_rec");
		JDBCDataModel model=new MySQLJDBCDataModel(dataSource,"Rating","User_id","Movie_id","Rating_value", null);
		System.out.println((System.currentTimeMillis()-start)/1000);
		try {
			UserSimilarity similarity=new SpearmanCorrelationSimilarity(model);
			//UserSimilarity similarity=new PearsonCorrelationSimilarity(model,Weighting.WEIGHTED);
			System.out.println("Similarity:"+(System.currentTimeMillis()-start)/1000);
			//UserSimilarity similarity=new EuclideanDistanceSimilarity(model);
			/*
			
			for(int i=0;i<neighborhood.getUserNeighborhood(100003).length;i++){
				System.out.println("neighbor:"+neighborhood.getUserNeighborhood(100003)[i]);
			}*/
			
			
			UserNeighborhood neighborhood=new NearestNUserNeighborhood(5,similarity,model);
			//UserNeighborhood neighborhood=new ThresholdUserNeighborhood(0.3,similarity,model);
			for(int i=0;i<neighborhood.getUserNeighborhood(100000).length;i++){
				System.out.println("neighbor:"+neighborhood.getUserNeighborhood(100000)[i]);
			}
			System.out.println((System.currentTimeMillis()-start)/1000);
			
			Recommender recommender=new GenericUserBasedRecommender(model,neighborhood,similarity);
			List<RecommendedItem> list=recommender.recommend(100000, 5);
			System.out.println((System.currentTimeMillis()-start)/1000);
			System.out.println(list.size());
			for(int i=0;i<list.size();i++){
				System.out.println(list.get(i));
			}
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
