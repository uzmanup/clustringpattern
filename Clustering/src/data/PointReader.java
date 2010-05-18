package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import Jama.Matrix;
import data.Pattern;

public class PointReader {
	private int dimensions;
	private double[] minVector;
	private double[] maxVector;
	
	public PointReader() {
		
	}

	public HashMap<String, List<Pattern>> read(String fileURL, boolean calcMINMAX){
		HashMap<String, List<Pattern>> pointsMap = new HashMap<String, List<Pattern>>();
		File file = new File(fileURL);
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = 0;
		double maxY = 0;
		
		if(calcMINMAX){
			minVector = new double[2];
			maxVector = new double[2];
		}
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
            	String line = scanner.nextLine();
                String[] tokens = line.split("\t");
                if(tokens.length == 0)
                	break;
                this.dimensions = tokens.length - 1;
                String classLabel = tokens[0];
                double[] vector = new double[dimensions];
                for(int i = 0; i < dimensions; i++){
                	vector[i] = Double.parseDouble(tokens[i + 1]);
                }
                if(calcMINMAX){
            		double x = vector[0];
            		double y = vector[1];
            		if(x < minX){
            			minX = x;
            			minVector[0] = x;
            		}else if( x > maxX){
            			maxX = x;
            			maxVector[0] = x;
            		}
            		if(y < minY){
            			minY = y;
            			minVector[1] = y;
            		}else if( y > maxY){
            			maxY = y;
            			maxVector[1] = y;
            		}
            	}
                Pattern pattern = new Pattern(vector, classLabel);
                if(!pointsMap.containsKey(classLabel))
                	pointsMap.put(classLabel, new ArrayList<Pattern>());
                pointsMap.get(classLabel).add(pattern);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		return pointsMap;

	}
	
	public int getDimensions() {
		return this.dimensions;
	}
	
	public double[] getMinVector() {
		return minVector;
	}

	public double[] getMaxVector() {
		return maxVector;
	}

	public static void main(String[] args) {
		Matrix m = new Matrix(new double[]{1, 2, 3, 4, 2}, 1);
		System.out.println(m.getRowDimension() + " x " + m.getColumnDimension());
		m.print(5, 5);
		System.out.println("Norms: " + m.norm1() + "   " + m.norm2() + "   " + m.normF() + "   " + m.normInf());
	}
}

