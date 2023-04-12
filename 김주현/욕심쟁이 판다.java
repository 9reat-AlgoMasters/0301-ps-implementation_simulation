import java.io.*;
import java.util.StringTokenizer;

public class Q1937 {
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static final int EMPTY = 0;
    static int N;
    static int[][] map;
    static int[][] dp;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        dp = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int max = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                max = Math.max(max, solveDP(i, j));
            }
        }

        sb.append(max);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    private static int solveDP(int x, int y) {
        if (dp[x][y] != EMPTY) {
            return dp[x][y];
        }

        dp[x][y] = 1;
        int max = 0;
        for (int[] d : DIR) {
            int nextX = x + d[0];
            int nextY = y + d[1];

            if (isInRange(nextX, nextY) && map[nextX][nextY] > map[x][y]) {
                max = Math.max(max, solveDP(nextX, nextY));
            }
        }
        dp[x][y] += max;
        return dp[x][y];
    }

    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N;
    }
}
