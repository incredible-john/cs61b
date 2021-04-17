package hw2;
import edu.princeton.cs.introcs.StdRandom;

import java.util.Arrays;

public class PercolationStats {
    private final double[] fractions;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        Percolation p = pf.make(N);
        fractions = new double[T];
        for (int i = 0; i < T; i += 1) {
            while (!p.percolates()) {
                int row = StdRandom.uniform(N - 1);
                int col = StdRandom.uniform(N - 1);
                p.open(row, col);
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
        return sqSum / (squares.length - 1);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(fractions.length);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(fractions.length);
    }
}
