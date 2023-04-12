import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static int n, ans;
	static int[][] map;
	static int[][] visited;
	static int[] dr = {0,1,0,-1};
	static int[] dc = {1,0,-1,0};
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
    //visited[i][j] : (i,j)칸에서 출발하여 갈 수 있는 최대 칸 수
		visited = new int[n][n];
		for (int i = 0; i < n; i++) {
			String[] input = br.readLine().split(" ");
			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(input[j]);
			}
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
        //dfs 과정에서 visited배열에 값이 기록된 경우는 dfs탐색을 또 해줄 필요 없음. 안 가본 칸에서만 dfs메서드 실행.
				if(visited[i][j] == 0) {
					ans = Math.max(ans, dfs(i,j));
				}
			}
		}
	
		System.out.println(ans);
	}
	static int dfs(int r, int c) {
    //처음 해당 위치에서 출발하여 갈수 있는 칸 수 == 1로 설정.
		visited[r][c] = 1;
		for (int i = 0; i < 4; i++) {
			int nr = r + dr[i];
			int nc = c + dc[i];
      //4방탐색. 현재 칸보다 더 대나무 많은 칸 찾기
			if(checkRange(nr, nc) && map[nr][nc] > map[r][c]) {
        //찾은 칸이 아직 안 가본 칸일 경우, 찾은 칸에서부터 dfs로 탐색한 결과 + 1(현재칸포함) 을 기존과 비교하여 큰 값으로 갱신
				if(visited[nr][nc] == 0) {
					visited[r][c] = Math.max(visited[r][c], dfs(nr, nc) + 1);
				}else {
          //찾은 칸이 이미 탐색해본 칸이면 탐색 또 할 필요 없음.
					visited[r][c] = Math.max(visited[r][c], visited[nr][nc] + 1);
				}
			}
		}
		return visited[r][c];
	}
	static boolean checkRange(int r, int c) {
		return r>=0 && r<n && c>=0 && c<n;
	}
}
