package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int NN;
    private final WeightedQuickUnionUF grid;
    private final WeightedQuickUnionUF grid2;
    private final boolean[] openGrid;
    private int numOfOpenSites;
    private final int virtualTop;
    private final int virtualBottom;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N must > 0");
        }
        NN = N;
        grid = new WeightedQuickUnionUF(N * N + 2);
        grid2 = new WeightedQuickUnionUF(N * N + 2);
        openGrid = new boolean[N * N];
        numOfOpenSites = 0;
        virtualTop = N * N;
        virtualBottom = N * N + 1;
    }

    private int rowColToN(int row, int col) {
        return row * NN + col;
    }

    private void checkBound(int row, int col) {
        if (row > NN || row < 0 || col > NN || col < 0) {
            throw new java.lang.IndexOutOfBoundsException("Out of box");
        }
    }

    public void open(int row, int col) {
        checkBound(row, col);
        int site = rowColToN(row, col);
        int left = rowColToN(row, col - 1);
        int right = rowColToN(row, col + 1);
        int up = rowColToN(row - 1, col);
        int down = rowColToN(row + 1, col);
        if (col - 1 >= 0 && openGrid[left]) {
            grid.union(left, site);
            grid2.union(left, site);
        }
        if (col + 1 < NN && openGrid[right]) {
            grid.union(right, site);
            grid2.union(right, site);
        }
        if (row - 1 >= 0 && openGrid[up]) {
            grid.union(up, site);
            grid2.union(up, site);
        }
        if (row + 1 < NN && openGrid[down]) {
            grid.union(down, site);
            grid2.union(down, site);
        }
        if (!openGrid[site]) {
            numOfOpenSites += 1;
        }
        openGrid[site] = true;
        // connect to virtual top
        if (site <= NN - 1) {
            grid.union(site, virtualTop);
            grid2.union(site, virtualTop);
        }
        // connect to virtual bottom
        if (site >= NN * (NN - 1) && site <= NN * NN - 1) {
            grid.union(site, virtualBottom);
        }
    }

    public boolean isOpen(int row, int col) {
        checkBound(row, col);
        int site = rowColToN(row, col);
        return openGrid[site];
    }

    public boolean isFull(int row, int col) {
        checkBound(row, col);
        int site = rowColToN(row, col);
        return grid2.connected(site, virtualTop);
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    public boolean percolates() {
        return grid.connected(virtualTop, virtualBottom);
    }

    public static void main(String[] args) {

    }
}
