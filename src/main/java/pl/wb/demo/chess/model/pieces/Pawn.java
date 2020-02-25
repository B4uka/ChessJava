package pl.wb.demo.chess.model.pieces;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;

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
    public PossibleActions moveValidation (Position pawnMove, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {

        pawnMove = pawnMove.getNewPositionByVector(rowShift, columnShift);

        if (!board.isOccupied(pawnMove)) {
            possibleActions.addPossibleMove(pawnMove);
            if ((this.position.getRow() == 1 && pawnMove.getRow() == 2) || (this.position.getRow() == 6 && pawnMove.getRow() == 5)) {
                moveValidation(pawnMove, board, possibleActions, rowShift, columnShift);
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
