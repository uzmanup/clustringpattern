package clusteringmethods;

import java.util.HashMap;
import java.util.List;

import data.Cluster;
import data.Pattern;

public abstract class ClusteringAlgorithm {
	protected HashMap<String, List<Pattern>> dataSet;
	
	public ClusteringAlgorithm(HashMap<String, List<Pattern>> dataSet) {
		this.dataSet = dataSet;
	}
	
	public abstract HashMap<String, List<Pattern>> cluster();
	
	
}
