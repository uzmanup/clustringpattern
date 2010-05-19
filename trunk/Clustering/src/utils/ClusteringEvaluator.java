package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import data.Pattern;

public class ClusteringEvaluator {
	//attributes
	private HashMap<String, List<Pattern>> dataSet;
	private HashMap<String, List<Pattern>> clusteredDataSet;
	private Iterator<String> datasetIterator1;
	private Iterator<String> clusteringIterator;
	private HashMap<String, List<KF>> confusionMatrix;
	
	
	public ClusteringEvaluator(HashMap<String, List<Pattern>> dataSet, HashMap<String, List<Pattern>> clusteredDataSet) {
		this.dataSet = dataSet;
		this.clusteredDataSet = clusteredDataSet;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------
	
	public double evaluate(){
		double semiFinalF=0.0;
		double FinalF=0.0;
		BuildConfusionMatrix();
		Iterator<String> matrixIterator=confusionMatrix.keySet().iterator();
		String classkey;
		long totalSize = 0;
		
		while(matrixIterator.hasNext()){
			classkey=matrixIterator.next();
			semiFinalF+=((dataSet.get(classkey).size()))*(getMaxKF((ArrayList<KF>) confusionMatrix.get(classkey)).getF());
			
		}
		totalSize=getTotalSizeOfPatterns();
		FinalF=(semiFinalF / ( (double)totalSize ));
		return FinalF;
	}

	//------------------------------------------------------------------------------------------------------------------------------
	
	private long getTotalSizeOfPatterns (){
		String c;
		datasetIterator1=dataSet.keySet().iterator();
		long total=0;
		while(datasetIterator1.hasNext()){
			c=datasetIterator1.next();
			total+=dataSet.get(c).size();
		}
		return total;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------	
	
	private void BuildConfusionMatrix(){
		String c,k;
		confusionMatrix=new HashMap<String, List<KF>>();
		List<KF> classCluster;
		
		datasetIterator1=dataSet.keySet().iterator();
		while(datasetIterator1.hasNext()){
			c=datasetIterator1.next();
			clusteringIterator=clusteredDataSet.keySet().iterator();
			classCluster=new ArrayList<KF>();
			while(clusteringIterator.hasNext()){
				k=clusteringIterator.next();
				classCluster.add(new KF(k,calculateF(c, k)));
			}
			confusionMatrix.put(c, classCluster);
		}
		
	}
	
	//------------------------------------------------------------------------------------------------------------------------------
	
	private double calculateF(String c , String k){
		double P = 0;double R=0;
		long sizeOfC=dataSet.get(c).size();
		long sizeOfK=clusteredDataSet.get(k).size();
		long intersection=getClassClusterIntersection(c, k);
		
		P=((double)intersection)/ ((double)sizeOfK);
		R=((double)intersection)/( (double)sizeOfC);
		
		return ((2*P*R)/(P+R));
	}
	
	
	//------------------------------------------------------------------------------------------------------------------------------	
	
	private long getClassClusterIntersection (String c, String k){
		//#(c,k)
		return getPatternIntersection(dataSet.get(c), clusteredDataSet.get(k));
	}

	//------------------------------------------------------------------------------------------------------------------------------	
	
	private long getPatternIntersection (List<Pattern> a,List<Pattern> b ) {
		long counter = 0;
		if(a.size()>=b.size()){
			for (Pattern pattern : a) {
				if(b.contains(pattern)) counter++;
			}
		}
		else{
			for (Pattern pattern : b) {
			if(a.contains(pattern)) counter++;	
			}
		}
		return counter;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------	
	
	//inner class F,k
	class KF {
		private double f;
		private String k;
		
		public KF( String k,double f) {
			this.f=f;
			this.k=k;
		}
		
		public double getF() {
			return f;
		}
		
		public String getK() {
			return k;
		}

		public void setF(double f) {
			this.f = f;
		}

		public void setK(String k) {
			this.k = k;
		}
		
	}
	
	
//------------------------------------------------------------------------------------------------------------------------------
	
	private KF getMaxKF (ArrayList<KF> list){
		double max=0.0;
		KF maxKF=new KF("",0.0);
		double currentF;
		String currentk;
		
		for (KF kf : list) {
			currentF=kf.getF();
			currentk=kf.getK();
			
			if(currentF > max){
				max=currentF;
				maxKF.setF(currentF);
				maxKF.setK(currentk);
			}
		}
		return maxKF;
	}
	
}
