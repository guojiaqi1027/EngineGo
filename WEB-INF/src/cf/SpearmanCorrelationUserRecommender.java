package cf;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.SpearmanCorrelationSimilarity;

/**
 * SpearmanCorrelationUserRecommender Class
 * To find neighbors and items by Spearman Correlation Similarity using User-based Collaborative Filter algorithm with Apache Mahout
 * Extends from UserRecommender and Override initial methods
 * Last modify time 2014/12/1
 * @author GawainGuo
 * 
 */
public class SpearmanCorrelationUserRecommender extends UserRecommender{
	public SpearmanCorrelationUserRecommender(){
		super();
	}
	
	

	@Override
	public void initialSimilarity() throws TasteException{
		similarity=new SpearmanCorrelationSimilarity(model);
	}
	
	@Override
	public void initialNeighborhood(int n) throws TasteException{
		neighborhood=new NearestNUserNeighborhood(n,similarity,model);
	}
	@Override
	public void initialNeighborhood(float value){
		neighborhood=new ThresholdUserNeighborhood(value,similarity,model);
	}
}
