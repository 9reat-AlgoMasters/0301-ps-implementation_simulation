import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Q2931 {
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static final int DOWN = 0;
    static final int RIGHT = 1;
    static final int UP = 2;
    static final int LEFT = 3;

    static final char EMPTY = '.';
    static final char BLOCK_VER = '|';
    static final char BLOCK_HOR = '-';
    static final char BLOCK_CROSS = '+';
    static final char BLOCK_ONE = '1';
    static final char BLOCK_TWO = '2';
    static final char BLOCK_THREE = '3';
    static final char BLOCK_FOUR = '4';
    static Map<Character, Map<Integer, Integer>> blocks; // 블록 문자 : (들어오는 방향 : 다음 방향)
    static int N, M;
    static char[][] map;
    static Point start;
    static Point end;
    static Point answerPoint;
    static char answerBlock;
    static boolean findAns = false;
    static int blockCnt = 0;

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        initBlocks();

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];

        for (int i = 0; i < N; i++) {
            char[] input = br.readLine().toCharArray();
            for (int j = 0; j < M; j++) {
                map[i][j] = input[j];
                if (map[i][j] == 'M') {
                    start = new Point(i, j);
                } else if (map[i][j] == 'Z') {
                    end = new Point(i, j);
                } else if (isBlock(map[i][j])) {
                    blockCnt++;
                    if (map[i][j] == BLOCK_CROSS) {
                        blockCnt++;
                    }
                }
            }
        }

        for (int dir = 0; dir < 4; dir++) {
            if (findAns) break;
            dfs(start.x, start.y, dir, true, blockCnt);
        }

        sb.append(answerPoint.x+1).append(" ")
                .append(answerPoint.y+1).append(" ")
                .append(answerBlock);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
    private static void initBlocks() {
        blocks = new HashMap<>();

        // 블록 '|'
        blocks.put(BLOCK_VER, new HashMap<>());
        blocks.get(BLOCK_VER).put(UP, UP);
        blocks.get(BLOCK_VER).put(DOWN, DOWN);

        // 블록 '-'
        blocks.put(BLOCK_HOR, new HashMap<>());
        blocks.get(BLOCK_HOR).put(RIGHT, RIGHT);
        blocks.get(BLOCK_HOR).put(LEFT, LEFT);

        // 블록 '+'
        blocks.put(BLOCK_CROSS, new HashMap<>());
        blocks.get(BLOCK_CROSS).put(UP, UP);
        blocks.get(BLOCK_CROSS).put(DOWN, DOWN);
        blocks.get(BLOCK_CROSS).put(RIGHT, RIGHT);
        blocks.get(BLOCK_CROSS).put(LEFT, LEFT);

        // 블록 '1'
        blocks.put(BLOCK_ONE, new HashMap<>());
        blocks.get(BLOCK_ONE).put(UP, RIGHT);
        blocks.get(BLOCK_ONE).put(LEFT, DOWN);

        // 블록 '2'
        blocks.put(BLOCK_TWO, new HashMap<>());
        blocks.get(BLOCK_TWO).put(DOWN, RIGHT);
        blocks.get(BLOCK_TWO).put(LEFT, UP);

        // 블록 '3'
        blocks.put(BLOCK_THREE, new HashMap<>());
        blocks.get(BLOCK_THREE).put(RIGHT, UP);
        blocks.get(BLOCK_THREE).put(DOWN, LEFT);

        // 블록 '4'
        blocks.put(BLOCK_FOUR, new HashMap<>());
        blocks.get(BLOCK_FOUR).put(RIGHT, DOWN);
        blocks.get(BLOCK_FOUR).put(UP, LEFT);
    }

    private static void dfs(int x, int y, int dir, boolean canUsePiece, int blockCnt) {
        if (findAns) {
            return;
        }

        int nextX = x + DIR[dir][0];
        int nextY = y + DIR[dir][1];
        if (isNotInRange(nextX, nextY)) {
            return;
        }

        if (nextX == end.x && nextY == end.y) { // 도착 지점이면
            if (blockCnt == 0) {
                findAns = true;
            }
            return;
        }

        if (map[nextX][nextY] == EMPTY) { // 조각이 없으면
            if (!canUsePiece) {
                return; // 이미 이전에 조각을 채웠다면/
            }
            for (Map.Entry<Character, Map<Integer, Integer>> block : blocks.entrySet()) {
                if (block.getValue().containsKey(dir)) { // 현재 방향으로 이 조각을 쓸 수 있으면
                    map[nextX][nextY] = block.getKey();
                    answerPoint = new Point(nextX, nextY);
                    answerBlock = block.getKey();
                    dfs(nextX, nextY, block.getValue().get(dir), false, block.getKey() == BLOCK_CROSS ? blockCnt+1 : blockCnt);
                    if (findAns) {
                        return;
                    }
                    map[nextX][nextY] = EMPTY;
                }
            }
        }

        if (blocks.containsKey(map[nextX][nextY])) { // 조각이 있으면
            if(blocks.get(map[nextX][nextY]).containsKey(dir)) { // 그 조각으로 들어갈 수 있으면
                dfs(nextX, nextY, blocks.get(map[nextX][nextY]).get(dir), canUsePiece, blockCnt-1);
            }
        }
    }

    private static boolean isBlock(char c) {
        return blocks.containsKey(c);
    }

    private static boolean isNotInRange(int x, int y) {
        return x < 0 || y < 0 || x >= N || y >= M;
    }
}
















