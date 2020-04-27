package pl.wb.demo.chess.model.pieces.Mate;

import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.board.allPossibleActions.WhiteOrBlackAllActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.pieces.check.CheckValidation;
import pl.wb.demo.chess.model.pieces.check.IsCheck;

public class IsMate implements MateValidation {
    private Board board;
    private PossibleActions possibleActions, possibleMovesOrCaptures;
    protected ChessGame chessGame;
    protected boolean isStalemate;
    protected Color color;

    public IsMate (Board board, PossibleActions possibleMovesOrCaptures, PossibleActions possibleActions, ChessGame chessGame) {
        this.board = board;
        this.possibleMovesOrCaptures = possibleMovesOrCaptures;
        this.possibleActions = possibleActions;
        this.chessGame = chessGame;
    }

    @Override
    public boolean isKingMated () {
        CheckValidation test = new IsCheck(board);
        Color color = chessGame.getWhoIsUpToMove();

        WhiteOrBlackAllActions allPossibleActionsByWhiteOrBlack = new WhiteOrBlackAllActions(board, possibleMovesOrCaptures, possibleActions, chessGame);

        if (color == Color.WHITE) {
            possibleMovesOrCaptures = allPossibleActionsByWhiteOrBlack.allWhitePiecesPossibleActions();
        } else if (color == Color.BLACK) {
            possibleMovesOrCaptures = allPossibleActionsByWhiteOrBlack.allBlackPiecesPossibleActions();
        }

        if (test.isKingChecked(color)) {
            return possibleMovesOrCaptures.getAllPossibleMovesAndCaptures().isEmpty();
        } else if (!test.isKingChecked(color) && possibleMovesOrCaptures.getAllPossibleMovesAndCaptures().size() == 0) {
            this.isStalemate = true;
            return false;
        }
        return false;
    }
}
