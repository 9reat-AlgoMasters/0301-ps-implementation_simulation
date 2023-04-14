import java.io.*;
import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;

public class Q1938 {
    static final int HOR = 0;
    static final int VER = 1;
    static final int POS_MOVE = 1;
    static final int NEG_MOVE = -1;
    static final int TREE = '1';
    static final int LOG = 'B';
    static final int GOAL = 'E';
    static final int IMPOSSIBLE = 0;

    static int N;
    static int[][] map;

    static class Log {
        final boolean HOR = true;
        final int SIZE = 3;
        int[] pos;
        boolean direction;

        public Log() {
            pos = new int[SIZE];
        }

        public Log(Log log) {
            pos = new int[SIZE];
            System.arraycopy(log.pos, 0, this.pos, 0, SIZE);
            this.direction = log.direction;
        }

        public void set(int index, int x, int y) {
            pos[index] = x*N + y;
        }

        public void setDirection() {
            this.direction = pos[1] - pos[0] == 1;
        }

        public void moveX(int dir) {
            for (int i=0; i<SIZE; i++) {
                pos[i] += dir;
            }
        }

        public boolean isMovableX(int dir) {
            boolean isPossible = true;
            if (dir == POS_MOVE) {
                isPossible =  pos[2] % N < N -1;
            } else {
                isPossible = pos[0] % N > 0;
            }
            if (!isPossible) return false;

            return isEmptySpace(dir);
        }

        public void moveY(int dir) {
            for (int i=0; i<SIZE; i++) {
                pos[i] += N*dir;
            }
        }

        public boolean isMovableY(int dir) {
            boolean isPossible = true;
            if (dir == POS_MOVE) {
                isPossible = pos[2] / N < N-1;
            } else {
                isPossible = pos[0] / N > 0;
            }
            if (!isPossible) return false;

            return isEmptySpace(dir*N);
        }

        public boolean isEmptySpace(int dirDiff) {
            for (int i=0; i<SIZE; i++) {
                int nextPos = pos[i] + dirDiff;
                if (map[nextPos/N][nextPos%N] == TREE) {
                    return false;
                }
            }
            return true;
        }

        public void rotate() {
            if (direction == HOR) {
                pos[0] -= (N-1);
                pos[2] += (N-1);
            } else {
                pos[0] += (N-1);
                pos[2] -= (N-1);
            }
            direction = !direction;
        }

        public boolean isRotatable() {
            boolean isPossible = true;
            if (direction == HOR) {
                isPossible = isMovableY(POS_MOVE) && isMovableY(NEG_MOVE);
            } else {
                isPossible = isMovableX(POS_MOVE) && isMovableX(NEG_MOVE);
            }
            return isPossible;
        }

        public boolean isGoal() {
            int cnt = 0;
            for (int i=0; i<SIZE; i++) {
                if (map[pos[i]/N][pos[i]%N] == GOAL) {
                    cnt++;
                }
            }
            return cnt == SIZE;
        }

        public int getCenterX() {
            return pos[1]/N;
        }

        public int getCenterY() {
            return pos[1]%N;
        }

        @Override
        public String toString() {
            return "Log{" +
                    "pos=" + Arrays.toString(pos) +
                    ", direction=" + direction +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        Log log = new Log();
        int logIndex = 0;

        for (int i=0; i<N; i++) {
            char[] input = br.readLine().toCharArray();
            for (int j=0; j<N; j++) {
                map[i][j] = input[j];
                if (map[i][j] == LOG) {
                    log.set(logIndex++, i, j);
                }
            }
        }

        log.setDirection();

        sb.append(bfs(log));

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }

    public static int bfs(Log log) {
        Deque<Log> q = new ArrayDeque<>();
        boolean[][][] visited = new boolean[2][N][N];
        q.add(log);
        visited[log.direction ? HOR : VER][log.getCenterX()][log.getCenterY()] = true;
        int cnt = 0;

        while (!q.isEmpty()) {
            cnt++;
            int size = q.size();

            while(size-- >0) {
                Log now = q.poll();

                for (int move=0; move<5; move++) {
                    Log nextLog = new Log(now);
                    switch(move) {
                        case 0:
                            if (nextLog.isMovableX(POS_MOVE)) {
                                nextLog.moveX(POS_MOVE);
                            }
                            break;
                        case 1:
                            if (nextLog.isMovableX(NEG_MOVE)) {
                                nextLog.moveX(NEG_MOVE);
                            }
                            break;
                        case 2:
                            if (nextLog.isMovableY(POS_MOVE)) {
                                nextLog.moveY(POS_MOVE);
                            }
                            break;
                        case 3:
                            if (nextLog.isMovableY(NEG_MOVE)) {
                                nextLog.moveY(NEG_MOVE);
                            }
                            break;
                        case 4:
                            if (nextLog.isRotatable()) {
                                nextLog.rotate();
                            }
                            break;
                    }
                    if (!visited[nextLog.direction ? HOR : VER][nextLog.getCenterX()][nextLog.getCenterY()]) {
                        if (nextLog.isGoal()) {
                            return cnt;
                        }
                        visited[nextLog.direction ? HOR : VER][nextLog.getCenterX()][nextLog.getCenterY()] = true;
                        q.add(nextLog);
                    }
                }
            }
        }
        return IMPOSSIBLE;
    }
}