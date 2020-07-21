package pl.chessWebApp.chess.model.pieces;

import pl.chessWebApp.chess.model.board.Board;
import pl.chessWebApp.chess.model.board.PossibleActions;
import pl.chessWebApp.chess.model.piece_properties.Color;
import pl.chessWebApp.chess.model.piece_properties.Position;
import pl.chessWebApp.chess.model.pieces.MoveGenerator.CaptureGenerator;
import pl.chessWebApp.chess.model.pieces.MoveGenerator.StandardMoveGenerator;

public class Pawn extends Piece implements StandardMoveGenerator, CaptureGenerator {

    public Pawn (Position position, Color color, String code) {
        super(position, color, code);
    }

    public Pawn (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {

        PossibleActions possibleActions = new PossibleActions();

        if (this.color == Color.BLACK) {
            moveGenerator(this.position, board, possibleActions, 1, 0);
            captureGenerator(this.position, board, possibleActions, 1, 1, Color.WHITE);
            captureGenerator(this.position, board, possibleActions, 1, -1, Color.WHITE);
        } else if (this.color == Color.WHITE) {
            moveGenerator(this.position, board, possibleActions, -1, 0);
            captureGenerator(this.position, board, possibleActions, -1, 1, Color.BLACK);
            captureGenerator(this.position, board, possibleActions, -1, -1, Color.BLACK);
        }
        return possibleActions;
    }

    @Override
    public PossibleActions moveGenerator (Position pawnPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {
        //potential position knowing row and columns shifts
        Position pawnPossibleMovePosition = pawnPosition.getNewPositionByVector(rowShift, columnShift);

        if (!board.isOccupied(pawnPossibleMovePosition)) {
            possibleActions.addPossibleMove(pawnPossibleMovePosition);
            if ((this.position.getRow() == 1 && pawnPossibleMovePosition.getRow() == 2) || (this.position.getRow() == 6 && pawnPossibleMovePosition.getRow() == 5)) {
                moveGenerator(pawnPossibleMovePosition, board, possibleActions, rowShift, columnShift);
            }
        }
        return possibleActions;
    }

    @Override
    public PossibleActions captureGenerator (Position pawnCapture, Board board, PossibleActions possibleActions, int rowShift, int columnShift, Color opponentsColor) {

        pawnCapture = pawnCapture.getNewPositionByVector(rowShift, columnShift);

        // TODO: En passant
        if (pawnCapture.isOnBoard() && board.isOccupiedByColor(pawnCapture, opponentsColor) && !board.isOccupiedBySpecificPiece(pawnCapture, opponentsColor, King.class)) {
            possibleActions.addPossibleCapture(pawnCapture);
        }
        return possibleActions;
    }
}
