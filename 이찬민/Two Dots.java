import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static int[][] map;
    static boolean[][] visited;

    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static boolean flag;

    static int startX;
    static int startY;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            String[] t = br.readLine().split("");
            for (int j = 0; j < M; j++) {
                map[i][j] = (int)t[j].charAt(0);
            }
        }

        //알파벳탐색
        int t = 0;
        f:for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                t = map[i][j];

                startX = i;
                startY = j;
                // System.out.println((char)map[i][j]);
                int cnt = 0;
                // 양옆에 무조건 2개는 있어야 들어 올 수 있다.
                for(int k = 0; k < 4; k++) {
                    int nx = i + dx[k];
                    int ny = j + dy[k];

                    if(check(nx, ny) && map[nx][ny] == t) {
                        cnt++;
                    }
                }
                if(cnt > 1) {
                    // System.out.println(i + " " + j);
                    // System.out.println((char)map[i][j]);
                    visited = new boolean[N][M];
                    search(i, j, t, 0);
                }

                if (flag) {
                    break f;
                }

            }
        }


        if (flag) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

    }

    static void search(int a, int b, int num, int cnt) {
        if(flag) {
            return;
        }
        if (a== startX && b == startY && cnt >= 4) {
            flag = true;
            return;
        }


        for (int i = 0; i < 4; i++) {
            int nx = a + dx[i];
            int ny = b + dy[i];

            if (nx== startX && ny == startY && cnt >= 4) {
                flag = true;
                return;
            }
            // 같은 알파벳 있는 경우
            //&& !visited[nx][ny]
            if(check(nx, ny) && !visited[nx][ny] && map[nx][ny] == num) {
                visited[nx][ny] = true;
                // System.out.println("방문: " + nx + " " + ny);
                search(nx, ny, num, cnt + 1);

                if (flag) {
                    return;
                }
            }
        }
    }

    static boolean check(int x, int y) {
        if (x<0 || x >= N || y < 0|| y >= M) {
            return false;
        }
        return true;
    }
}
