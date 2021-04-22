package lab11.graphs;

import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private PriorityQueue<Integer> toSearch;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        toSearch = new PriorityQueue<>((o1, o2) -> {
            int vDist = distTo[o1] + h(o1);
            int wDist = distTo[o2] + h(o2);
            return Integer.compare(vDist, wDist);
        });
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int p1 = Math.abs(maze.toX(v) - maze.toX(t));
        int p2 = Math.abs(maze.toY(v) - maze.toY(t));
        return  p1 + p2;
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        toSearch.add(s);
        marked[s] = true;
        while (!toSearch.isEmpty()) {
            int v = toSearch.poll();
            announce();


            for (int w: maze.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    toSearch.add(w);
                    announce();
                    if (w == t) {
                        targetFound = true;
                    }

                    if (targetFound) {
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

