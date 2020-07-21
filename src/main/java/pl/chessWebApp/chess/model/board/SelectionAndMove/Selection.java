package pl.chessWebApp.chess.model.board.SelectionAndMove;

import lombok.Getter;
import lombok.Setter;
import pl.chessWebApp.chess.model.ChessGame;
import pl.chessWebApp.chess.model.piece_properties.Color;
import pl.chessWebApp.chess.model.board.Board;
import pl.chessWebApp.chess.model.board.PossibleActions;
import pl.chessWebApp.chess.model.pieces.Piece;

@Getter
@Setter
public class Selection {
    private ChessGame game;
    private Board board;
    private PossibleActions possibleActions = new PossibleActions();
    private Piece currentlySelected;

    public Selection (ChessGame game, Board board) {
        this.game = game;
        this.board = board;
    }

    public PossibleActions selectedPiecePossibleActions (int row, int column, Color color) {
        tryToGetSelectedPiece(row, column, color);
        return possibleActions = generateActionsForCurrentlySelected();
    }

    private PossibleActions generateActionsForCurrentlySelected () {
        return currentlySelected.generatePossibleActions(board);
    }

    private void tryToGetSelectedPiece (int row, int column, Color color) {
        try {
            this.currentlySelected = board.getPieceByColor(row, column, color);
            generateActionsForCurrentlySelected();
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }
}
