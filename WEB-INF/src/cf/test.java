package cf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;

public class test {
	public static void main(String[] args) throws TasteException, IOException{
		
		SpearmanCorrelationUserRecommender a=new SpearmanCorrelationUserRecommender();
		double start=System.currentTimeMillis();
		System.out.println(a.getRecommend("100001", 5, 5));
		System.out.println((System.currentTimeMillis()-start)/1000);
		
	
	}
}
