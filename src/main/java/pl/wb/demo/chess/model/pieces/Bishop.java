package pl.wb.demo.chess.model.pieces;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.MoveGenerator.StandardMoveGenerator;

public class Bishop extends Piece implements StandardMoveGenerator {

    public Bishop (Position position, Color color, String code) {
        super(position, color, code);
    }

    public Bishop (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        moveGenerator(this.position, board, possibleActions, 1, 1);
        moveGenerator(this.position, board, possibleActions, 1, -1);
        moveGenerator(this.position, board, possibleActions, -1, 1);
        moveGenerator(this.position, board, possibleActions, -1, -1);

        return possibleActions;
    }

    @Override
    public PossibleActions moveGenerator (Position bishopPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {
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