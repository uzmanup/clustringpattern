package clusteringmethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Distance;

import data.Pattern;

public class DBSCAN extends ClusteringAlgorithm {
	private double eps;
	private int minPts;
	
	
	
	public DBSCAN(HashMap<String, List<Pattern>> dataSet, double eps, int minPts) {
		super(dataSet);
		this.eps = eps;
		this.minPts = minPts;
	}

	@Override
	public HashMap<String, List<Pattern>> cluster() {
		int clusterID = nextClusterID();
		HashMap<String, List<Pattern>> clusteredPatterns = new HashMap<String, List<Pattern>>();
		List<Pattern> patternList = new ArrayList<Pattern>();
		for(String classLabel : dataSet.keySet()){
			patternList.addAll(dataSet.get(classLabel));
		}
		
		for(Pattern pattern : patternList){
			if(pattern.getTestCluster() == null){
				if(expandCluster(clusteredPatterns, patternList, pattern, clusterID)){
					clusterID = nextClusterID();
				}
			}
		}
		return clusteredPatterns;
	}

	private boolean expandCluster(HashMap<String, List<Pattern>> clusteredPatterns, List<Pattern> patternList, Pattern srcPattern, int clusterID) {
		clusteredPatterns.put(clusterID+"", new ArrayList<Pattern>());
		List<Pattern> seeds = regionQuery(patternList, srcPattern);
		if(seeds.size() < minPts){
			srcPattern.setTestCluster("noise");
			return false;
		}
		changeClusterIDs(seeds, clusterID);
		clusteredPatterns.get(clusterID+"").addAll(seeds);
		
		while(seeds.size() > 0){
			Pattern currentP = seeds.get(0);
			List<Pattern> curPatternResults = regionQuery(patternList, currentP);
			if(curPatternResults.size() >= minPts){
				for(Pattern resultP : curPatternResults){
					if(resultP.getTestCluster() == null || resultP.getTestCluster().equals("noise")){
						resultP.setTestCluster(clusterID + "");
						clusteredPatterns.get(clusterID+"").add(resultP);
						if(resultP.getTestCluster() == null){
							seeds.add(resultP);
						}
					}
				}

			}
			seeds.remove(currentP);
		}
		return true;
	}
	
	private List<Pattern> regionQuery(List<Pattern> patternList, Pattern centerPattern){
		List<Pattern> regionPattern = new ArrayList<Pattern>();
		for(Pattern pattern : patternList){
			if(Distance.calculateDistance(centerPattern, pattern) <= eps){
				regionPattern.add(pattern);
			}
		}
		
		return regionPattern;
	}
	
	private void changeClusterIDs(List<Pattern> patternList, int clusterID){
		for(Pattern p : patternList){
			p.setTestCluster(clusterID + "");
		}
	}

}
