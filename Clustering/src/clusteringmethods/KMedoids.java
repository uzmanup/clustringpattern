package clusteringmethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import utils.Distance;

import data.Pattern;

public class KMedoids extends ClusteringAlgorithm {
	private int k;
	private Pattern[] medoids;
	
	public KMedoids(HashMap<String, List<Pattern>> dataSet, int k) {
		super(dataSet);
		this.k = k;
	}

	@Override
	public HashMap<String, List<Pattern>> cluster() {
		HashMap<String, List<Pattern>> clusteredPatterns = new HashMap<String, List<Pattern>>();
		medoids = chooseInitialMedoids();
		
		Pattern[] newMedoids;
		do{
			for(Pattern medoid : medoids){
				clusteredPatterns.put(medoid.getTestCluster(), new ArrayList<Pattern>());
			}
			Iterator<String> classItr = dataSet.keySet().iterator();
			while(classItr.hasNext()){
				List<Pattern> classPatterns = dataSet.get(classItr.next());
				for(Pattern p : classPatterns){
					double minDistance = Double.MAX_VALUE;
					
					for(Pattern medoid : medoids){
						double distance = Distance.calculateDistance(p, medoid);
						if(distance < minDistance){
							minDistance = distance;
							p.setTestCluster(medoid.getTestCluster());
						}
					}
					clusteredPatterns.get(p.getTestCluster()).add(p);
				}
			}
			newMedoids = calculateNewMedoids(clusteredPatterns);
		}while(!stopIterations(newMedoids));
		
		return clusteredPatterns;
	}

	private Pattern[] calculateNewMedoids(HashMap<String, List<Pattern>> clusteredPatterns) {
		Pattern[] newMedoids = new Pattern[k];
		
		for(int i = 0; i < medoids.length; i++){
			Pattern medoid = medoids[i];
			String medoidCluster = medoid.getTestCluster();
			List<Pattern> clusterPatterns = clusteredPatterns.get(medoidCluster);
			newMedoids[i] = getMedoid(clusterPatterns); 
		}
		return newMedoids;
	}

	private Pattern getMedoid(List<Pattern> clusterPatterns) {
		double minAvgDistance = Double.MAX_VALUE;
		Pattern medoid = null;
		for(Pattern candidatePattern : clusterPatterns){
			double avgDistance = 0;
			for(Pattern clusterPattern : clusterPatterns){
				avgDistance += Distance.calculateDistance(candidatePattern, clusterPattern);
			}
			avgDistance /= clusterPatterns.size();
			if(avgDistance < minAvgDistance){
				minAvgDistance = avgDistance;
				medoid = candidatePattern;
			}
		}
		return medoid;
	}

	private boolean stopIterations(Pattern[] newMedoids) {
		for(int i = 0; i < medoids.length; i++){
			if(newMedoids[i] != medoids[i]){
				return false;
			}
		}
		return true;
	}

	private Pattern[] chooseInitialMedoids() {
		List<Pattern> medoids = new ArrayList<Pattern>();
		
		List<Pattern> patternList = new ArrayList<Pattern>();
		for(String classLabel : dataSet.keySet()){
			patternList.addAll(dataSet.get(classLabel));
		}
		Pattern medoidPattern = getFarthestPattern(patternList, patternList);
		medoids.add(medoidPattern);
		medoidPattern.setTestCluster("1");
		for(int i = 0; i < k - 1; i++){
			medoidPattern = getFarthestPattern(patternList, medoids);
			medoids.add(medoidPattern);
			medoidPattern.setTestCluster(( i + 2) + "");
		}
		Pattern[] medoidPatterns = new Pattern[k];
		for(int i = 0; i < k; i++){
			Pattern p = medoids.get(i);
			medoidPatterns[i] = p;
		}
		return medoidPatterns;
	}
	
	private Pattern getFarthestPattern(List<Pattern> allPatterns, List<Pattern> fromPatterns){
		Pattern farthestPattern = null;
		double maxTotalDistance = 0;
		for(Pattern candidatePattern : allPatterns){
			double totalDistance = 0;
			for(Pattern pattern : fromPatterns){
				double distance = Distance.calculateDistance(candidatePattern, pattern);
				totalDistance += distance;
			}
			if(totalDistance > maxTotalDistance){
				maxTotalDistance = totalDistance;
				farthestPattern = candidatePattern;
			}
		}
		return farthestPattern;
	}
	
}
