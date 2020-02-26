package pl.wb.demo.chess.model.pieces;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.ValidationForMovesChecksCaptures.MoveValidation;

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
    public PossibleActions moveValidation (Position knightPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {
        //potential position knowing row and columns shifts
        Position knightPossibleMovePosition = knightPosition.getNewPositionByVector(rowShift, columnShift);

        if (this.color == Color.WHITE && knightPossibleMovePosition.isOnBoard()) {
            if (!board.isOccupied(knightPossibleMovePosition)) {
                possibleActions.addPossibleMove(knightPossibleMovePosition);
            } else if (board.isOccupiedByColor(knightPossibleMovePosition, Color.BLACK) && !board.isOccupiedBySpecificPiece(knightPossibleMovePosition, Color.BLACK, King.class)) {
                possibleActions.addPossibleCapture(knightPossibleMovePosition);
            }
        } else if (this.color == Color.BLACK && knightPossibleMovePosition.isOnBoard()) {
            if (!board.isOccupied(knightPossibleMovePosition)) {
                possibleActions.addPossibleMove(knightPossibleMovePosition);
            } else if (board.isOccupiedByColor(knightPossibleMovePosition, Color.WHITE) && !board.isOccupiedBySpecificPiece(knightPossibleMovePosition, Color.WHITE, King.class)) {
                possibleActions.addPossibleCapture(knightPossibleMovePosition);
            }
        }
        return possibleActions;
    }
}
