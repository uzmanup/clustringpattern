package data;

import Jama.Matrix;

public class Pattern {
	
	protected double [] features;
	private String classLabel;
	private String testClass;
	
	public Pattern(double [] features, String classLabel) {
		this.features = features;
		this.classLabel = classLabel;
	}
	
	/**
	 * Get the feature vector of the pattern
	 * @return the <b>row feature vector</b> of the pattern
	 */
	public Matrix getFeatureVector(){
		return new Matrix(features, 1);
	}
	
	public String getClassLabel(){
		return this.classLabel;
	}
	
	public void setTestCluster(String c){
		this.testClass = c;
	}

	public String getTestCluster() {
		return testClass;
	}
	
	public void setFeatures(double[] features){
		this.features = features;
	}
}
