import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Q16929 {
    static final int[][] DIR = {{1,0}, {0,1}, {-1,0}, {0,-1}};
    static int N, M;
    static int[][] map;
    static boolean[][] visited;

    static class Point {
        int x, y;
        int beforeDir;

        public Point(int x, int y, int beforeDir) {
            this.x = x;
            this.y = y;
            this.beforeDir = beforeDir;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            char[] input = br.readLine().toCharArray();
            for (int j = 0; j < M; j++) {
                map[i][j] = input[j];
            }
        }

        boolean hasCycle = false;
        loop : for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!visited[i][j]) {
//                    System.out.printf("(%d, %d)에서 탐색 시작!\n", i ,j);
                    if (dfs(0, i, j, map[i][j], -1)) {
//                        System.out.println("find!!");
                        hasCycle = true;
                        break loop;
                    }
                }
            }
        }

        sb.append(hasCycle ? "Yes" : "No");

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static boolean dfs(int cnt, int x, int y, int color, int beforeDir) {
        for (int i = 0; i < 4; i++) {
            if ((i+2)%4 == beforeDir) continue;

            int nextX = x + DIR[i][0];
            int nextY = y + DIR[i][1];
            if (isInRange(nextX, nextY) && map[nextX][nextY] == color) {
                if (cnt + 1 >= 4 && visited[nextX][nextY]) {
                    return true;
                }
                if (!visited[nextX][nextY]) {
                    visited[nextX][nextY] = true;
                    boolean findLoop = dfs(cnt + 1, nextX, nextY, color, i);
                    if (findLoop) return true;
                }
            }
        }
        return false;
    }

    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < M;
    }
}
