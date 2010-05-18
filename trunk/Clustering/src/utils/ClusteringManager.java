package utils;

import java.util.HashMap;
import java.util.List;

import clusteringmethods.ClusteringAlgorithm;

import data.Pattern;

public class ClusteringManager {
	private HashMap<String, List<Pattern>> dataSet;
	private HashMap<String, List<Pattern>> clusteredDataSet;
	private ClusteringAlgorithm clusteringAlgorithm;
	private static ClusteringManager managerInstance;
	
	public static ClusteringManager getInstance(){
		if(managerInstance == null){
			managerInstance = new ClusteringManager();
		}
		return managerInstance;
	}
	
	private ClusteringManager() {
		
	}

}
