package clusteringmethods;

import java.util.ArrayList;
import java.util.HashMap;
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
		HashMap<String, List<Pattern>> clusteredPatterns;
		medoids = chooseInitialMedoids();
		
		List<Pattern> patternList = new ArrayList<Pattern>();
		for(String classLabel : dataSet.keySet()){
			patternList.addAll(dataSet.get(classLabel));
		}
		
		int iterations = 0;
		Pattern[] newMedoids;
		do{
			iterations++;
			clusteredPatterns = new HashMap<String, List<Pattern>>();
			for(Pattern medoid : medoids){
				clusteredPatterns.put(medoid.getTestCluster(), new ArrayList<Pattern>());
				clusteredPatterns.get(medoid.getTestCluster()).add(medoid);
			}
			for(Pattern p : patternList){
				double minDistance = Double.MAX_VALUE;
				for(Pattern medoid : medoids){
					if(!containsPattern(medoids, p)){
						double distance = Distance.calculateDistance(p, medoid);
						if(distance < minDistance){
							minDistance = distance;
							p.setTestCluster(medoid.getTestCluster());
						}
					}
				}
				clusteredPatterns.get(p.getTestCluster()).add(p);
			}
			newMedoids = calculateNewMedoids(clusteredPatterns);
		}while(!stopIterations(newMedoids));
		
		System.out.println("Number of Iterations: " + iterations);
		return clusteredPatterns;
	}

	private Pattern[] calculateNewMedoids(HashMap<String, List<Pattern>> clusteredPatterns) {
		Pattern[] newMedoids = new Pattern[k];
		
		int i = 0;
		for(String cluster : clusteredPatterns.keySet()){
			newMedoids[i] = getMedoid(clusteredPatterns.get(cluster));
			newMedoids[i].setTestCluster(i + "");
			i++;
		}
		
		return newMedoids;
	}

	private Pattern getMedoid(List<Pattern> clusterPatterns) {
		double minTotalDistance = Double.MAX_VALUE;
		Pattern medoid = null;
		for(Pattern candidatePattern : clusterPatterns){
			double TotalDistance = 0;
			for(Pattern clusterPattern : clusterPatterns){
				TotalDistance += Distance.calculateDistance(candidatePattern, clusterPattern);
			}
			if(TotalDistance < minTotalDistance){
				minTotalDistance = TotalDistance;
				medoid = candidatePattern;
			}
		}
		return medoid;
	}

	private boolean stopIterations(Pattern[] newMedoids) {
		boolean stopIterations = true;
		
		for(int i = 0; i < k; i++){
			if(!containsPattern(newMedoids, medoids[i])){
				stopIterations = false;
			}
			medoids[i] = newMedoids[i];
			medoids[i].setTestCluster(i + "");
		}
		
		return stopIterations;
	}
	
	private boolean containsPattern(Pattern[] patterns, Pattern pattern){
		for(Pattern p : patterns){
			if(p == pattern)
				return true;
		}
		return false;
	}
	
	private Pattern[] chooseInitialMedoids() {
//		Pattern[] medoidPatterns = new Pattern[k];
//		List<Pattern> medoids = new ArrayList<Pattern>();
//		
//		List<Pattern> patternList = new ArrayList<Pattern>();
//		for(String classLabel : dataSet.keySet()){
//			patternList.addAll(dataSet.get(classLabel));
//		}
//		Pattern medoidPattern = getFarthestPattern(patternList, patternList);
//		medoids.add(medoidPattern);
//		medoidPattern.setTestCluster("1");
//		for(int i = 0; i < k - 1; i++){
//			medoidPattern = getFarthestPattern(patternList, medoids);
//			medoids.add(medoidPattern);
//			medoidPattern.setTestCluster(( i + 2) + "");
//		}
//		for(int i = 0; i < k; i++){
//			Pattern p = medoids.get(i);
//			medoidPatterns[i] = p;
//		}
		
		
		List<Pattern> patternList = new ArrayList<Pattern>();
		for(String classLabel : dataSet.keySet()){
			patternList.addAll(dataSet.get(classLabel));
		}
		
		Pattern[] medoidPatterns = new Pattern[k];
		for(int i = 0; i < k; i++){
			medoidPatterns[i] = patternList.get((int) (Math.random() * patternList.size()));
			medoidPatterns[i].setTestCluster(i + "");
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
