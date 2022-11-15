package Practicing;

public class abrar {
    static void  toggle(char[][] board, int r, int c, char wanted) {
        if (board[r][c] == wanted) board[r][c] = '.'; else board[r][c] = wanted;
    }

    public static String[] enclose(int A) {
        if (A % 2 == 1) return new String[0];
        if (A > 2500 - 50 - 48) return new String[0];

        char[][] board = new char[50][50];
        for (int i=0; i<50; ++i) for (int j=0; j<50; ++j) board[i][j] = '.';

        int squaresWanted = A/2;
        int[] X = new int[squaresWanted];
        int[] Y = new int[squaresWanted];
        int id = 0;
        for (int x=0; x<=24; ++x) for (int y=x; y<=48-x; ++y){
            System.out.println(x + " " + y);
            if (id < squaresWanted) { X[id] = x; Y[id] = y; ++id; }}
        for (int x=-1; x>=-24; --x) for (int y=-x; y<=48+x; ++y)
            if (id < squaresWanted) { X[id] = x; Y[id] = y; ++id; }

        for (int i=0; i<squaresWanted; ++i) {
            int dr = Y[i]-X[i], dc = Y[i]+X[i];
            toggle(board,dr,dc,'/');
            toggle(board,dr+1,dc,'\\');
            toggle(board,dr,dc+1,'\\');
            toggle(board,dr+1,dc+1,'/');
        }

        String[] answer = new String[50];
        for (int r=0; r<50; ++r) {
            answer[r] = "";
            for (int c=0; c<50; ++c) answer[r] += board[r][c];
        }
        return answer;
    }
    public static void main(String[] args) {
        String [] ans = enclose(2402) ;
        System.out.println(ans);
        for(int i= 0; i < ans.length ; ++i)
            System.out.println(ans[i]);
    }
}
