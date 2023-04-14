import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main{
	static int R, C;
	static char[][] map;
	static boolean[][] visited;
	static int[] z, m, ans;
	static char ansCh;
	static int total ,cnt;
	static boolean found;
	static int[] dr = {0,1,0,-1};
	static int[] dc = {1,0,-1,0};
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		R = Integer.parseInt(input[0]);
		C = Integer.parseInt(input[1]);
		map = new char[R][C];
		visited = new boolean[R][C];
		m = new int[2];
		z = new int[2];
		ans = new int[2];
		
		for (int i = 0; i < R; i++) {
			char[] temp = br.readLine().toCharArray();
			for (int j = 0; j < C; j++) {
				map[i][j] = temp[j];
				if(temp[j] != '.') {
          //M은 출발지
					if(temp[j] == 'M') {
						m[0] = i;
						m[1] = j;
            //Z 도착지
					}else if(temp[j] == 'Z') {
						z[0] = i;
						z[1] = j;
					}else {
            //total : M->Z 사이에 거쳐가는 블록 수
						total++;
					}
				}
			}
		}
    //하나 지워놨으므로 1더해줌
		total++;
		
    //출발지에서 4방 탐색
		for (int i = 0; i < 4; i++) {
			int nr = m[0] + dr[i];
			int nc = m[1] + dc[i];
			if(checkRange(nr, nc)) {
        //옆에 Z가 있다고 해서 바로 가면 안됨. 블록을 거쳐서 가야함
				if(map[nr][nc]!='Z') {
					dfs(nr, nc, i, false);
				}
			}
		}
		
		
	}
  
  //현재 칸의 행, 열, 이전칸에서 온 방향, 지워진 칸을 대체했는지 여부 를 인자로
	static void dfs(int r, int c, int dir, boolean filled) {
    //도착지 도달
		if(r == z[0] && c == z[1]) {
      //거쳐온 블록수가 total과 같나 == 모든 블록에 가스 흐르나 확인
			if(cnt == total) {
				System.out.println(ans[0]+" "+ans[1]+" "+ansCh);
				found = true;
			}
			return;
		}
    
    //cnt : 거쳐온 총 블록 수
		cnt++;
    
    //'+'모양 블록은 수평방향, 수직방향으로 총 2번 거쳐감. 
    //그래도 블록 수 셀 때는 한번만 세야하므로 현재칸이 이미 지나온 '+'라면 cnt다시 내려줌
		if(map[r][c] == '+' && visited[r][c]) {
			cnt--;
		}
		visited[r][c] = true;
    
    //현재 칸이 빈칸 아님
		if(map[r][c] != '.') {
      //다음에 갈 방향 구하기
			int nextDir = getNextDirection(map[r][c], dir);
      
      //다음 방향 == -1 : 잘못됨, 끝
			if(nextDir != -1) {
				int nr = r + dr[nextDir];
				int nc = c + dc[nextDir];
        //다음 칸 dfs 탐색
				dfs(nr, nc, nextDir, filled);
      }
      
      //현재칸이 빈칸이고, 이미 지워진 칸을 대체했는데 또 빈칸을 만났으면 잘못 대체했다는 뜻.그럼 끝.
      //(잘 대체 했다면 출발지에서 블록 따라가면 스무스하게 도착지까지 가야 정상)
		}else if(!filled) {
      //아직 지워진 칸을 대체 안했는데 현재칸이 빈칸 => 여기가 지워진 칸일수도 있으니 대체해보자.
      //7가지 블록 다 넣어보기
			for (int i = 0; i < 7; i++) {
				char ch = makeChar(i);
				int nextDir = getNextDirection(ch, dir);
				if(nextDir == -1) continue;
				map[r][c] = ch;
				ansCh = ch;
				ans[0] = r+1;
				ans[1] = c+1;
				int nr = r + dr[nextDir];
				int nc = c + dc[nextDir];
        
        //맘대로 넣어본 블록이므로 지도 벗어날 수 있음.
				if(checkRange(nr, nc)) {
          //블록 넣은 후 다음칸으로 dfs 탐색, 대체했음 표시
					dfs(nr, nc, nextDir, true);
          //하고 나왔는데 도착지까지 잘 갔고 모든 블록에 가스 흐름 => 반복문 더 돌 필요x
					if(found) {
						return;
					}
				}
			}
      //대체 잘못함 => 여기는 지워진 칸이 아님 => 다시 빈칸으로 돌리기
			map[r][c] = '.';
		}
    //dfs 돌렸는데 도착지까지 못갔으면 되돌아가야함 => 거쳐간 블록수 줄이기 
		cnt--;
    //'+' 블록인 경우 한번만 줄이면 됨
		if(map[r][c] == '+' && visited[r][c]) {
			cnt++;
		}
    
    //visited도 false로 돌려주면 틀린다..왜..??
	}
	
  //현재 칸, 이전에 온 방향을 바탕으로 다음 방향 계산하는 메서드
  // 0  1  2  3
  // 우 하 좌 상
	static int getNextDirection(char ch, int prev) {
		if(ch == '-') {
			if(prev == 0 || prev == 2) {
				return prev;
			}
		}else if(ch == '|') {
			if(prev == 1 || prev == 3) {
				return prev;
			}
		}else if(ch == '+') {
			return prev;
		}else if(ch == '1') {
			if(prev == 2) {
				return 1;
			}else if(prev == 3) {
				return 0;
			}
		}else if(ch == '2') {
			if(prev == 1) {
				return 0;
			}else if(prev == 2) {
				return 3;
			}
		}else if(ch == '3') {
			if(prev == 0) {
				return 3;
			}else if(prev == 1) {
				return 2;
			}
		}else if(ch == '4') {
			if(prev == 0) {
				return 1;
			}else if(prev == 3) {
				return 2;
			}
		}
		return -1;
	}
	
  //7가지 블록 반환 메서드
	static char makeChar(int i) {
		if(i == 0) {
			return '|';
		}else if(i == 1) {
			return '-';
		}else if(i == 2) {
			return '1';
		}else if(i == 3) {
			return '2';
		}else if(i == 4) {
			return '3';
		}else if(i == 5) {
			return '4';
		}else {
			return '+';
		}
	}
	static boolean checkRange(int r, int c) {
		return r>=0 && r<R && c>=0 && c<C;
	}
}
