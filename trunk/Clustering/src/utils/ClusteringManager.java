package utils;

import java.util.HashMap;
import java.util.List;

import clusteringmethods.ClusteringAlgorithm;
import clusteringmethods.DBSCAN;
import clusteringmethods.KMedoids;
import clusteringmethods.SingleLinkage;

import data.Pattern;
import data.PointReader;

public class ClusteringManager {
	private HashMap<String, List<Pattern>> dataSet;
	private HashMap<String, List<Pattern>> clusteredDataSet;
	private static ClusteringManager managerInstance;
	private static PointReader reader=new PointReader();
	private static Visualizer visualizer;
	private static ClusteringEvaluator evaluator;
	
	public static ClusteringManager getInstance(){
		if(managerInstance == null){
			managerInstance = new ClusteringManager();
		}
		return managerInstance;
	}
	
	private ClusteringManager() {
		
	}
	
	private String Dataset ,ClusteringAlgorithm;
	private double Eps;
	private  int minPts,K;
	
	public void clusterData() {
		//get options from properties file
		
		dataSet=reader.read(Dataset, false);
		getValuse();
		String title ="";
		if(ClusteringAlgorithm.equals("DBSCAN")){
			clusteredDataSet=new DBSCAN(dataSet,Eps,minPts).cluster();
			title ="DBSCAN Clustering";
		}
		else if (ClusteringAlgorithm.equals("KM")){
			title ="KMedoids Clustering";
			clusteredDataSet=new KMedoids(dataSet,K).cluster();
		}
		else if (ClusteringAlgorithm.equals("SL")){
		//Single Linkage
			title ="SingleLinkage Clustering";
//			clusteredDataSet=new SingleLinkage(dataSet).cluster(K);
		}
		else {
			System.err.println("Error in the algorithm name");
		}
		
		visualizer=new Visualizer(title);
		visualizer.visualize(clusteredDataSet,false);
		
		evaluator=new ClusteringEvaluator(dataSet,clusteredDataSet);
		
		System.out.println("Evaluation : F = "+evaluator.evaluate());
		
	}
	
	private void getValuse(){
		PropertyManager manager =PropertyManager.getInstance();
		Dataset=manager.getProperty("Dataset");
		ClusteringAlgorithm =manager.getProperty("clusteringAlgorithm");
		Eps=Double.parseDouble(manager.getProperty("Eps"));
		minPts=Integer.parseInt(manager.getProperty("minPts"));
		K=Integer.parseInt(manager.getProperty("K"));
		
	}

}
