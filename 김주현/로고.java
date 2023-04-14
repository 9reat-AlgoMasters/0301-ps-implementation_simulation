import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Q3108 {
    static int N;
    static int[] parent;
    static Square[] squares;
    static class Square {
        int x1, x2, y1, y2;

        public Square(int x1, int y1, int x2, int y2) {
            this.x1 = Math.min(x1, x2);
            this.x2 = Math.max(x1, x2);
            this.y1 = Math.min(y1, y2);
            this.y2 = Math.max(y1, y2);
        }

        public boolean hasPoint(int x, int y) {
            if (x == x1 || x == x2) {
                return y >= y1 && y <= y2;
            } else if (y == y1 || y == y2) {
                return x >= x1 && x <= x2;
            }
            return false;
        }

        public boolean hasIntersection(Square o) { // x의 범위와 y의 범위가 겹쳐야 함
            return hasCommonRange(x1, x2, o.x1, o.x2) && hasCommonRange(y1, y2, o.y1, o.y2)
                    && !contains(o) && !o.contains(this);
        }

        private boolean hasCommonRange(int from1, int end1, int from2, int end2) {
            return (from2 >= from1 && from2 <= end1) || (end2 >= from1 && end2 <= end1)
                    || (from1 >= from2 && from1 <= end2) || (end1 >= from2 && end1 <= end2);
        }

        private boolean contains(Square o) {
            return o.x1 > x1 && o.x2 < x2 && o.y1 > y1 && o.y2 < y2;
        }

        @Override
        public String toString() {
            return "Square{" +
                    "x1=" + x1 +
                    ", x2=" + x2 +
                    ", y1=" + y1 +
                    ", y2=" + y2 +
                    '}';
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        boolean containStartPoint = false;

        N = Integer.parseInt(br.readLine());
        squares = new Square[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            squares[i] = new Square(Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()));
            if (squares[i].hasPoint(0, 0)) {

                containStartPoint = true;
            }
        }

        initParent();
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (squares[i].hasIntersection(squares[j])) {
                    union(i, j);
                }
            }
        }

        Set<Integer> parentSet = new HashSet<>();
        for (int i = 0; i < N; i++) {
            parentSet.add(find(i));
        }

        sb.append(containStartPoint ? parentSet.size()-1 : parentSet.size());

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static void initParent() {
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
        }
    }

    private static int find(int a) {
        if (a == parent[a]) {
            return a;
        }
        return parent[a] = find(parent[a]);
    }

    private static boolean union(int a, int b) {
        a = find(a);
        b = find(b);
        if(a==b) return false;
        parent[b] = a;
        return true;
    }
}
