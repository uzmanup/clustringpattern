package utils;

import java.util.HashMap;
import java.util.List;
import data.Pattern;

public class ClusteringEvaluator {
	private HashMap<String, List<Pattern>> dataSet;
	private HashMap<String, List<Pattern>> clusteredDataSet;
	
	public ClusteringEvaluator(HashMap<String, List<Pattern>> dataSet, HashMap<String, List<Pattern>> clusteredDataSet) {
		this.dataSet = dataSet;
		this.clusteredDataSet = clusteredDataSet;
	}
	

}
