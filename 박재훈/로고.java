import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;

public class Main {
	static int N, ans, val = 2;
	static int[][] map;
	static boolean[][] visited = new boolean[1001][1001];
	static int[] dr = {0,1,0,-1};
	static int[] dc = {1,0,-1,0};
	static HashSet<Integer> set = new HashSet<>();
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[1001][1001];
    //0,0을 배열의 500,500으로 잡기
		map[500][500] = 1;
		for (int k = 0; k < N; k++) {
			String[] input = br.readLine().split(" ");
			int x1 = Integer.parseInt(input[0]) + 500;
			int y1 = Integer.parseInt(input[1]) + 500;
			int x2 = Integer.parseInt(input[2]) + 500;
			int y2 = Integer.parseInt(input[3]) + 500;
			
      //set : 겹치는 부분의 개수를 저장하기 위해
			set.clear();
			for (int i = 0; i < 1001; i++) {
				Arrays.fill(visited[i], false);
			}

      //겹치는지 확인할 변수
			boolean isOverlaped = false;
      //1001x1001 크기 배열에 사각형 직접 그리기
			for (int i = y1; i <= y2; i++) {
				for (int j = x1; j <= x2; j++) {
					if(i != y1 && i != y2 && j != x1 && j != x2)continue;
					
          //0이 아닌 칸이 있다 == 이미 그려진 사각형과 겹침
					if(map[i][j] != 0) {
						isOverlaped = true;
						
            //0,0을 지나면 PU연산 한번 안해도 됨
						if(map[i][j] == 1) {
							ans--;
						}else {
              //이미 그려진 사각형의 번호 저장
							set.add(map[i][j]);
						}
					}
          //배열에 val 값으로 사각형의 둘레를 채움 (사각형은 각각 2번부터 번호가 매겨짐)
					map[i][j] = val;
				}
			}
			if(isOverlaped) {
        //겹치면 겹친 사각형과 겹쳐있는 모든 사각형 탐색 필요
				bfs(y1, x1);
			}
			val++;
			ans++;
		}
		
		System.out.println(ans);
	}
	static void bfs(int r, int c) {
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {r,c});
		visited[r][c] = true;
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			int cr = cur[0];
			int cc = cur[1];
			
			for (int i = 0; i < 4; i++) {
				int nr = cr + dr[i];
				int nc = cc + dc[i];
				if(checkRange(nr, nc)) {
          //사각형 둘레 따라 이동
					if(!visited[nr][nc] && map[nr][nc] != 0) { 
						q.add(new int[] {nr,nc});
						visited[nr][nc] = true;
            
            //이미 그려진 사각형을 만났으면 새로 겹쳐진 사각형 번호로 대체						
						if(set.contains(map[nr][nc])) {
							map[nr][nc] = val;
						}
					}
				}
			}
		}
    //겹쳐진 사각형 개수만큼 ans에서 빼줌
		ans -= set.size();
		
	}
	static boolean checkRange(int r, int c) {
		return r>=0 && r<=1000 && c>=0 && c<=1000;
	}
}
