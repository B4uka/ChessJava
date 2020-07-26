package pl.chessWebApp.chess.model.pieces;

import pl.chessWebApp.chess.model.board.Board;
import pl.chessWebApp.chess.model.piece_properties.Color;
import pl.chessWebApp.chess.model.piece_properties.Position;
import pl.chessWebApp.chess.model.board.PossibleActions;
import pl.chessWebApp.chess.model.pieces.MoveGenerator.StandardMoveGenerator;

public class Knight extends Piece implements StandardMoveGenerator {

    public Knight (Position position, Color color, String code) {
        super(position, color, code);
    }

    public Knight (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        moveGenerator(this.position, board, possibleActions, 1, 2);
        moveGenerator(this.position, board, possibleActions, 1, -2);
        moveGenerator(this.position, board, possibleActions, 2, 1);
        moveGenerator(this.position, board, possibleActions, 2, -1);

        moveGenerator(this.position, board, possibleActions, -1, 2);
        moveGenerator(this.position, board, possibleActions, -1, -2);
        moveGenerator(this.position, board, possibleActions, -2, 1);
        moveGenerator(this.position, board, possibleActions, -2, -1);

        return possibleActions;
    }

    @Override
    public PossibleActions moveGenerator (Position knightPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {
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
