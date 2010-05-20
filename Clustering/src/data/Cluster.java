package data;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	
	//Attributes
	private List<Pattern> patternsList;
	public List<Pattern> getPatternsList() {
		return patternsList;
	}

	private List<Cluster> clusterList;
	
	public Cluster() {
		this.patternsList=new ArrayList<Pattern>();
		this.clusterList=new ArrayList<Cluster>();
	}

	public List<Cluster> getClusterList() {
		return clusterList;
	}
	
	public List<Pattern> getPatterns(){
		return this.patternsList;
	}

	
}