import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int map[][];
	static int dp[][];
	static int dir[][] = {{1,0},{-1,0},{0,1},{0,-1}};
	static int max;
	static boolean visited[][];
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		dp = new int[N][N];
		visited = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());				
			}
		}
		
		for (int i = 0; i < N; i++) {
			Arrays.fill(dp[i], -1);
		}
		

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(dp[i][j]==-1) {
					dp[i][j]= move(i,j);
				}
			}			
		}
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				max = Math.max(dp[i][j], max);
			}
		}
//		for (int i = 0; i < N; i++) {
//			System.out.println(Arrays.toString(dp[i]));
//		}
		
		System.out.println(max);
		
	}
	private static int move(int r, int c) {
		boolean isMax = true;
		for (int d = 0; d < 4; d++) {
			int nr = r + dir[d][0];
			int nc = c + dir[d][1];
			
			if(isInRange(nr, nc) && map[r][c] < map[nr][nc]) {
				isMax = false;
				if(dp[nr][nc] != -1) {
					dp[r][c] = Math.max(dp[r][c], dp[nr][nc]+1);
				}else {
					if(!visited[nr][nc]) {
						visited[nr][nc] = true;
						dp[r][c] = Math.max(dp[r][c],move(nr, nc)+1);
						visited[nr][nc] = false;
					}
				}
			}
		}
		if(isMax) {
			return 1;
		}
		return dp[r][c];
	}
	private static boolean isInRange(int nr, int nc) {
		return nr >=0 && nc >=0 && nr <N && nc < N;
	}
}