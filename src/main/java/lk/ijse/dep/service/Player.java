package lk.ijse.dep.service;

public abstract class Player {//provides a common interface for human & AI
    protected Board board;
    public Player(Board board) {
        this.board=board;
    }
    public abstract void movePiece(int col);
}

