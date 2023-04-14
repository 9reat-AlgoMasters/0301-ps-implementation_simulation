import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
    static int R;
    static int C;

    static int startX;
    static int startY;

    static int ax;
    static int ay;

    static char[][] map;
    static boolean[][] visited;

    static char pipe;


    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R + 1][C + 1];
        visited = new boolean[R + 1][C + 1];

        for (int i = 1; i < R + 1; i++) {
            String[] temp = br.readLine().split("");
            for (int j = 1; j < C + 1; j++) {
                map[i][j] = temp[j - 1].charAt(0);
                if (temp[j-1].charAt(0) == 'M') {
                    startX = i;
                    startY = j;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            int nx = startX + dx[i];
            int ny = startY + dy[i];
            if (check(nx, ny) && !(map[nx][ny] == '.' || map[nx][ny] == 'Z')) {;
                visited[nx][ny] = true;
                dfs(nx, ny);
                findStolenPipe(ax, ay);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(ax).append(" ").append(ay).append(" ").append(pipe);

        System.out.println(sb.toString());

    }

    static void dfs(int a, int b) {

        for (int i = 0; i < 4; i++) {
            int nx = a + dx[i];
            int ny = b + dy[i];
            if (check(nx, ny) && map[nx][ny] != '.') {
                // 들오는 물에 방향과 파이프따라
                if((i == 0 || i == 2) && map[nx][ny] == '|' && !visited[nx][ny]){
                    makeDfs(nx, ny);
                }
                if((i == 1 || i == 3) && map[nx][ny] == '-' && !visited[nx][ny]){
                    makeDfs(nx, ny);
                }
                if(map[nx][ny] == '+' && !visited[nx][ny]){
                    makeDfs(nx, ny);
                }
                if((i == 0 || i == 3) && map[nx][ny] == '1' && !visited[nx][ny]){
                    makeDfs(nx, ny);
                }
                if((i == 2 || i == 3) && map[nx][ny] == '2' && !visited[nx][ny]){
                    makeDfs(nx, ny);
                }
                if((i == 1 || i == 2) && map[nx][ny] == '3' && !visited[nx][ny]){
                    makeDfs(nx, ny);
                }
                if((i == 0 || i == 1) && map[nx][ny] == '4' && !visited[nx][ny]){
                    makeDfs(nx, ny);
                }
            }

        }
//        System.out.println(". 아님: " + a + " " + b);
//        checkpipe(a, b);

        if(ax == 0 && ay == 0) {
            checkpipe(a, b);

        }
        return;
    }

    static void makeDfs(int nx, int ny) {
        visited[nx][ny] = true;
//        System.out.println(nx + " " + ny + " " + map[nx][ny]);
        dfs(nx, ny);
        visited[nx][ny] = false;
    }

    static void checkpipe(int x, int y) {
        if(!check(x, y)) {
            return;
        }
//        System.out.println(x + " " + y);
        if (map[x][y] == '|') {

            if (map[x+1][y]=='.') {
//                System.out.println("| 밑에 .");
                ax = x + 1;
                ay = y;
//                System.out.println(" 결과: " + ax + " " + ay);
                return;
            }
            if (map[x - 1][y] == '.') {
                ax = x - 1;
                ay = y;
                return;
            }
        }
        if (map[x][y] == '-') {
            if (map[x][y-1] == '.') {
                ax = x;
                ay = y - 1;
                return;
            }
            if (map[x][y+1] == '.') {
                ax = x;
                ay = y + 1;
                return;
            }
        }
        if (map[x][y] == '+') {
            if (map[x][y - 1] == '.') { ax = x; ay = y-1;  return; }
            if (map[x][y + 1] == '.') { ax = x; ay = y + 1;  return; }
            if (map[x - 1][y] == '.') { ax = x -1; ay = y;  return; }
            if (map[x + 1][x] == '.') { ax = x + 1; ay = y;  return; }

        }
        if (map[x][y] == '1') {
            if (map[x + 1][y] == '.') { ax = x+1; ay = y;  return; }
            if (map[x][y + 1] == '.') { ax = x; ay = y+1;  return; }

        }
        if (map[x][y] == '2') {
            if (map[x - 1][y] == '.') { ax = x-1; ay = y;  return; }
            if (map[x][y + 1] == '.') { ax = x; ay = y+1; return; }

        }
        if (map[x][y] == '3') {

            if (map[x - 1][y] == '.') { ax = x-1; ay = y;  return;  }
            if (map[x][y - 1] == '.') { ax = x; ay = y-1;  return; }

        }
        if (map[x][y] == '4') {

            if (map[x][y - 1] == '.') { ax = x; ay = y-1;  return; }
            if (map[x + 1][y] == '.') { ax = x+1; ay = y;  return; }
        }
    }

    static void findStolenPipe(int x, int y) {
//         System.out.println("훔친 파이프 위치: " + x + " " + y);
        if (upSide(x, y) && downSide(x, y)) {
            if(!( check(x, y -1) && (map[x][y-1] == '-' || map[x][y - 1] == '1' || map[x][y - 1] == '2'|| map[x][y - 1] == '+')
                    || check(x, y + 1) && (map[x][y + 1] == '-' || map[x][y + 1] == '3' || map[x][y + 1] == '4'|| map[x][y + 1] == '+' ))){
                pipe = '|';
            }

        }
        if(leftSide(x, y) && rightSide(x, y)) {
            if(!(check(x - 1, y) && (map[x - 1][y] == '|' || map[x - 1][y] == '1' || map[x - 1][y] == '4' || map[x - 1][y] == '+')
                    || check(x + 1, y) && (map[x + 1][y] == '|' || map[x + 1][y] == '2' || map[x + 1][y] == '3' || map[x + 1][y] == '+'))){
                pipe = '-';
            }

        }
        if (upSide(x, y) && leftSide(x, y) && downSide(x, y) && rightSide(x, y)) {

            pipe = '+';
        }
        if (rightSide(x, y) && downSide(x, y)) {
            if(!(check(x - 1, y) && (map[x - 1][y] == '|'||  map[x - 1][y] == '1' ||  map[x - 1][y] == '4' || map[x - 1][y] == '+')
                    || check(x, y - 1) && (map[x][y - 1] == '-'|| map[x][y - 1] == '1' || map[x][y - 1] == '2'|| map[x][y - 1] == '+'))) {
                pipe = '1';
            }

        }
        if (rightSide(x, y) && upSide(x, y)) {
            // check(x  + 1, y) && check(x, y - 1) &&
            if(!(check(x  + 1, y) && (map[x + 1][y] == '|' ||  map[x + 1][y] == '2' ||  map[x + 1][y] == '3' || map[x + 1][y] == '+')
                    || check(x, y - 1) && (map[x][y - 1] == '-' || map[x][y - 1] == '1'||  map[x][y-1] == '2' || map[x][y - 1] == '+'))) {
                pipe = '2';
            }

        }
        if (leftSide(x, y) && upSide(x, y)) {
            if(!(check(x + 1, y) && (map[x + 1][y] == '|' ||  map[x + 1][y] == '2' ||  map[x + 1][y] == '3' || map[x + 1][y] == '+')
                    || check(x, y + 1) && (map[x][y + 1] == '-'|| map[x][y + 1] == '3' || map[x][y + 1] == '4'|| map[x][y + 1] == '+'))) {
                pipe = '3';
            }

        }
        if (leftSide(x, y) && downSide(x, y)) {
            if(!(check(x - 1, y) && (map[x - 1][y] == '|' ||  map[x - 1][y] == '1' ||  map[x - 1][y] == '4'|| map[x - 1][y] == '+')
                    ||check(x, y + 1) && (map[x][y + 1] == '-' || map[x][y + 1] == '3'|| map[x][y + 1] == '4' || map[x][y + 1] == '+'))) {
                pipe = '4';
            }

        }

    }

    static boolean upSide (int x, int y) {
        return x > 0 && (map[x - 1][y] == '|' || map[x - 1][y] == '+' || map[x - 1][y] == '1' || map[x - 1][y] == '4');
    }

    static boolean leftSide (int x, int y) {
        return y > 0 && (map[x][y-1] == '-' || map[x][y- 1] == '+' || map[x][y-1] == '1' || map[x][y-1] == '2');
    }
    static boolean downSide (int x, int y) {
        return x < R && (map[x + 1][y] == '|' || map[x + 1][y] == '+' || map[x + 1][y] == '2' || map[x  + 1][y] == '3');
    }
    static boolean rightSide (int x, int y) {
        return y < C && (map[x][y+1] == '-' || map[x][y+ 1] == '+' || map[x][y +1] == '3' || map[x][y + 1] == '4');
    }

    static boolean check(int x, int y) {
        if (x <= 0 || x > R|| y <= 0 || y > C){
            return false;
        }
        return true;
    }
}
