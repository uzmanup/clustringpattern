package clusteringmethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import utils.ClusterDistance;
import utils.Distance;
import data.Cluster;
import data.Pattern;

public class SingleLinkage {

	private HashMap<String, List<Pattern>> dataSet;
	private HashMap<String, List<Pattern>> clusterResult;
	private Iterator<String> iterator;
	private List<Cluster> clusters;
	
	public SingleLinkage(HashMap<String, List<Pattern>> dataSet) {
		this.dataSet = dataSet;
	}

	
	public HashMap<String, List<Pattern>> cluster(int k) {
		clusterResult=new HashMap<String, List<Pattern>>();
		Cluster merged = null;
		ClusterDistance smallestPair;
		initialize();
		
		while (clusters.size() > k) {
				smallestPair=getSmallestPair();
				merged = mergerClusters(smallestPair.getC1(),smallestPair.getC2());
				this.clusters.remove(smallestPair.getC1());
				this.clusters.remove(smallestPair.getC2());
				this.clusters.add(merged);
			
		}
		
		int counter =1;
		for (Cluster cluster : clusters) {
			clusterResult.put(counter+"",cluster.getPatterns());
			counter++;
		}
		
		return clusterResult;
	}
	
	
	//------------------------------------------------------------------------------------------------------------------------------
	
	private void initialize(){

		clusters = new ArrayList<Cluster>();
		iterator = dataSet.keySet().iterator();
		String key;
		List<Pattern> patternlist;
		List<Cluster> clusters = new ArrayList<Cluster>();
		Cluster c;

		// building initial list of clusters
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			patternlist = dataSet.get(key);
			
			for (Pattern pattern : patternlist) {
				// creat a cluster of each pattern
				c = new Cluster();
				c.getPatternsList().add(pattern);
				clusters.add(c);
			}
		}
		
	}
	
	
	//------------------------------------------------------------------------------------------------------------------------------
	
	private Cluster mergerClusters(Cluster c1, Cluster c2) {
		Cluster mergedCluster = new Cluster();
		mergedCluster.getClusterList().add(c1);
		mergedCluster.getClusterList().add(c2);

		return mergedCluster;
	}

	
	//------------------------------------------------------------------------------------------------------------------------------
	
	private ClusterDistance getNearstCluster(Cluster target) {
		Cluster nearstCluster = null;
		double minDistance = Double.MAX_VALUE;
		double currentDistance;

		for (Cluster cluster : clusters) {
			currentDistance = getMinDistance(target, cluster);
			if (currentDistance < minDistance) {
				minDistance = currentDistance;
				nearstCluster = cluster;
			}
		}
		ClusterDistance distance=new ClusterDistance(target, nearstCluster);
		distance.setDistance(minDistance);
		
		return distance;
	}

	
	
	//------------------------------------------------------------------------------------------------------------------------------
	private double getMinDistance(Cluster c1, Cluster c2) {
		double min = Double.MAX_VALUE;
		double currentDistance;
		for (Pattern p1 : c1.getPatterns()) {
			for (Pattern p2 : c2.getPatterns()) {
				currentDistance = Distance.calculateDistance(p1, p2);
				if (currentDistance < min) {
					min = currentDistance;
				}
			}
		}
		return min;
	}
	
	
	//------------------------------------------------------------------------------------------------------------------------------
	
	private ClusterDistance getSmallestPair(){
		
		ClusterDistance minPairDistance=new ClusterDistance(null,null);
		minPairDistance.setDistance(Double.MAX_VALUE);
		
		ClusterDistance currentPairDistance=null;
		
		for (Cluster cluster : clusters) {
			currentPairDistance=getNearstCluster(cluster);
			
			if(currentPairDistance.isLessThan(minPairDistance)){
				minPairDistance.setC1(currentPairDistance.getC1());
				minPairDistance.setC2(currentPairDistance.getC2());
				minPairDistance.setDistance(currentPairDistance.getDistance());
						
			}
	}
		
		return minPairDistance;
	}


}

