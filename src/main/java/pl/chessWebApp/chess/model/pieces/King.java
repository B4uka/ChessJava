package pl.chessWebApp.chess.model.pieces;

import pl.chessWebApp.chess.model.board.Board;
import pl.chessWebApp.chess.model.board.PossibleActions;
import pl.chessWebApp.chess.model.piece_properties.Color;
import pl.chessWebApp.chess.model.piece_properties.Position;
import pl.chessWebApp.chess.model.pieces.MoveGenerator.CastlingKingMovesValidation;
import pl.chessWebApp.chess.model.pieces.MoveGenerator.StandardMoveGenerator;

public class King extends Piece implements StandardMoveGenerator {

    public King (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    public King (Position position, Color color, String code) {
        super(position, color, code);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        moveGenerator(this.position, board, possibleActions,0, 1);
        moveGenerator(this.position, board, possibleActions,0, -1);
        moveGenerator(this.position, board, possibleActions,1, 0);
        moveGenerator(this.position, board, possibleActions,-1, 0);
        moveGenerator(this.position, board, possibleActions,1, 1);
        moveGenerator(this.position, board, possibleActions,1, -1);
        moveGenerator(this.position, board, possibleActions,-1, 1);
        moveGenerator(this.position, board, possibleActions,-1, -1);

        CastlingKingMovesValidation possibleKingCastleMoves = new CastlingKingMovesValidation(possibleActions, this.color, board, this.position);
        possibleKingCastleMoves.kingMovesForCastling();

        return possibleActions;
    }

    @Override
    public PossibleActions moveGenerator (Position kingPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {
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