import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main_1938 {
    static int N, ans;
    static char[][] map;
    static boolean[][][] visited;
    static int[] B, E;
    static int[] dr = {0,1,0,-1};
    static int[] dc = {1,0,-1,0};
    static int[] dr8 = {-1,0,1,1,1,0,-1,-1};
    static int[] dc8 = {1,1,1,0,-1,-1,-1,0};
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new char[N][N];
        //방문 표시할 배열. 같은 좌표 r,c 더라도 모양이 가로일 때와 세로 일때 다르므로 3차원 배열로 선언
        visited = new boolean[N][N][2];
        //통나무의 중심 좌표와 놓여진 모양을 저장할 배열
        B = new int[3];
        E = new int[3];
        //BBB - 0
		/* B
		 * B - 1
		   B
		*/
        boolean findB = false, findE = false;
        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split("");
            for (int j = 0; j < N; j++) {
                map[i][j] = input[j].charAt(0);
                if(map[i][j] == 'B') {
                    if(findB) {
                        findB = false;
                        B[0] = i;
                        B[1] = j;
                        //가로로 있는 경우
                        if(j > 0 && map[i][j-1] == 'B') {
                            B[2] = 0;
                        }else {
                            B[2] = 1;
                        }
                    }else {
                        findB = true;
                    }
                }
                if(map[i][j] == 'E') {
                    if(findE) {
                        findE = false;
                        E[0] = i;
                        E[1] = j;
                        if(j > 0 && map[i][j-1] == 'E') {
                            E[2] = 0;
                        }else {
                            E[2] = 1;
                        }
                    }else {
                        findE = true;
                    }
                }
            }
        }
        //출발 시 위치와 모양(가로 or 세로)을 인자로 bfs 메서드 실행
        bfs(B[0], B[1], B[2]);
        System.out.println(ans);
    }
    static void bfs(int r, int c, int shape){
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{r,c,shape,0});
        visited[r][c][shape] = true;
        while(!q.isEmpty()){
            int[] cur = q.poll();
            int cr = cur[0];
            int cc = cur[1];
            int cShape = cur[2];
            int cCount = cur[3];
            for (int i = 0; i < 5; i++) {
                int nr, nc, nShape;
                if(i < 4) {
                    //4방탐색으로 다음 갈 곳 찾기
                    nr = cr + dr[i];
                    nc = cc + dc[i];
                    nShape = cShape;
                    //지도를 넘어가지않고 아직 방문 안 한 곳, 그 중에서도 '1'없는 곳으로 가야함
                    if(checkRange(nr, nc, nShape) && !visited[nr][nc][nShape]) {
                        if (nShape == 0) {
                            if (map[nr][nc - 1] == '1' || map[nr][nc] == '1' || map[nr][nc + 1] == '1') {
                                continue;
                            }
                        } else {
                            if (map[nr - 1][nc] == '1' || map[nr][nc] == '1' || map[nr + 1][nc] == '1') {
                                continue;
                            }
                        }
                    }else continue;
                }else {
                    //회전하는 경우 처리
                    nr = cr;
                    nc = cc;
                    boolean canNotTurn = false;
                    for (int j = 0; j < 8; j++) {
                        //8방 탐색하여 지도 벗어나거나 '1'이 있으면 회전 못함
                        int ar = nr + dr8[j];
                        int ac = nc + dc8[j];
                        if(ar < 0 || ar >= N || ac < 0 || ac >= N || map[ar][ac] == '1' ){
                            canNotTurn = true;
                            break;
                        }
                    }
                    if(canNotTurn){
                        break;
                    }
                    //회전 가능하면 모양 변경
                    nShape = 1 - cShape;
                    //회전 가능은 한데 이미 방문했으면 큐에 넣지않음
                    if(visited[nr][nc][nShape]){
                        break;
                    }
                }
                //다음 위치로 이동 가능하면 큐에 저장
                int nCount = cCount + 1;
                q.add(new int[]{nr,nc,nShape,nCount});
                //도착지의 중심좌표와 일치, 모양도 일치하면 끝
                if(nr == E[0] && nc == E[1] && nShape == E[2]) {
                    ans = nCount;
                    return;
                }
                visited[nr][nc][nShape] = true;
            }

        }
    }

    //통나무가 지도 안에 있는지 확인하는 메서드
    static boolean checkRange(int r, int c, int shape) {
        return r>=0+shape && r<N-shape && c>=0+(1-shape) && c<N-(1-shape);
    }
}
