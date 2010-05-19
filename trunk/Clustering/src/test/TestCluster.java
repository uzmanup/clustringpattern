package test;

import utils.PropertyManager;

public class TestCluster {

	public static void main(String[] args) {
		System.out.println(PropertyManager.getInstance().getProperty("DBSCAN"));
	}
	
}
