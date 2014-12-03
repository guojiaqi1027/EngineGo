
package cf;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;


/**
 * PearsonUserRecommender Class
 * To find neighbors and items by Pearson Correlation Similarity using User-based Collaborative Filter algorithm with Apache Mahout
 * Extends from UserRecommender and Override initial methods
 * Last modify time 2014/12/1
 * @author GawainGuo
 * 
 */
public class PearsonUserRecommender extends UserRecommender{
	public PearsonUserRecommender(){
		super();
	}
	
	/**
	 * Override initialSimilarity method
	 * Using PearsonCorrelationSimilarity to initial Similarity 
	 */
	@Override
	public void initialSimilarity() throws TasteException{
		similarity=new PearsonCorrelationSimilarity(model,Weighting.WEIGHTED);
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
