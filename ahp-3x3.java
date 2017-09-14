// http://ideone.com/TUoWs4

/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Ideone
{
	public static final double RI = 0.58;
	
	public static void main (String[] args) throws java.lang.Exception
	{
		double[][] weights = new double[][] {
			new double[] { 1.0, 		5.0, 			7.0 },	
			new double[] { 1.0 / 5.0,	1.0, 			5.0 },	
			new double[] { 1.0 / 7.0, 	1.0 / 5.0,		1.0 }	
		};
		
		int n = weights.length;
		
		double[] c = new double[] {
			columnSum(weights, 0),
			columnSum(weights, 1),
			columnSum(weights, 2)
		};
		
		double[][] normalizedWeights = normalize(weights, c);
		
		double[] s = new double[] {
			rowSum(normalizedWeights, 0) / ((double) n),
			rowSum(normalizedWeights, 1) / ((double) n),
			rowSum(normalizedWeights, 2) / ((double) n)
		};
		
		double consistency = consistency(c, s);
		System.out.println("Consistency: " + consistency);
		
		if (consistency > 0.1) { 
		
			s = new double[] {
				priority(weights, 0),
				priority(weights, 1),
				priority(weights, 2)
			};
		
			consistency = consistency(c, s);
			System.out.println("New consistency (1): " + consistency);
		
			s = new double[] {
				priority2(weights, 0),
				priority2(weights, 1),
				priority2(weights, 2)
			};
		
			consistency = consistency(c, s);
			System.out.println("New consistency (2): " + consistency);
		}
		
	}
	
	private static double rowSum(double[][] weights, int row) {
		double sum = 0.0;
		for (int j = 0; j < weights[0].length; j++) {
			sum += weights[row][j];
		}
		return sum;
	}
	
	private static double columnSum(double[][] weights, int column) {
		double sum = 0.0;
		for (int i = 0; i < weights.length; i++) {
			sum += weights[i][column];
		}
		return sum;
	}
	
	private static double[][] normalize(double[][] weights, double[] c) {
		double[][] newWeights = new double[weights.length][weights[0].length];
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[0].length; j++) {
				newWeights[i][j] = weights[i][j] / c[j];
			}
		}
		return newWeights;
	}
	
	private static double priority(double[][] weights, int index) {
		double sum = 0.0;
		for (int i = 0; i < weights.length; i++) {
			sum += rowProduct(weights, i);
		}
		return rowProduct(weights, index) / sum;
	}
	
	private static double rowProduct(double[][] weights, int row) {
		double product = 1.0;
		int n = weights[0].length;
		for (int j = 0; j < n; j++) {
			product *= weights[row][j] / weights[j][row];
		}
		return Math.pow(product, 1.0 / (2.0 * n));
	}
	
	private static double priority2(double[][] weights, int index) {
		double sum = 0.0;
		for (int i = 0; i < weights.length; i++) {
			sum += rowProduct2(weights, i);
		}
		return rowProduct2(weights, index) / sum;
	}
	
	private static double rowProduct2(double[][] weights, int row) {
		double product = 1.0;
		int n = weights[0].length;
		for (int j = 0; j < n; j++) {
			product *= weights[row][j];
		}
		return Math.pow(product, 1.0 / ((double) n));
	}
	
	private static double consistency(double[] c, double[] s) {
		double lambda = 0.0;
		int n = c.length;
		for (int i = 0; i < n; i++) {
			lambda += c[i] * s[i];
		}
		double ci = (lambda - n) / ((double) (n - 1));
		return ci / RI;
	}
}
