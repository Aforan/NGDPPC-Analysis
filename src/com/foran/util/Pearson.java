package com.foran.util;

public class Pearson {
	public static double pearson(double[] x, double[] y) throws RuntimeException{
		int n = x.length;

		double sxy = n * sum(x, y) - (sum(x) * sum(y));
		double sxx = n * sum(x, x) - Math.pow(sum(x), 2);
		double syy = n * sum(y, y) - Math.pow(sum(y), 2);

		double r = sxy / (Math.sqrt(sxx) * Math.sqrt(syy));

		return r;		
	}

	public static double sum(double[] d) {
		double r = 0.0;

		for(int i = 0; i < d.length; i++) {
			r += d[i];
		}

		return r;
	}

	public static double sum(double[] dx, double[] dy) throws RuntimeException{
		double r = 0.0;
		
		if(dx.length != dy.length) throw new RuntimeException("Summation over non-equal length arrays");

		for(int i = 0; i < dx.length; i++) {
			r += dx[i] * dy[i];
		}

		return r;
	}
}