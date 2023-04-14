import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] map;
    static int[][] dp;

    static int[] dx = {1, 0, -1, 0};
    static int[] dy= {0,1,0,-1};


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        dp = new int[N][N];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j =0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                dp[i][j] = 0;
            }
        }

        int answer = 0;
        for(int i = 0; i < N; i++) {
            for(int j =0; j < N; j++) {
                answer = Math.max(answer, dfs(i, j));
            }
        }

        System.out.println(answer);
    }



    static int dfs(int x,int y){
        // 방문된 적이 있는 경우
        if (dp[x][y] > 0) {
            return dp[x][y];
        }
        dp[x][y] = 1;
        for(int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (check(nx, ny) && map[nx][ny] > map[x][y]) {
                // 여러개의 경로에 의해 겹친 지점에서 최대값(최대한 많이 방문한 값)
                dp[x][y] = Math.max(dp[x][y], dfs(nx, ny) + 1);
            }
        }
        return dp[x][y];
    }


    static boolean check(int x, int y){
        if (x < 0 || x >= N || y < 0 || y >= N) {
            return false;
        }
        return true;

    }
}
