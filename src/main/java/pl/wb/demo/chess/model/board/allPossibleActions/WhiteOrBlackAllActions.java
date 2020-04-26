package pl.wb.demo.chess.model.board.allPossibleActions;

import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;

public class WhiteOrBlackAllActions {
    protected Board board;
    protected PossibleActions possibleMovesOrCaptures, possibleActions;
    protected ChessGame game;

    public WhiteOrBlackAllActions (Board board, PossibleActions possibleMovesOrCaptures, PossibleActions possibleActions, ChessGame game) {
        this.board = board;
        this.possibleMovesOrCaptures = possibleMovesOrCaptures;
        this.possibleActions = possibleActions;
        this.game = game;
    }

    // all actions that white player can do
    public PossibleActions allWhitePiecesPossibleActions () {

        board.getAllWhitePiecesPosition();
        possibleMovesOrCaptures = new PossibleActions();

        for (Position test : board.whitePiecesPositions) {
            possibleActions = game.selectPiece(test.getRow(), test.getColumn(), Color.WHITE);
            for (Position position : possibleActions.getPossibleMoves()) {
                if (!game.getMove().isCheckAfterTheMove(position)) {
                    possibleMovesOrCaptures.addPossibleMove(position);
                }
                // System.out.println("possible moves: " + possibleMovesOrCaptures.getPosition().getRow() + possibleMovesOrCaptures.getPosition().getColumn());
            }
            for (Position position : possibleActions.getPossibleCaptures()) {
                if (!game.getMove().isCheckAfterTheCapture(position)) {
                    possibleMovesOrCaptures.addPossibleCapture(position);
                    // System.out.println("possible moves: " + possibleMovesOrCaptures.getPosition().getRow() + possibleMovesOrCaptures.getPosition().getColumn());
                }
            }
        }
        return possibleMovesOrCaptures;
    }

    //all actions that black player can do
    public PossibleActions allBlackPiecesPossibleActions () {
        board.getAllBlackPiecesPosition();
        possibleMovesOrCaptures = new PossibleActions();

        for (Position test : board.blackPiecesPositions) {
            possibleActions = game.selectPiece(test.getRow(), test.getColumn(), Color.BLACK);
            for (Position position : possibleActions.getPossibleMoves()) {
                if (!game.getMove().isCheckAfterTheMove(position)) {
                    possibleMovesOrCaptures.addPossibleMove(position);
                }
                // System.out.println("possible moves: " + possibleMovesOrCaptures.getPosition().getRow() + possibleMovesOrCaptures.getPosition().getColumn());
            }
            for (Position position : possibleActions.getPossibleCaptures()) {
                if (!game.getMove().isCheckAfterTheCapture(position)) {
                    possibleMovesOrCaptures.addPossibleCapture(position);
                    //  System.out.println("possible moves: " + possibleMovesOrCaptures.getPosition().getRow() + possibleMovesOrCaptures.getPosition().getColumn());
                }
            }
        }

        return possibleMovesOrCaptures;
    }
}
