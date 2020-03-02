package pl.wb.demo.chess.model.board.AllPossibleActions;

import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Position;

public class WhiteOrBlackAllActions {
    protected Board board;
    protected PossibleActions possibleMovesOrCaptures, possibleActions;
    protected ChessGame game;


    public WhiteOrBlackAllActions(Board board, PossibleActions possibleMovesOrCaptures, PossibleActions possibleActions, ChessGame game){
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
            possibleActions = game.selectWhitePiece(test.getRow(), test.getColumn());
            for (Position position : possibleActions.getPossibleMoves()) {
                if (game.isCheckAfterTheMove(position)) {
                    continue;
                } else {
                    possibleMovesOrCaptures.addPossibleMove(position);
                }
                // System.out.println("possible moves: " + possibleMovesOrCaptures.getPosition().getRow() + possibleMovesOrCaptures.getPosition().getColumn());
            }
            for (Position position : possibleActions.getPossibleCaptures()) {
                if (game.isCheckAfterTheCapture(position)) {
                    continue;
                } else {
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
            possibleActions = game.selectBlackPiece(test.getRow(), test.getColumn());
            for (Position position : possibleActions.getPossibleMoves()) {
                if (game.isCheckAfterTheMove(position)) {
                    continue;
                } else {
                    possibleMovesOrCaptures.addPossibleMove(position);
                }
                // System.out.println("possible moves: " + possibleMovesOrCaptures.getPosition().getRow() + possibleMovesOrCaptures.getPosition().getColumn());
            }
            for (Position position : possibleActions.getPossibleCaptures()) {
                if (game.isCheckAfterTheCapture(position)) {
                    continue;
                } else {
                    possibleMovesOrCaptures.addPossibleCapture(position);
                    //  System.out.println("possible moves: " + possibleMovesOrCaptures.getPosition().getRow() + possibleMovesOrCaptures.getPosition().getColumn());
                }
            }
        }

        return possibleMovesOrCaptures;
    }
}
