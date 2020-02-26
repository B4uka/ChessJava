package pl.wb.demo.chess.model.pieces;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.ValidationForMovesChecksCaptures.CastlingKingMovesValidation;
import pl.wb.demo.chess.model.pieces.ValidationForMovesChecksCaptures.MoveValidation;

public class King extends Piece implements MoveValidation {

    public King (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        moveValidation(this.position, board, possibleActions,0, 1);
        moveValidation(this.position, board, possibleActions,0, -1);
        moveValidation(this.position, board, possibleActions,1, 0);
        moveValidation(this.position, board, possibleActions,-1, 0);
        moveValidation(this.position, board, possibleActions,1, 1);
        moveValidation(this.position, board, possibleActions,1, -1);
        moveValidation(this.position, board, possibleActions,-1, 1);
        moveValidation(this.position, board, possibleActions,-1, -1);

        CastlingKingMovesValidation possibleKingCastleMoves = new CastlingKingMovesValidation(possibleActions, this.color, board, this.position);
        possibleKingCastleMoves.kingMovesForCastling();

        return possibleActions;
    }

    @Override
    public PossibleActions moveValidation (Position kingPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {
        //potential position knowing row and columns shifts
        Position kingPossibleMovePosition = kingPosition.getNewPositionByVector(rowShift, columnShift);

        if (kingPossibleMovePosition.isOnBoard()) {
            if (this.color == Color.WHITE && !board.isOccupied(kingPossibleMovePosition)) {//&& ChessGame.isWhiteKingChecked() ) {
                possibleActions.addPossibleMove(kingPossibleMovePosition);
            } else if (this.color == Color.WHITE && !board.isOccupiedByColor(kingPossibleMovePosition, Color.WHITE) && board.isOccupiedByColor((kingPossibleMovePosition), Color.BLACK)) {
                possibleActions.addPossibleCapture(kingPossibleMovePosition);
            } else if (this.color == Color.BLACK && !board.isOccupied(kingPossibleMovePosition)) {
                possibleActions.addPossibleMove(kingPossibleMovePosition);
            } else if (this.color == Color.BLACK && !board.isOccupiedByColor(kingPossibleMovePosition, Color.BLACK) && board.isOccupiedByColor((kingPossibleMovePosition), Color.WHITE)) {
                possibleActions.addPossibleCapture(kingPossibleMovePosition);
            }
        }
        return possibleActions;
    }
}