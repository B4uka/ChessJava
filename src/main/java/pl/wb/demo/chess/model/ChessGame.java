package pl.wb.demo.chess.model;

import lombok.Getter;
import lombok.Setter;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.Move.MoveOnTheChessBoard;
import pl.wb.demo.chess.model.board.Move.Selection;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.Mate.IsMate;
import pl.wb.demo.chess.model.pieces.Mate.MateValidation;

@Getter
@Setter
public class ChessGame {

    private Board board;
    private PossibleActions possibleActions, possibleMovesOrCaptures;
    private Position position;
    private Color whoIsUpToMove = Color.WHITE;
    public static boolean blackCastled, whiteCastled, isStalemate;
    private Selection playersSelection;
    private MoveOnTheChessBoard move;

    public ChessGame () {
        this.board = new Board();
        blackCastled = false;
        whiteCastled = false;
    }

    // Mate is returned. Inside method from class IsMate is also validation for stalemate -> for know class Mate in controller catches this event
    public boolean isKingMated (Color color) {
        MateValidation test = new IsMate(board, color, possibleMovesOrCaptures, possibleActions, ChessGame.this);
        return test.isKingMated(color);
    }

    public PossibleActions selectPiece (int row, int column, Color color) {
        this.playersSelection = new Selection(ChessGame.this, board);
        this.possibleActions = playersSelection.selectedPiecePossibleActions(row, column, color);
        this.move = new MoveOnTheChessBoard(playersSelection, board, possibleActions);
        return possibleActions;
    }

    public static void main (String[] args) {

        ChessGame game = new ChessGame();
        game.board.printBoard();

        game.selectPiece(1, 3, Color.BLACK);
        game.move.newPiecePositionByMove(new Position(3, 3));
        System.out.println();

        game.selectPiece(6, 0, Color.WHITE);
        game.move.newPiecePositionByMove(new Position(4, 0));

        System.out.println();
        game.selectPiece(1, 4, Color.BLACK);
        if (game.move.newPiecePositionByMove(new Position(3, 4))) {
            System.out.println(game.isKingMated(Color.WHITE));
        }

        System.out.println();
        game.selectPiece(6, 6, Color.WHITE);
        if (game.move.newPiecePositionByMove(new Position(4, 6))) {
//            System.out.println(game.isKingMated(Color.BLACK));
        }
        System.out.println();
        game.selectPiece(6, 5, Color.WHITE);
        if (game.move.newPiecePositionByMove(new Position(4, 5))) {
//            System.out.println(game.isKingMated(Color.BLACK));
        }

        game.selectPiece(3, 4, Color.BLACK);
        if (game.move.newPiecePositionByCapture(new Position(4, 5))) {
            System.out.println(game.isKingMated(Color.WHITE));
        }

        System.out.println();
        game.selectPiece(0, 3, Color.BLACK);
        game.move.newPiecePositionByMove(new Position(4, 7));
        System.out.println(game.isKingMated(Color.WHITE));

        System.out.println();
        game.selectPiece(1, 1, Color.BLACK);
        if (game.move.newPiecePositionByMove(new Position(3, 1))) {
            System.out.println(game.isKingMated(Color.WHITE));
        }

        //tests for reverting move with deep copy
        System.out.println();
        game.selectPiece(0, 1, Color.BLACK);
        if (game.move.newPiecePositionByMove(new Position(4, 4))) {
            System.out.println(game.isKingMated(Color.WHITE));
        }

        System.out.println();
        game.selectPiece(7, 1, Color.WHITE);
        if (game.move.newPiecePositionByMove(new Position(5, 1))) {
            System.out.println(game.isKingMated(Color.BLACK));
        }
        //test for reverting moves after checks
        System.out.println();
        game.selectPiece(6, 4, Color.WHITE);
        if (game.move.newPiecePositionByMove(new Position(4, 4))) {
            System.out.println(game.isKingMated(Color.BLACK));
        }

        game.selectPiece(7, 4, Color.WHITE);
        if (game.move.newPiecePositionByMove(new Position(6, 5))) {
            System.out.println(game.isKingMated(Color.BLACK));
        }


        game.selectPiece(6, 4, Color.WHITE);
        game.move.newPiecePositionByMove(new Position(4, 4));
        game.board.printBoard();
        System.out.println(game.isKingMated(Color.BLACK));
        System.out.println(game.isKingMated(Color.WHITE));
    }
}
