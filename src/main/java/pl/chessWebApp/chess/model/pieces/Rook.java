package pl.chessWebApp.chess.model.pieces;

import pl.chessWebApp.chess.model.board.Board;
import pl.chessWebApp.chess.model.board.PossibleActions;
import pl.chessWebApp.chess.model.piece_properties.Color;
import pl.chessWebApp.chess.model.piece_properties.Position;
import pl.chessWebApp.chess.model.pieces.MoveGenerator.StandardMoveGenerator;

public class Rook extends Piece implements StandardMoveGenerator {

    public Rook (Position position, Color color, String code) {
        super(position, color, code);
    }

    public Rook (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        moveGenerator(this.position, board, possibleActions, 0, 1);
        moveGenerator(this.position, board, possibleActions, 0, -1);
        moveGenerator(this.position, board, possibleActions, 1, 0);
        moveGenerator(this.position, board, possibleActions, -1, 0);

        return possibleActions;
    }

    @Override
    public PossibleActions moveGenerator (Position rookPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {
        Position rookPossibleMovePosition = rookPosition.getNewPositionByVector(rowShift, columnShift);

        while (rookPossibleMovePosition.isOnBoard()) {
            if (this.color == Color.BLACK && board.isOccupiedByColor(rookPossibleMovePosition, Color.BLACK)
                    || this.color == Color.WHITE && board.isOccupiedByColor(rookPossibleMovePosition, Color.WHITE)) {
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