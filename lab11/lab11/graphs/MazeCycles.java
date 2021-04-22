package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private int s;
    private int[] parentOf;
    private boolean identifiedCycle = false;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        parentOf = new int[edgeTo.length];
    }

    private void dfs(int v) {
        marked[v] = true;
        announce();

        for (int w: maze.adj(v)) {
            if (marked[w] && parentOf[v] != w) {
                edgeTo[w] = v;
                while (v != w) {
                    edgeTo[v] = parentOf[v];
                    v = parentOf[v];
                }
                identifiedCycle = true;
                announce();
                return;
            }
            if (!marked[w]) {
                parentOf[w] = v;
                dfs(w);
                if (identifiedCycle) {
                    return;
                }
            }
        }
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(0);
    }

    // Helper methods go here
}

