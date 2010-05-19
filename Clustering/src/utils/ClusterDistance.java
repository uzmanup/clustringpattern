package utils;
import data.Cluster;

public class ClusterDistance {

	private Cluster c1;
	private Cluster c2;
	private double distance;
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public ClusterDistance(Cluster c1 ,Cluster c2){
	this.c1=c1;
	this.c2=c2;
	}
	
	public Cluster getC1() {
		return c1;
	}
	public void setC1(Cluster c1) {
		this.c1 = c1;
	}
	public Cluster getC2() {
		return c2;
	}
	public void setC2(Cluster c2) {
		this.c2 = c2;
	}


	
	public boolean isLessThan(ClusterDistance distance){
		if(this.distance < distance.distance){
			return true;
		}
		return false;
	}
	
}
