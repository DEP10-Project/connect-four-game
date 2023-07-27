package lk.ijse.dep.service;

public class BoardImpl implements Board{
    private final Piece[][] pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
    private final BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {//display the board in the UI
        this.boardUI = boardUI;

        /*initialize all the pieces of the 2D array to Piece.EMPTY*/
        for (int row = 0; row <NUM_OF_ROWS; row++) {
            for (int col = 0; col <NUM_OF_COLS; col++) {//Todo
                pieces[col][row] = Piece.EMPTY;
            }
        }
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public BoardUI getBoardUI() {
        return boardUI;
    }

    public int findNextAvailableSpot(int col) {//finds the next available row in the given column to place a piece.
        for (int row = 0; row < NUM_OF_ROWS; row++) {
            if (pieces[col][row] == Piece.EMPTY) {
                return row;
            }
        }
        return -1;// no free spaces
    }

    public boolean isLegalMoves(int col) {
        return findNextAvailableSpot(col) !=-1;
    }


    public boolean exitLegalMoves() {//Checks if there exists any legal moves on the board.
        for (int i = 0; i < NUM_OF_COLS; i++) {
            if(isLegalMoves(i)) return true;
        }
        return false;
    }
    public void updateMove(int col, Piece move) {// updates the movements
        pieces[col][findNextAvailableSpot(col)] = move;
    }public void updateMove(int col,int row, Piece move) {// updates the movements
        pieces[col][row] = move;
    }

    public Winner findWinner() {
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS-3; j++) {
                if(pieces[i][j] == Piece.EMPTY && pieces[i][j] != pieces[i][j+1]) continue;

                if(pieces[i][j] != Piece.EMPTY && pieces[i][j] == pieces[i][j+1] &&
                        pieces[i][j] == pieces[i][j+2] && pieces[i][j] == pieces[i][j+3]){
                    return pieces[i][j] == Piece.GREEN? new Winner(Piece.GREEN, i, j, i, j +3):
                            new Winner(Piece.BLUE, i, j, i, j +3);
                }
            }
        }

        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS-3; j++) {
                if(pieces[j][i] == Piece.EMPTY && pieces[j][i] != pieces[j+1][i]) continue;

                if(pieces[j][i] != Piece.EMPTY && pieces[j][i] == pieces[j+1][i] &&
                        pieces[j][i] == pieces[j+2][i] && pieces[j][i] == pieces[j+3][i]){
                    return pieces[j][i] == Piece.GREEN? new Winner(Piece.GREEN, j, i, j + 3, i):
                            new Winner(Piece.BLUE, j, i, j + 3, i);
                }
            }
        }
        return new Winner(Piece.EMPTY);

    }
}
