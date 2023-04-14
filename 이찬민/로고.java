import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.*;

public class Main {
    static int N;
    static int x1;
    static int y1;
    static int x2;
    static int y2;
    static int[][] points;

    static int max;

    static Integer[] parent;
    static int[] rank;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        max = Integer.MIN_VALUE;

        points = new int[N][4];

        parent = new Integer[N];
        rank = new int[N];

        int answer = 0;

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            x1 = Integer.parseInt(st.nextToken());

            y1 = Integer.parseInt(st.nextToken());

            x2 = Integer.parseInt(st.nextToken());

            y2 = Integer.parseInt(st.nextToken());




            points[i] = new int[]{x1, y1, x2, y2};
        }

        f: for (int i = 0; i < N; i++) {
            for (int j = points[i][0]; j < points[i][2] + 1; j++) {

                if (j== 0 && points[i][1] ==0 || j== 0 && points[i][3] ==0) {
                    answer = -1;
                    break f;
                }
            }
            for (int j = points[i][1]; j < points[i][3] + 1; j++) {
                if (points[i][0] == 0 && j == 0 || points[i][2]== 0 && j ==0) {
                    answer = -1;
                    break f;
                }
            }
        }


        init();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != j && checkRect(points[i], points[j])) {
                    union(i , j);
                }

            }
        }

        for(int i = 0;i < N; i++) {
            find(i);
        }

        Set<Integer> a = new HashSet<>(Arrays.asList(parent));
//        System.out.println(a.size());
        answer += a.size();

        System.out.println(answer);


    }

    static boolean checkRect(int[] a, int[] b) {
        int x1 = a[0];
        int y1 = a[1];
        int x2 = a[2];
        int y2 = a[3];

        int bx1 = b[0];
        int by1 = b[1];
        int bx2 = b[2];
        int by2 = b[3];

        if (x2 < bx1 || bx2 < x1 ||y2 < by1 || by2 < y1 ) {  // 안만나짐
            return false;
        }

        // 내부에
        if(x1 > bx1 && y1 > by1 && x2 < bx2 && y2 < by2) {
            return false;
        }

        if(x1 < bx1 && y1 < by1 && x2 > bx2 && y2 > by2) {
            return false;
        }

        return true;

    }

    static void init() {
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }
    static boolean union(int a, int b) {
        int x = find(a);
        int y = find(b);

        if (x == y) {
            return false;
        }

        if (rank[x] < rank[y]) {
            rank[y] += rank[x];
            parent[x] = y;
        } else if (rank[x] > rank[y]) {
            rank[x] += rank[y];
            parent[y] = x;
        }else {
            rank[x] += rank[y];
            parent[y] = x;
        }

        return true;
    }

    static int find(int a) {
        if(a == parent[a]) {
            return a;
        }
        return parent[a] = find(parent[a]);
    }
}
