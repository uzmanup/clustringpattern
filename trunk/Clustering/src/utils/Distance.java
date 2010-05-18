package utils;

import data.Pattern;
import Jama.Matrix;

public class Distance{
	private Pattern srcPattern;
	private Pattern destPattern;
	private double distance;
	
	public Distance(Pattern srcPattern, Pattern destPattern) {
		this.srcPattern = srcPattern;
		this.destPattern = destPattern;
		calculateDistance();
	}
	
	private void calculateDistance() {
		this.distance = calculateDistance(srcPattern, destPattern);
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	public Pattern getDestination() {
		return this.destPattern;
	}
	
	public static double calculateDistance(Pattern srcPattern, Pattern destPattern) {
		Matrix srcFeatureVector = srcPattern.getFeatureVector();
		Matrix destFeatureVector = destPattern.getFeatureVector();
		double patternDistance = calculateDistance(srcFeatureVector, destFeatureVector);
		return patternDistance;
	}
	
	public static double calculateDistance(Matrix srcVector, Matrix destVector) {
		Matrix differenceVector = destVector.minus(srcVector);
		double patternDistance = differenceVector.normF();
		return patternDistance;
	}
}
