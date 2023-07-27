package lk.ijse.dep.service;

public class AiPlayer extends Player{
    public final Piece[][] pieces = new Piece[Board.NUM_OF_COLS][Board.NUM_OF_ROWS];

    public AiPlayer(Board board) {
        super(board);
    }

    @Override
    public void movePiece(int col) {// updating UI and check for the winner
        col=predictColumn();

        // Update the board and notify the UI
        board.updateMove(col, Piece.GREEN);
        board.getBoardUI().update(col, false);

        //Check if there is a winner
        Winner winner = board.findWinner();

        if (winner.getWinningPiece() == Piece.EMPTY && board.exitLegalMoves()) return;
        board.getBoardUI().notifyWinner(winner);
    }

    private int predictColumn() {
        return 0;
    }
}
