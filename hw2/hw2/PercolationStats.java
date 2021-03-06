package hw2;
import edu.princeton.cs.introcs.StdRandom;

import java.util.Arrays;

public class PercolationStats {
    private double[] fractions;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        fractions = new double[T];
        for (int i = 0; i < T; i += 1) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            fractions[i] = (double) p.numberOfOpenSites() / (N * N);
        }
    }

    public double mean() {
        return Arrays.stream(fractions).average().orElse(Double.NaN);
    }

    public double stddev() {
        double mean = mean();
        double[] squares = Arrays.stream(fractions).map(d -> (d - mean) * (d - mean)).toArray();
        double sqSum = Arrays.stream(squares).sum();
        return Math.sqrt(sqSum / (squares.length - 1));
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(fractions.length);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(fractions.length);
    }

//    public static void main(String[] args) {
//        PercolationStats ps = new PercolationStats(100, 10000, new PercolationFactory());
//        System.out.println("mean: " + ps.mean());
//        System.out.println("stddev: " + ps.stddev());
//        System.out.println("confidence: " + ps.confidenceLow() + " " + ps.confidenceHigh());
//    }
}
