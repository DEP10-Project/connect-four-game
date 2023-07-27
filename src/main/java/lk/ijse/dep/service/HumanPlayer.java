package lk.ijse.dep.service;

public class HumanPlayer extends Player{
    public HumanPlayer(Board board) {
        super(board);
    }

    @Override
    public void movePiece(int col) {
//        System.out.println(board);

        if (!board.isLegalMoves(col)) return;
        board.updateMove(col, Piece.BLUE);
        board.getBoardUI().update(col, true);

        Winner winner = board.findWinner();

        if(winner.getWinningPiece() == Piece.EMPTY && board.exitLegalMoves()) return;
        board.getBoardUI().notifyWinner(winner);

    }
}
