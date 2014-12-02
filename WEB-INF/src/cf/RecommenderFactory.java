package cf;

public class RecommenderFactory {
	public static final int PEARSON_RECOMMENDER=0;
	public static final int SPEARMAN_RECOMMENDER=1;
	public static UserRecommender getRecommender(int type){
		UserRecommender recommender = null;
		switch(type){
		case PEARSON_RECOMMENDER:
			recommender=new PearsonUserRecommender();
			break;
		case SPEARMAN_RECOMMENDER:
			recommender=new SpearmanCorrelationUserRecommender();
			break;
		}
		return recommender;
	}
}
