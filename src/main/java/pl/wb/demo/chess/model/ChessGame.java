package pl.wb.demo.chess.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.wb.demo.chess.communication.field.Field;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.board.SelectionAndMove.MoveOnTheChessBoard;
import pl.wb.demo.chess.model.board.SelectionAndMove.Selection;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.Mate.IsMate;
import pl.wb.demo.chess.model.pieces.Mate.MateValidation;

import java.util.EmptyStackException;

@Slf4j
@Getter
@Setter
public class ChessGame {

    private Board board;
    private PossibleActions possibleActions, possibleMovesOrCaptures;
    private Color whoIsUpToMove;
    public static boolean blackCastled, whiteCastled, isStalemate, isMate;
    private Selection playersSelection;
    private MoveOnTheChessBoard searchForCheckOrMate;

    public ChessGame() {
        this.board = new Board();
        blackCastled = false;
        whiteCastled = false;
        isMate = false;
        isStalemate = false;
        whoIsUpToMove = Color.WHITE;
    }

    // Mate is returned. Inside method from class IsMate is also validation for stalemate -> for know class Mate in controller catches this event
    public boolean isKingMated() {
        MateValidation test = new IsMate(board, possibleMovesOrCaptures, possibleActions, ChessGame.this);
        return isMate = test.isKingMated();
    }

    public boolean selectPiece(int row, int column, Color color) {
        if (whoIsUpToMove != color || isStalemate || isMate) {
            return false;
        }
        this.playersSelection = new Selection(ChessGame.this, board);
        this.possibleActions = playersSelection.selectedPiecePossibleActions(row, column, color);

        return true;
    }

    public void selectPieceForCheckSearching(int row, int column, Color color) {
        this.playersSelection = new Selection(ChessGame.this, board);
        this.possibleActions = playersSelection.selectedPiecePossibleActions(row, column, color);
        //needed for searching for check and mate
        this.searchForCheckOrMate = new MoveOnTheChessBoard(playersSelection, board, possibleActions);
    }

    public boolean movePieceAndSwitchColor(int row, int column) {
        MoveOnTheChessBoard move = new MoveOnTheChessBoard(playersSelection, board, possibleActions);

         if (isStalemate || isMate || whoIsUpToMove != playersSelection.getCurrentlySelected().getColor()) {
            return false;
        } else {
            Position positionWhereWeWantToMove = new Position(row, column);

            if (board.isOccupied(positionWhereWeWantToMove)) {
                if (!move.newPiecePositionByCapture(positionWhereWeWantToMove)) {
                    log.warn("You cant capture piece on " + Field.getFieldByPosition(row, column));
                    return false;
                }
            } else if (!move.newPiecePositionByMove(positionWhereWeWantToMove)) {
                log.warn("You cant move to " + Field.getFieldByPosition(row, column));
                return false;
            }
            switchColor();
        }
        return true;
    }

    private void switchColor() {
        // changing between players
        if (this.whoIsUpToMove == Color.WHITE) {
            this.whoIsUpToMove = whoIsUpToMove.blackToMove();
        } else {
            this.whoIsUpToMove = whoIsUpToMove.whiteToMove();
        }
    }

    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        game.board.printBoard();
    }
}