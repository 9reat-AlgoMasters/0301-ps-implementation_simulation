import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;
//https://m.blog.naver.com/zbqmgldjfh/222468337186
public class Main {
	static int N;
	static int p[];
	static Square squares[];
	static class Square{
		int x1;
		int y1;
		int x2;
		int y2;
		public Square(int x1, int y1, int x2, int y2) {
			super();
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		@Override
		public String toString() {
			return "Square [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + "]";
		}
		
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		squares = new Square[N+1];
		squares[0] = new Square(0, 0, 0, 0);
		for (int i = 1; i < N+1; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
//			if(N == 1 && x1==0 && y1 == 0) {
//				System.out.println(0);
//				System.exit(0);
//			}
			
			squares[i]= new Square(x1, y1, x2, y2);
		}
		
		p = new int[1001];
		for (int i = 0; i < N+1; i++) {
			p[i] = i;
		}
		for (int i = 0; i < N+1; i++) {
			for (int j = 0; j < N+1; j++) {
//				System.out.println(i+" "+j);
				if(isOverlap(squares[i],squares[j])) {
					//겹치면 하나로 만들어줌
//					System.out.println("겹침");
					union(j, i);
//						System.out.println("union");
				}
//				System.out.println(Arrays.toString(p));
			}
		}

		HashSet<Square> set = new HashSet<>();
		for (int i = 0; i <= N; i++) {
			set.add(squares[find(p[i])]);
		}
		System.out.println(set.size()-1);

	}


	static boolean union(int x, int y) {
		x = find(x);
		y = find(y);
//		if( x == y ) return false;
		if(x < y )
			p[y] = x;
		else p[x]  =y;
		return true;
		
	}


	static int find(int x) {
		if(x == p[x]) return x;
		else return p[x]= find(p[x]);
	}


	static boolean isOverlap(Square s1,Square s2) {
		if(s1.x1<s2.x1 && s1.y1<s2.y1 && s1.x2>s2.x2 && s1.y2>s2.y2) {
//			System.out.println("바깥");
			return false;
		}
		

		if(s1.x1>s2.x1 && s1.y1>s2.y1 && s1.x2<s2.x2 && s1.y2<s2.y2) {
//			System.out.println("안쪽");
			return false;
		}
		
		if(s1.x2 < s2.x1 || s1.x1 > s2.x2 || s1.y2 < s2.y1 || s1.y1 > s2.y2) {
//			System.out.println("사방중하나");
			return false;
		}
		
		return true;
	}

}