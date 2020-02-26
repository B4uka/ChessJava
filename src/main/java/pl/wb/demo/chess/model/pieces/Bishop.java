package pl.wb.demo.chess.model.pieces;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.ValidationForMovesChecksCaptures.MoveValidation;

public class Bishop extends Piece implements MoveValidation {

    public Bishop (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        moveValidation(this.position, board, possibleActions, 1, 1);
        moveValidation(this.position, board, possibleActions, 1, -1);
        moveValidation(this.position, board, possibleActions, -1, 1);
        moveValidation(this.position, board, possibleActions, -1, -1);

        return possibleActions;
    }

    @Override
    public PossibleActions moveValidation (Position bishopPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {
        //first iteration - potential position knowing row and columns shifts
        Position bishopPossibleMovePosition = bishopPosition.getNewPositionByVector(rowShift, columnShift);

        while (bishopPossibleMovePosition.isOnBoard()) {
            if (this.color == Color.BLACK && board.isOccupiedByColor(bishopPossibleMovePosition, Color.BLACK)
                    || this.color == Color.WHITE && board.isOccupiedByColor(bishopPossibleMovePosition, Color.WHITE)) {
                break;
            } else if (this.color == Color.WHITE && board.isOccupiedByColor(bishopPossibleMovePosition, Color.BLACK)
                    || (this.color == Color.BLACK && board.isOccupiedByColor(bishopPossibleMovePosition, Color.WHITE))) {
                possibleActions.addPossibleCapture(bishopPossibleMovePosition);
                return possibleActions;
            } else if (!board.isOccupied(bishopPossibleMovePosition)) {
                possibleActions.addPossibleMove(bishopPossibleMovePosition);
                bishopPossibleMovePosition = bishopPossibleMovePosition.getNewPositionByVector(rowShift, columnShift);
            }
        }
        return possibleActions;
    }
}
//    public void bishopMoveValidation (Position bishopPosition, Board board, PossibleActions possibleActions, int directionForRow, int directionForColumn){
//
//        while (bishopPosition.isOnBoard() && (this.color == Color.BLACK && !board.isOccupiedByColor(bishopPosition, Color.BLACK)) ||
//                (this.color == Color.WHITE && !board.isOccupiedByColor(bishopPosition, Color.WHITE))) {
//            if (!board.isOccupied(bishopPosition)) {
//                possibleActions.addPossibleMove(bishopPosition);
//            } else if (this.color == Color.WHITE && board.isOccupiedBySpecificPiece(bishopPosition, Color.BLACK, King.class)) {
//                possibleActions.addPossibleChecks(bishopPosition);
//                break;
//            } else if (this.color == Color.WHITE && board.isOccupiedByColor(bishopPosition, Color.BLACK)) {
//                possibleActions.addPossibleCapture(bishopPosition);
//                break;
//            } else if (this.color == Color.BLACK && board.isOccupiedBySpecificPiece(bishopPosition, Color.WHITE, King.class)) {
//                possibleActions.addPossibleChecks(bishopPosition);
//                break;
//            } else if (this.color == Color.BLACK && board.isOccupiedByColor(bishopPosition, Color.WHITE)) {
//                possibleActions.addPossibleCapture(bishopPosition);
//                break;
//            }
//            bishopPosition = bishopPosition.getNewPositionByVector(directionForRow, directionForColumn);
//        }
//    }