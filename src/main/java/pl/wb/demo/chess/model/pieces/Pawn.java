package pl.wb.demo.chess.model.pieces;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.ValidationForMovesChecksCaptures.MoveValidation;

public class Pawn extends Piece implements MoveValidation {

    public Pawn (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {

        PossibleActions possibleActions = new PossibleActions();

        if (this.color == Color.BLACK) {
            moveValidation(this.position, board, possibleActions, 1, 0);
            captureValidation(this.position, board, possibleActions, 1, 1, Color.WHITE);
            captureValidation(this.position, board, possibleActions, 1, -1, Color.WHITE);
        } else if (this.color == Color.WHITE) {
            moveValidation(this.position, board, possibleActions, -1, 0);
            captureValidation(this.position, board, possibleActions, -1, 1, Color.BLACK);
            captureValidation(this.position, board, possibleActions, -1, -1, Color.BLACK);
        }
        return possibleActions;
    }

    @Override
    public PossibleActions moveValidation (Position pawnPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {
        //potential position knowing row and columns shifts
        Position pawnPossibleMovePosition = pawnPosition.getNewPositionByVector(rowShift, columnShift);

        if (!board.isOccupied(pawnPossibleMovePosition)) {
            possibleActions.addPossibleMove(pawnPossibleMovePosition);
            if ((this.position.getRow() == 1 && pawnPossibleMovePosition.getRow() == 2) || (this.position.getRow() == 6 && pawnPossibleMovePosition.getRow() == 5)) {
                moveValidation(pawnPossibleMovePosition, board, possibleActions, rowShift, columnShift);
            }
        }
        return possibleActions;
    }


    public PossibleActions captureValidation (Position pawnCapture, Board board, PossibleActions possibleActions, int rowShift, int columnShift, Color opponentsColor) {

        pawnCapture = pawnCapture.getNewPositionByVector(rowShift, columnShift);

        // TODO: En passant
        if (pawnCapture.isOnBoard() && board.isOccupiedByColor(pawnCapture, opponentsColor) && !board.isOccupiedBySpecificPiece(pawnCapture, opponentsColor, King.class)) {
            possibleActions.addPossibleCapture(pawnCapture);
        }
        return possibleActions;
    }
}
