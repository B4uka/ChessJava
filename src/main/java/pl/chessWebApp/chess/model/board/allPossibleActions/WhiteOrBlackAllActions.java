package pl.chessWebApp.chess.model.board.allPossibleActions;

import pl.chessWebApp.chess.model.ChessGame;
import pl.chessWebApp.chess.model.board.Board;
import pl.chessWebApp.chess.model.board.PossibleActions;
import pl.chessWebApp.chess.model.piece_properties.Color;
import pl.chessWebApp.chess.model.piece_properties.Position;

public class WhiteOrBlackAllActions {
    protected Board board;
    protected PossibleActions possibleMovesOrCaptures, possibleActions;
    protected ChessGame game;

    public WhiteOrBlackAllActions(Board board, PossibleActions possibleMovesOrCaptures, PossibleActions possibleActions, ChessGame game) {
        this.board = board;
        this.possibleMovesOrCaptures = possibleMovesOrCaptures;
        this.possibleActions = possibleActions;
        this.game = game;
    }

    public PossibleActions allWhitePiecesPossibleActions() {
        board.getAllWhitePiecesPosition();
        possibleMovesOrCaptures = new PossibleActions();

        for (Position test : board.whitePiecesPositions) {
            game.selectPieceForCheckSearching(test.getRow(), test.getColumn(), Color.WHITE);
            possibleActions = game.getPossibleActions();
            for (Position position : possibleActions.getPossibleMoves()) {
                if (!game.getSearchForCheckOrMate().isCheckAfterTheMove(position)) {
                    possibleMovesOrCaptures.addPossibleMove(position);
                }
            }
            for (Position position : possibleActions.getPossibleCaptures()) {
                if (!game.getSearchForCheckOrMate().isCheckAfterTheCapture(position)) {
                    possibleMovesOrCaptures.addPossibleCapture(position);
                }
            }
        }
        return possibleMovesOrCaptures;
    }

    public PossibleActions allBlackPiecesPossibleActions() {
        board.getAllBlackPiecesPosition();
        possibleMovesOrCaptures = new PossibleActions();
        for (Position test : board.blackPiecesPositions) {
            game.selectPieceForCheckSearching(test.getRow(), test.getColumn(), Color.BLACK);
            possibleActions = game.getPossibleActions();

            for (Position position : possibleActions.getPossibleMoves()) {
                if (!game.getSearchForCheckOrMate().isCheckAfterTheMove(position)) {
                    possibleMovesOrCaptures.addPossibleMove(position);
                }
            }
            for (Position position : possibleActions.getPossibleCaptures()) {
                if (!game.getSearchForCheckOrMate().isCheckAfterTheCapture(position)) {
                    possibleMovesOrCaptures.addPossibleCapture(position);
                }
            }
        }
        return possibleMovesOrCaptures;
    }
}
