package lk.ijse.dep.service;

import static jdk.nashorn.internal.objects.Global.Infinity;

public class AiPlayer extends Player {

    //    public boolean
    public final Piece[][] pieces = new Piece[Board.NUM_OF_COLS][Board.NUM_OF_ROWS];
    public int heuristicVal;
    public int maxEval;
    public int minEval;

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

    int miniMax(int depth, boolean maximizingPlayer) {
        Piece winningPiece = board.findWinner().getWinningPiece();

        if (winningPiece == Piece.GREEN) {//AI won
            return 1;
        } else if (winningPiece == Piece.BLUE) {// Human won
            return -1;
        } else if (!board.exitLegalMoves() || depth == 4) return 0;// check the AI or Human win or depth ==4

        if (maximizingPlayer) {
            maxEval = (int) -Infinity;
            for (int i = 0; i < Board.NUM_OF_COLS; i++) {
                if (!board.isLegalMoves(i)) continue;

                int row = board.findNextAvailableSpot(i);
                board.updateMove(i, Piece.GREEN);// put a ball & check
                heuristicVal = miniMax(depth + 1, false);
                maxEval = Math.max(heuristicVal, maxEval);
                board.updateMove(i, row, Piece.EMPTY);// remove above put ball
                if (heuristicVal==1) return heuristicVal;
            }
            return maxEval;
        } else {
            minEval = 10;
            for (int i = 0; i < Board.NUM_OF_COLS; i++) {
                if (!board.isLegalMoves(i)) continue;

                int row = board.findNextAvailableSpot(i);
                board.updateMove(i, Piece.BLUE);// put a ball & check
                heuristicVal = miniMax(depth + 1, true);//Todo
                minEval = Math.min(heuristicVal, minEval);
                board.updateMove(i, row, Piece.EMPTY);// remove above put ball
            }
            return minEval;
        }
    }

    private int predictColumn() {

        int bestScore = -10;
        int selectedColumn = -1;
        Boolean isUserWinning = false;//1

        for (int i = 0; i < Board.NUM_OF_COLS; i++) {
            if (!board.isLegalMoves(i)) continue;// find a legal free space in board

            int row = board.findNextAvailableSpot(i);// get row in relevant to the legal column
            board.updateMove(i, Piece.GREEN);// put a ball & check winning chances to AI
            heuristicVal = miniMax(0, false);
            System.out.println("**"+heuristicVal);
            bestScore = Math.max(heuristicVal, bestScore);
            board.updateMove(i, row, Piece.EMPTY);// remove above put ball
            if (heuristicVal == 1) {
                System.out.println("col"+i);
                return i;
            } else if (heuristicVal == 0) {
                selectedColumn = i;
                //System.out.println("0 awa "+i);
            } else if (heuristicVal == -1 &&  bestScore != 0) {
                selectedColumn = i;
                isUserWinning = true;
            }
        }
        if (isUserWinning && board.isLegalMoves(selectedColumn)) {
            return selectedColumn;
        }else {// select random column
            selectedColumn = 0;
            do {
                selectedColumn = (int) (Math.random() * 6);
            } while (!board.isLegalMoves(selectedColumn));
            System.out.println("colmvv" + selectedColumn);
            return selectedColumn;
        }
    }
}
