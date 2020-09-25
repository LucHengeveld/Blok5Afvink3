class TicTacToeComputer {

    // Het bord //
    private final int[][] Bord;
    // Lege waarde //
    public static final int EMPTY = 0;
    // Speler 1 //
    public static final int ONE = 1;
    // Speler 2 (computer) //
    public static final int TWO = 2;

    public TicTacToeComputer() {
        Bord = new int[3][3];
    }

    // Get de positie op het bord //
    public int getBordValue(int i,int j) {
        if(i < 0 || i >= 3) return EMPTY;
        if(j < 0 || j >= 3) return EMPTY;
        return Bord[i][j];
    }

    // Set de positie op het bord //
    public void setBordValue(int i,int j,int token) {
        if(i < 0 || i >= 3) return;
        if(j < 0 || j >= 3) return;
        Bord[i][j] = token;
    }

    // Berekend winnende zet //
    public int []nextWinningMove(int token) {

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(getBordValue(i, j)==EMPTY) {
                    Bord[i][j] = token;
                    boolean win = isWin(token);
                    Bord[i][j] = EMPTY;
                    if(win) return new int[]{i,j};
                }

        return null;
    }

    public int inverse(int token) {
        return token==ONE ? TWO : ONE;
    }

    // Berekend beste volgende zet //
    public int []nextMove(int token) {

        // Computer begint in midden als dit niet is gekozen//
        if(getBordValue(1, 1)==EMPTY) return new int[]{1,1};

        // Volgende beurt als er niet is gewonnen//
        int[] winMove = nextWinningMove(token);
        if(winMove!=null) return winMove;

        // Computer kiest waarde zodat speler niet 3 op een rij krijgt //
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(getBordValue(i, j)==EMPTY)
                {
                    Bord[i][j] = token;
                    boolean ok = nextWinningMove(inverse(token)) == null;
                    Bord[i][j] = EMPTY;
                    if(ok) return new int[]{i,j};
                }

        // Kies een vak en return de waarde ervan op//
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(getBordValue(i, j)==EMPTY)
                    return new int[]{i,j};

        // Geen mogelijke move
        return null;
    }

    // Kijkt of speler of pc heeft gewonnen //
    public boolean isWin(int token) {
        final int[] DI ={-1,0,1,1};
        final int[] DJ ={1,1,1,0};
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++) {

                if(getBordValue(i, j)!=token) continue;

                for(int k=0;k<4;k++) {
                    int ctr = 0;
                    while(getBordValue(i+DI[k]*ctr, j+DJ[k]*ctr)==token) ctr++;

                    if(ctr==3) return true;
                }
            }
        return false;
    }
}
