package pl.wb.demo.chess.model.pieces;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.ValidationForMovesChecksCaptures.MoveValidation;

public class Rook extends Piece implements MoveValidation {

    public Rook (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        moveValidation(this.position, board, possibleActions, 0, 1);
        moveValidation(this.position, board, possibleActions, 0, -1);
        moveValidation(this.position, board, possibleActions, 1, 0);
        moveValidation(this.position, board, possibleActions, -1, 0);

        return possibleActions;
    }

    @Override
    public PossibleActions moveValidation (Position rookPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {
        //first iteration - potential position knowing row and columns shifts
        Position rookPossibleMovePosition = rookPosition.getNewPositionByVector(rowShift, columnShift);

        while (rookPossibleMovePosition.isOnBoard()) {
            if(this.color == Color.BLACK && board.isOccupiedByColor(rookPossibleMovePosition, Color.BLACK)
                    || this.color == Color.WHITE && board.isOccupiedByColor(rookPossibleMovePosition, Color.WHITE)){
                break;
            } else if (this.color == Color.WHITE && board.isOccupiedByColor(rookPossibleMovePosition, Color.BLACK)
                    || (this.color == Color.BLACK && board.isOccupiedByColor(rookPossibleMovePosition, Color.WHITE))) {
                possibleActions.addPossibleCapture(rookPossibleMovePosition);
                return possibleActions;
            } else if (!board.isOccupied(rookPossibleMovePosition)) {
                possibleActions.addPossibleMove(rookPossibleMovePosition);
                rookPossibleMovePosition = rookPossibleMovePosition.getNewPositionByVector(rowShift, columnShift);
            }
        }
        return possibleActions;
    }
}