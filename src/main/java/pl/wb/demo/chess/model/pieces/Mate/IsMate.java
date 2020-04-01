package pl.wb.demo.chess.model.pieces.Mate;

import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.board.allPossibleActions.WhiteOrBlackAllActions;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;

import static pl.wb.demo.chess.model.ChessGame.isBlackKingChecked;
import static pl.wb.demo.chess.model.ChessGame.isWhiteKingChecked;

public class IsMate implements MateValidation {
    private Board board;
    private PossibleActions possibleActions, possibleMovesOrCaptures;
    protected ChessGame chessGame;
    protected boolean isStalemate;
    protected Color color;

    public IsMate(Board board, Color color, PossibleActions possibleMovesOrCaptures, PossibleActions possibleActions, ChessGame chessGame){
        this.board = board;
        this.color = color;
        this.possibleMovesOrCaptures = possibleMovesOrCaptures;
        this.possibleActions = possibleActions;
        this.chessGame = chessGame;
    }

    @Override
    public boolean isKingMated (Color color) {

        WhiteOrBlackAllActions allPossibleActionsByWhiteOrBlack = new WhiteOrBlackAllActions(board, possibleMovesOrCaptures, possibleActions, chessGame);

        if (color == Color.WHITE) {
            possibleMovesOrCaptures = allPossibleActionsByWhiteOrBlack.allWhitePiecesPossibleActions();
            if (isWhiteKingChecked()) {
                return possibleMovesOrCaptures.getAllPossibleMovesAndCaptures().isEmpty();
            } else if (!isWhiteKingChecked() && possibleMovesOrCaptures.getAllPossibleMovesAndCaptures().size() == 0) {
                isStalemate = true;
                return false;
            }
        } else if (color == Color.BLACK) {
            possibleMovesOrCaptures = allPossibleActionsByWhiteOrBlack.allBlackPiecesPossibleActions();
            if (isBlackKingChecked()) {
                return possibleMovesOrCaptures.getAllPossibleMovesAndCaptures().isEmpty();
            } else if (!isBlackKingChecked() && possibleMovesOrCaptures.getAllPossibleMovesAndCaptures().size() == 0) {
                isStalemate = true;
                return false;
            }
        }
        return false;
    }
}
