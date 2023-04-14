import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static int N;
	static char[][] map;
	static Log center;
	static int range[][] = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
	static int dir[][] = {{1,0},{-1,0},{0,1},{0,-1}};
	static int visited[][][];
	static class Log {
		int r;
		int c;
		int d; // 1이면 세로 2면 가로

		public Log(int r, int c, int d) {
			super();
			this.r = r;
			this.c = c;
			this.d = d;
		}

		@Override
		public String toString() {
			return "Log [r=" + r + ", c=" + c + ", d=" + d + "]";
		}
		

	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		map = new char[N][N];
		visited = new int[N][N][2];
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}

		boolean find = false;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 'B') {
					if (isInRange(i, j + 1) && isInRange(i, j + 2) && map[i][j + 1] == 'B' && map[i][j + 2] == 'B') {
						center = new Log(i, j + 1, 2);
						find = true;
						break;
					}

					if (isInRange(i + 1, j) && isInRange(i + 2, j) && map[i + 1][j] == 'B' && map[i + 2][j] == 'B') {
						center = new Log(i + 1, j, 1);
						find = true;
						break;
					}
				}
			}
			if (find) {
				break;
			}
		}
		
		
		Queue<Log> q = new LinkedList<>();
		visited[center.r][center.c][center.d-1] = 1;
		q.add(center);
		int k =1;
		Log result = null;
		while (!q.isEmpty()) {
//			System.out.println("{{{{{{{{{{  "+ (k++) +"  }}}}}}}}}}}");
//			for (int i = 0; i < N; i++) {
//				for (int j = 0; j < N; j++) {
//					System.out.print(visited[i][j][0]+" ");
//				}
//				System.out.println();
//			}
//			
//			System.out.println("====================");
//
//			for (int i = 0; i < N; i++) {
//				for (int j = 0; j < N; j++) {
//					System.out.print(visited[i][j][1]+" ");
//				}
//				System.out.println();
//			}
//
//			System.out.println("====================");
//			
			
			Log log = q.poll();
			
			if(map[log.r][log.c] == 'E') {
				if(log.d == 1) {
					if(isInRange(log.r-1, log.c) && isInRange(log.r+1, log.c)
							&& map[log.r-1][log.c]=='E'&& map[log.r+1][log.c]=='E') {
						result = log;
						break;
					}
				}else {
					if(isInRange(log.r, log.c-1) && isInRange(log.r, log.c+1)
							&& map[log.r][log.c-1]=='E'&& map[log.r][log.c+1]=='E') {
						result = log;
						break;
					}
				}
			}
			
			//사방으로 이동
			for (int d = 0; d < 4; d++) {
				int nr = log.r + dir[d][0];
				int nc = log.c + dir[d][1];
				if(isInRange(nr, nc) && map[nr][nc] != '1' ) {
					if(log.d == 1&& visited[nr][nc][log.d-1]==0) {
						if(isInRange(nr-1, nc)&&isInRange(nr+1, nc)&&map[nr-1][nc] != '1' && map[nr+1][nc] !='1') {
							visited[nr][nc][0] =visited[log.r][log.c][0]+1;
							q.add(new Log(nr, nc, log.d));
						}
					}else if(log.d == 2&& visited[nr][nc][log.d-1]==0){
						if(isInRange(nr, nc-1)&&isInRange(nr, nc+1)&&map[nr][nc-1] !='1' && map[nr][nc+1] !='1') {
							visited[nr][nc][1] =visited[log.r][log.c][1]+1;;
							q.add(new Log(nr, nc, log.d));
						}
					}
				}
			}
			
			//회전
			boolean turn = true;
			for (int r = 0; r < 8; r++) {
				int nr = log.r+range[r][0];
				int nc = log.c+range[r][1];
				if(!isInRange(nr, nc)) {
					turn = false;
					break;
				}else {
					if(map[nr][nc] =='1') {
						turn = false;
						break;
					}
				}
			}
			
			if(turn) {
				if(log.d== 1) {
					if(visited[log.r][log.c][2-1]==0) {
						visited[log.r][log.c][2-1] =visited[log.r][log.c][1-1]+1;
						q.add(new Log(log.r,log.c,2));	
					}
				}else {
					if(visited[log.r][log.c][1-1]==0) {
						visited[log.r][log.c][1-1] =visited[log.r][log.c][2-1]+1;
						q.add(new Log(log.r,log.c,1));
					}
				}
			}
		}
		if(result == null ) {
			System.out.println(0);
		}else {
//		System.out.println("Result : " + result.toString());
			System.out.println(visited[result.r][result.c][result.d-1]-1);
		}
		

	}

	static boolean isInRange(int r, int c) {
		return r >= 0 && c >= 0 && r < N && c < N;
	}

}