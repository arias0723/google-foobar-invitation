import java.util.Arrays;
import java.util.stream.IntStream;

/*
 * Overall solution:
 *  The problem has some similarities with the TSP (NP-Hard) problem. Using a dp approach generating all 
 *  possible subsets using a bitmask. 
 *  
 *  Bitmask + DP: O(2^n*n^2)
 * 
 *  1. Use Floyd-Warshall to get the optimal distances and detect if exists a negative cylce in the graph.
 *  2. dp[mask][v] : best way to traverse the 'mask' subset and ending at node v
 */
public class Problem2 {
    
    public static int[] solution(int[][] times, int times_limit) {
        // init
        int N = times.length;
        int dp[][] = new int[1<<N][N];
        for(int mask = 1; mask < (1<<N); mask++) {
            Arrays.fill(dp[mask], 10000);
        }
        int[][] dist = floydWarshall(times, N);

        // if a negative cycle exists, then we can iterate it an arbitrarily amount of times, so we can pick every bunny
        for(int u=0; u<N; u++) {
            if(dist[u][u] < 0) {
                return IntStream.rangeClosed(1, N-2)
                    .map(i -> i - 1)
                    .toArray();
            }
        }

        // base cases
        for(int i=0; i<N; i++) {
            dp[(1<<i)][i] = dist[0][i];
        }

        // bitmask dp
        for(int mask = 1; mask < (1<<N); mask++) {

            for(int end=0; end<N; end++) {
                if((mask & (1<<end)) > 0) {

                    for(int next=0; next<N; next++) {
                        if((mask & (1<<next)) == 0) {
                            dp[mask | (1<<next)][next] = Math.min(dp[mask | (1<<next)][next], dp[mask][end] + dist[end][next]);
                        }
                    }
                }
            }
        }

        // get solution
        int best_mask = 1<<N;
        int best_count = Integer.bitCount(best_mask);
        for(int mask = 1; mask < (1<<N); mask++) {
            boolean contains_start = (mask & 1) > 0;
            boolean contains_bulkhead = (mask & (1 << (N-1))) > 0;

            // solution has to contain both start and bulkhead
            if(contains_start && contains_bulkhead && dp[mask][N-1] <= times_limit) {
                int bunny_count = Integer.bitCount(mask);

                // select the solution with most bunnies
                if(bunny_count >= best_count) {
                    // from the solutions, select the bunnies with least IDs
                    if(bunny_count == best_count) {
                        best_mask = Math.min(mask, best_mask);
                    }
                    else {
                        best_mask = mask;
                    }
                    best_count = bunny_count;
                }
            }
        }

        // build result
        final int sol = best_mask;
        return IntStream.range(1, N-1)
            .filter(i -> (sol & (1<<i)) > 0)
            .map(i -> i - 1)
            .toArray();
    }

    private static int[][] floydWarshall(int[][] times, int N) {
        int dist[][] = new int[N][N];

        // init
        for(int u=0; u<N; u++) {
            for(int v=0; v<N; v++) {
                dist[u][v] = times[u][v];
            }
        }

        // dist
        for(int k=0; k<N; k++) {
            for(int u=0; u<N; u++) {
                for(int v=0; v<N; v++) {
                    dist[u][v] = Math.min(dist[u][v], dist[u][k] + dist[k][v]);
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        int[] s = Solution.solution(new int[][]{{0, 1, 1, 1, 1}, {1, 0, 1, 1, 1}, {1, 1, 0, 1, 1}, {1, 1, 1, 0, 1}, {1, 1, 1, 1, 0}}, 3);
        for(int i : s) {
            System.out.print(i + " ");
        }
    }
}