import java.util.HashSet;
import java.util.Set;

/*
 * Overall solution:
 * 
 *  1. Reflect the grid over the first (positive) quadrant. Other 3 quadrants are calculated multipliying by -1 the corresponding axis 
 *  2. Count every DISTINCT line between YOU and (reflected) TRAINERS, where <= Distance and no "obstacles" (yourself or a trainer point) in the middle.
 */
public class Problem1 {
    public static int solution(int[] dimensions, int[] your_position, int[] trainer_position, int distance) {

        int X = dimensions[0], Y = dimensions[1];
        int[] A = {your_position[0], your_position[1]};
        int[] B = {trainer_position[0], trainer_position[1]};

        int[] quadrant_x = {1, 1, -1, -1};
        int[] quadrant_y = {1, -1, -1, 1};
        Set<String> lines = new HashSet<>();
        int ans = 0;

        for(int over_x = 0; over_x <= distance; over_x++) {
            reflect_over_x(A, X * over_x);
            reflect_over_x(B, X * over_x);
            A[1] = your_position[1];
            B[1] = trainer_position[1];

            // prune search when distance is greater
            if(square_distance(your_position, B) > (long) distance * distance) {
                break;
            }

            for(int over_y = 0; over_y <= distance; over_y++) {
                reflect_over_y(A, Y * over_y);
                reflect_over_y(B, Y * over_y); 

                // prune search when distance is greater
                if(square_distance(your_position, B) > (long) distance * distance) {
                    break;
                }

                // reflection in the 4 quadrants
                for(int i = 0; i < 4; i++) {
                    int aa[] = {A[0] * quadrant_x[i], A[1] * quadrant_y[i]};
                    int bb[] = {B[0] * quadrant_x[i], B[1] * quadrant_y[i]};

                    // put line from your_position to aa in the set
                    put_line(lines, your_position, aa);

                    // check for previous points liying in the same line
                    if(!check_line(lines, your_position, bb)) {
                        if(square_distance(your_position, bb) <= (long) distance * distance) {
                            ans ++;
                        }
                    }
                    
                    // put line from your_position to bb in the set
                    put_line(lines, your_position, bb);
                }
            } 
        }

        return ans;
    }

    private static boolean check_line(Set<String> lines, int[] a, int[] b) {
        int deltax = a[0] - b[0];
        int deltay = a[1] - b[1];
        int g = Math.abs(gcd(deltax, deltay));
        if(g == 0) g = 1;

        deltax /= g;
        deltay /= g;

        String hash = String.valueOf(deltax) + "-" + String.valueOf(deltay);
        return lines.contains(hash);
    }

    private static void put_line(Set<String> lines, int[] a, int[] b) {
        int deltax = a[0] - b[0];
        int deltay = a[1] - b[1];
        int g = Math.abs(gcd(deltax, deltay));
        if(g == 0) g = 1;

        deltax /= g;
        deltay /= g; 
        
        String hash = String.valueOf(deltax) + "-" + String.valueOf(deltay);
        lines.add(hash);
    }

    public static int gcd(int a, int b) { return b==0 ? a : gcd(b, a%b); }

    private static long square_distance(int[] a, int[] b) {
        long deltax = a[0] - b[0];
        long deltay = a[1] - b[1];
        return (deltax) * (deltax) + (deltay) * (deltay);
    }
    
    private static void reflect_over_x(int[] point, int x) {
        if (x <= 0) return;
        int delta = x - point[0];
        point[0] = x + delta;
    }

    private static void reflect_over_y(int[] point, int y) {
        if (y <= 0) return;
        int delta = y - point[1];
        point[1] = y + delta;
    }

    public static void main(String[] args) {
        // int[] dimensions = {3, 3};
        // int[] you = {1, 1};
        // int[] trainer = {2, 2};
        System.out.println(Problem1.solution(new int[]{10,10}, new int[]{4,4}, new int[]{3,3}, 10));
    }
}