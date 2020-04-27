package pl.wb.demo.chess.model.board.SelectionAndMove;

import lombok.Getter;
import lombok.Setter;
import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.pieces.Piece;

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
            throw new IllegalStateException("There is no piece on this cell!", e);
        }
    }
}
