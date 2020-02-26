package pl.wb.demo.chess.model.pieces.ValidationForMovesChecksCaptures;

import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.Rook;

public class CastlingKingMovesValidation {
    public Color color;
    public Board board;
    public PossibleActions possibleActions;
    public Position position;

    public CastlingKingMovesValidation(PossibleActions possibleActions, Color color, Board board, Position position){
        this.possibleActions = possibleActions;
        this.color = color;
        this.board = board;
        this.position = position;
    }

    public void kingMovesForCastling() {
        if (this.color == Color.BLACK && !ChessGame.blackCastled && board.getBlackKingPiece().getCountMoves() == 0) {
            if (!board.isBoardOccupiedByAnyPiece(0, 6) && board.getBlackPiece(0, 7).getClass() == Rook.class) {
                Position castlingKingMove1 = this.position.setNewPosition(0, 6);
                if (!ChessGame.isBlackKingChecked() && !board.isBoardOccupiedByAnyPiece(0, 5) && board.getPiece(0, 7).getCountMoves() == 0) {
                    possibleActions.addPossibleCastlingKingMove(castlingKingMove1);//King move
                }
            }
            if (!board.isBoardOccupiedByAnyPiece(0, 3) & !board.isBoardOccupiedByAnyPiece(0, 2) && !board.isBoardOccupiedByAnyPiece(0, 1) && board.getBlackPiece(0, 0).getClass() == Rook.class) {
                Position castlingKingMove2 = this.position.setNewPosition(0, 2);
                if (!ChessGame.isBlackKingChecked() && !board.isBoardOccupiedByAnyPiece(0, 3) && board.getPiece(0, 0).getCountMoves() == 0) {
                    possibleActions.addPossibleCastlingKingMove(castlingKingMove2);//King move
                }
            }
        } else if (this.color == Color.WHITE && !ChessGame.whiteCastled && board.getWhiteKingPiece().getCountMoves() == 0) {
            if (!board.isBoardOccupiedByAnyPiece(7, 6) && !board.isBoardOccupiedByAnyPiece(7, 5)) {
                Position castlingKingMove1 = this.position.setNewPosition(7, 6);
                if (!ChessGame.isWhiteKingChecked() && board.getPiece(7, 7).getCountMoves() == 0
                        && board.getWhitePiece(7, 7).getClass() == Rook.class) {
                    possibleActions.addPossibleCastlingKingMove(castlingKingMove1);//King move
                }
            }
            if (!board.isBoardOccupiedByAnyPiece(7, 3) && !board.isBoardOccupiedByAnyPiece(7, 2) && !board.isBoardOccupiedByAnyPiece(7, 1) && board.getWhitePiece(7, 0).getClass() == Rook.class) {
                Position castlingKingMove2 = this.position.setNewPosition(7, 2);
                if (!ChessGame.isWhiteKingChecked() && !board.isBoardOccupiedByAnyPiece(7, 3) && board.getPiece(7, 0).getCountMoves() == 0) {
                    possibleActions.addPossibleCastlingKingMove(castlingKingMove2);//King move
                }
            }
        }
    }
}
