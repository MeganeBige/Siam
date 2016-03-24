package siam.player;

public class Player {

    protected Camp camp;
    protected String name;
    protected int pieceOnBoard;
    protected int pieceOut;

    public Player(Camp camp, String name) {
        this.camp = camp;
        this.name = name;
        pieceOnBoard = 0;
        pieceOut = 0;
    }

    public Camp getCamp() {
        return camp;
    }

    public String getName() {
        return name;
    }

    public void put() {
        pieceOnBoard++;
    }

    public void bringOut() {
        pieceOnBoard--;
        pieceOut++;
    }

    public boolean canPut(){
        return pieceOnBoard < 5;
    }

    public boolean canBringOut() {
        return pieceOut < 1;
    }

    public int getPieceOnBoard() {
        return pieceOnBoard;
    }
}
