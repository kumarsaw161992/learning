package it.sella.fabrickpfm.movements.batch.config;

import java.util.ArrayList;

public class Test {

    public String[] solution(int N, int K) {
        if (N == 0) {
            return new String[]{""};
        }
        ArrayList<String> result = new ArrayList<String>();
        for (String p : solution(N - 1, K - 1)) {
            for (char c : new char[]{'a', 'b', 'c'}) {
                int plen = p.length();
                if (plen == 0 || p.charAt(plen-1) != c) {
                    result.add(p + c);
                }
            }
        }
        int prefSize = Math.min(result.size(), K);
        return result.subList(0, prefSize).toArray(new String[prefSize]);

    }

    public static void main(String args[]) {
        Test t = new Test();
        for (String s:t.solution(10, 20))
            System.out.print(s+" ");

    }
}
