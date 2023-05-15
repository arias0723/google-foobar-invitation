import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 * Overall solution:
 *  Solved apply group theory concepts: Burnside's Lemma / Polya Enumeration Theorem.
 * 
 *  Ref:
 *      https://medium.com/@chris.bell_/google-foobar-as-a-non-developer-level-5-a3acbf3d962b
 *      https://stackoverflow.com/questions/42655813/algorithm-to-find-unique-non-equivalent-configurations-given-the-height-the-wi
 *      https://codereview.stackexchange.com/questions/263421/applied-solution-based-on-polya-enumeration-theorem
 */
public class Problem3 {

    private static BigInteger factorial[];

    public static String solution(int w, int h, int s) {

        // factorial up to 20
        factorial = IntStream.rangeClosed(0, 20)
            .mapToObj(i -> i == 0 ? BigInteger.ONE : IntStream.rangeClosed(1, i)
                .mapToObj(BigInteger::valueOf)
                .reduce(BigInteger.ONE, BigInteger::multiply))
            .toArray(BigInteger[]::new);

        BigInteger grid = BigInteger.ZERO;

        for (List<Integer> pw : partitions(w, 1)) {
            for (List<Integer> ph : partitions(h, 1)) {

                BigInteger m = count(pw, w).multiply(count(ph, h));
                int sum = 0;
                
                for (int[] pair : pairs(pw, ph)) {
                    sum += gcd(pair[0], pair[1]);
                }

                grid = grid.add(m.multiply(BigInteger.valueOf(s).pow(sum)));
            }
        }

        BigInteger factorialW = factorial[w];
        BigInteger factorialH = factorial[h];
        BigInteger denominator = factorialW.multiply(factorialH);
        BigInteger res = grid.divide(denominator);

        return res.toString();
    }

    public static BigInteger count(List<Integer> c, int n) {
        BigInteger cnt = factorial[n];
        Map<Integer, Integer> counter = new HashMap<>();

        for (int i : c) {
            counter.put(i, counter.getOrDefault(i, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
            int a = entry.getKey();
            int b = entry.getValue();
            cnt = cnt.divide(BigInteger.valueOf(a).pow(b).multiply(factorial[b]));
        }
        
        return cnt;
    }

    public static List<List<Integer>> partitions(int n, int i) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(Collections.singletonList(n));

        for (int j = i; j <= n / 2; j++) {
            for (List<Integer> p : partitions(n - j, j)) {
                List<Integer> partition = new ArrayList<>();
                partition.add(j);
                partition.addAll(p);
                result.add(partition);
            }
        }

        return result;
    }

    public static List<int[]> pairs(List<Integer> pw, List<Integer> ph) {
        return pw.stream()
            .flatMap(i -> ph.stream().map(j -> new int[]{i, j}))
            .collect(Collectors.toList());
    }

    public static int gcd(int x, int y) {
        while (y != 0) {
            int temp = y;
            y = x % y;
            x = temp;
        }
        return x;
    }

    public static void main(String[] args) {
        System.out.println(solution(2, 3, 4));
    }
}