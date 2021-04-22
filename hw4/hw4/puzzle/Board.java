package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {

    private int[][] board;
    private int N;
    private int BLANK;

    private int ijTo1D(int i, int j, int N) {
        return i * N + j;
    }

    private int[] valToij(int val, int N) {
        return new int[]{val / N, val % N};
    }

    public Board(int[][] tiles) {
        N = tiles.length;
        BLANK = 0;
//        board = tiles;
        board = new int[N][N];
        for(int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                board[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        return board[i][j];
    }

    public int size() {
        return N;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        /* Written by Josh Hug */
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int count = 0;
        for(int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                if (board[i][j] != ijTo1D(i, j, N) + 1 && board[i][j] != 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public int manhattan() {
        int count = 0;
        for(int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                int val = board[i][j];
                if (val-1 != -1) {
                    int x = valToij(val-1, N)[0];
                    int y = valToij(val-1, N)[1];
                    count = count + Math.abs(x - i) + Math.abs(y - j);
                }
            }
        }
        return count;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        int NN = Math.max(((Board) y).size(), N);
        for(int i = 0; i < NN; i += 1) {
            for (int j = 0; j < NN; j += 1) {
                if (board[i][j] != ((Board) y).tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
