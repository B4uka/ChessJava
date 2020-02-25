package pl.wb.demo.chess.model.pieces;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;

public class Knight extends Piece implements MoveValidation {

    public Knight (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        moveValidation(this.position, board, possibleActions, 1, 2);
        moveValidation(this.position, board, possibleActions, 1, -2);
        moveValidation(this.position, board, possibleActions, 2, 1);
        moveValidation(this.position, board, possibleActions, 2, -1);

        moveValidation(this.position, board, possibleActions, -1, 2);
        moveValidation(this.position, board, possibleActions, -1, -2);
        moveValidation(this.position, board, possibleActions, -2, 1);
        moveValidation(this.position, board, possibleActions, -2, -1);

        return possibleActions;
    }

    @Override
    public PossibleActions moveValidation (Position knightMove, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {

        knightMove = knightMove.getNewPositionByVector(rowShift, columnShift);

        if (this.color == Color.WHITE && knightMove.isOnBoard()) {
            if (!board.isOccupied(knightMove)) {
                possibleActions.addPossibleMove(knightMove);
            } else if (board.isOccupiedByColor(knightMove, Color.BLACK) && !board.isOccupiedBySpecificPiece(knightMove, Color.BLACK, King.class)) {
                possibleActions.addPossibleCapture(knightMove);
            }
        } else if (this.color == Color.BLACK && knightMove.isOnBoard()) {
            if (!board.isOccupied(knightMove)) {
                possibleActions.addPossibleMove(knightMove);
            } else if (board.isOccupiedByColor(knightMove, Color.WHITE) && !board.isOccupiedBySpecificPiece(knightMove, Color.WHITE, King.class)) {
                possibleActions.addPossibleCapture(knightMove);
            }
        }
        return possibleActions;
    }
}
