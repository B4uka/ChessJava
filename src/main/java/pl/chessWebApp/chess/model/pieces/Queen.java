package pl.chessWebApp.chess.model.pieces;

import pl.chessWebApp.chess.model.board.Board;
import pl.chessWebApp.chess.model.board.PossibleActions;
import pl.chessWebApp.chess.model.piece_properties.Color;
import pl.chessWebApp.chess.model.piece_properties.Position;
import pl.chessWebApp.chess.model.pieces.MoveGenerator.StandardMoveGenerator;

public class Queen extends Piece implements StandardMoveGenerator {

    public Queen (Position position, Color color, String code) {
        super(position, color, code);
    }

    public Queen (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        moveGenerator(this.position, board, possibleActions, 0, 1);
        moveGenerator(this.position, board, possibleActions, 0, -1);
        moveGenerator(this.position, board, possibleActions, 1, 0);
        moveGenerator(this.position, board, possibleActions, -1, 0);

        moveGenerator(this.position, board, possibleActions, 1, 1);
        moveGenerator(this.position, board, possibleActions, 1, -1);
        moveGenerator(this.position, board, possibleActions, -1, 1);
        moveGenerator(this.position, board, possibleActions, -1, -1);

        return possibleActions;
    }

    @Override
    public PossibleActions moveGenerator (Position queenPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {
        queenPosition = queenPosition.getNewPositionByVector(rowShift, columnShift);

        while (queenPosition.isOnBoard()) {
            if (this.color == Color.BLACK && board.isOccupiedByColor(queenPosition, Color.BLACK)
                    || this.color == Color.WHITE && board.isOccupiedByColor(queenPosition, Color.WHITE)) {
                break;
            } else if (this.color == Color.WHITE && board.isOccupiedByColor(queenPosition, Color.BLACK)
                    || (this.color == Color.BLACK && board.isOccupiedByColor(queenPosition, Color.WHITE))) {
                possibleActions.addPossibleCapture(queenPosition);
                return possibleActions;
            } else if (!board.isOccupied(queenPosition)) {
                possibleActions.addPossibleMove(queenPosition);
                queenPosition = queenPosition.getNewPositionByVector(rowShift, columnShift);
            }
        }
        return possibleActions;
    }
}
