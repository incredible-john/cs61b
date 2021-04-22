package hw4.puzzle;

import java.util.ArrayDeque;
import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    private ArrayDeque<WorldState> solution;
    private final int minMoves;

    private static class SearchNode {
        private WorldState worldState;
        private int moves;
        private SearchNode prevSearchNode;

        SearchNode(WorldState ws, int m, SearchNode prev) {
            worldState = ws;
            moves = m;
            prevSearchNode = prev;
        }
    }

    public Solver(WorldState initial) {
        solution = new ArrayDeque<>();
        MinPQ<SearchNode> pq = new MinPQ<>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                int o1Priority = o1.moves + o1.worldState.estimatedDistanceToGoal();
                int o2Priority = o2.moves + o2.worldState.estimatedDistanceToGoal();
                return Integer.compare(o1Priority, o2Priority);
            }
        });

        SearchNode initialSearchNode = new SearchNode(initial, 0, null);
        pq.insert(initialSearchNode);
        SearchNode nextBest = pq.delMin();

        while (!nextBest.worldState.isGoal()) {
            for (WorldState n: nextBest.worldState.neighbors()) {
                SearchNode neighborNode = new SearchNode(n, nextBest.moves + 1, nextBest);
                if (nextBest.prevSearchNode == null
                        || !neighborNode.worldState.equals(nextBest.prevSearchNode.worldState)) {
                    pq.insert(neighborNode);
                }
            }
            nextBest = pq.delMin();
        }
        // results
        minMoves = nextBest.moves;
        SearchNode nb = nextBest;
        for (int i = 0; i < minMoves; i += 1) {
            solution.addFirst(nb.worldState);
            nb = nb.prevSearchNode;
        }
        solution.addFirst(initial);
    }

    public int moves() {
        return minMoves;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }
}
