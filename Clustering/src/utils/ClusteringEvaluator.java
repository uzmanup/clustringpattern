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
	private double p,r;
	
	public ClusteringEvaluator(HashMap<String, List<Pattern>> dataSet, HashMap<String, List<Pattern>> clusteredDataSet) {
		this.dataSet = dataSet;
		this.clusteredDataSet = clusteredDataSet;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------
	
	public double evaluate(){
		double semiFinalF=0.0;
		double totalAcc = 0;
		double FinalF=0.0;
		BuildConfusionMatrix();
		Iterator<String> matrixIterator=confusionMatrix.keySet().iterator();
		String classkey;
		long totalSize = 0;
		KF kf;double acctmp;
		System.out.println();
		System.out.println("======================================EVALUATION=============================");
		while(matrixIterator.hasNext()){
			
			classkey=matrixIterator.next();
			kf=getMaxKF((ArrayList<KF>) confusionMatrix.get(classkey));
			System.out.println();
			 System.out.println("P [ "+classkey+ " ] = "+kf.p+"     R [ "+classkey+ " ] = "+kf.r+"     F [ "+classkey+ " ] = "+kf.f +"    size ( "+classkey+" ) = "+dataSet.get(classkey).size()) ;
			acctmp=(double)clusteredDataSet.get(kf.k).size()/(double)(dataSet.get(classkey).size());
			 System.out.println("Acc [ "+classkey+" ]= "+acctmp);
			 System.out.println();
			 semiFinalF+=((dataSet.get(classkey).size()))*  kf.getF();
			 totalAcc+=(dataSet.get(classkey).size())* acctmp;
		}
		
		totalSize=getTotalSizeOfPatterns();
		System.out.println("total # of patterns = "+totalSize);
		System.out.println();
		FinalF=(semiFinalF / ( (double)totalSize ));
		totalAcc=totalAcc/(double)totalSize;
		System.out.println("Total Accurcy = "+ totalAcc);
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
		double f; KF mkf;
		datasetIterator1=dataSet.keySet().iterator();
		while(datasetIterator1.hasNext()){
			c=datasetIterator1.next();
			clusteringIterator=clusteredDataSet.keySet().iterator();
			classCluster=new ArrayList<KF>();
			while(clusteringIterator.hasNext()){
				k=clusteringIterator.next();
				f=calculateF(c, k);
				mkf=new KF(k,f,this.p,this.r);
				classCluster.add(mkf);
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
		this.p=P;
		this.r=R;
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
		private double f,p,r;
		public double getP() {
			return p;
		}

		public double getR() {
			return r;
		}

		private String k;
		
		public KF( String k,double f,double p,double r) {
			this.f=f;
			this.k=k;
			this.p=p;
			this.r=r;
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

		public void setP(double p) {
			this.p = p;
		}

		public void setR(double r) {
			this.r = r;
		}
		
	}
	
	
//------------------------------------------------------------------------------------------------------------------------------
	
	private KF getMaxKF (ArrayList<KF> list){
		double max=0.0;
		KF maxKF=new KF("",0.0,0,0);
		double currentF;double currentP;double currentR;
		String currentk;
		
		for (KF kf : list) {
			currentF=kf.getF();
			currentk=kf.getK();
			currentP=kf.getP();
			currentR=kf.getR();
			
			if(currentF > max){
				max=currentF;
				maxKF.setF(currentF);
				maxKF.setK(currentk);
				maxKF.setP(currentP);
				maxKF.setR(currentR);
			}
		}
		return maxKF;
	}

	
	
}
