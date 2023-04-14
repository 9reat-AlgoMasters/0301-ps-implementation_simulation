import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main {
	static int N, M;
	static char[][] map;
	static boolean[][] visited;
	static int[] dr = {0,1,0,-1};
	static int[] dc = {1,0,-1,0};
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		map = new char[N][M];
		visited = new boolean[N][M];
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}
		
    
    //게임판 위의 점에 대해 하나씩 bfs
    //앞서 방문한 점은 이미 사이클 여부를 확인한 상태이므로 안해도 됨
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if(!visited[i][j]) {
          //bfs 메서드가 true리턴 시 사이클 생긴다는 의미
					if(bfs(i, j)) {
						System.out.println("Yes");
						return;
					}
				}
			}
		}
		System.out.println("No");
	}
	static boolean bfs(int r, int c) {
		Queue<int[]> q = new ArrayDeque<>();
    //큐에 저장되는 값 : 행, 열, 현재 이어진 같은 색의 점 수, 이전에 어느 방향에서 왔는지
		q.add(new int[] {r,c,1,-1});
		visited[r][c] = true;
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			int cr = cur[0];
			int cc = cur[1];
			int count = cur[2];
			int dir = cur[3];
			for (int i = 0; i < 4; i++) {
        //방금 들어온 방향으로는 탐색 x
				if(dir >= 0 && (dir+2)%4 == i) continue;
				int nr = cr + dr[i];
				int nc = cc + dc[i];
				if(checkRange(nr,nc)) {
          //색이 다른 점이면 탐색 x
					if(map[nr][nc] != map[cr][cc]) continue;
          //다음 탐색할 점이 방문한 적 있음 + 이어진 수가 3이상인 경우 -> 이거 이으면 길이 4이상, 사이클 
					if(visited[nr][nc] && count >= 3) {
						return true;
					}
					if(!visited[nr][nc]) {
						q.add(new int[] {nr,nc,count+1,i});
						visited[nr][nc] = true;
					}
				}
			}
		}
		return false;
	}
	static boolean checkRange(int r, int c) {
		return r>=0 && r<N && c>=0 && c<M;
	}
}
