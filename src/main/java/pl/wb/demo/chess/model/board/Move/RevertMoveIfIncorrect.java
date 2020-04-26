package pl.wb.demo.chess.model.board.Move;

import lombok.Getter;
import lombok.Setter;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.Piece;

@Getter
@Setter
public class RevertMoveIfIncorrect {
    private Position piecePositionOLD, piecePositionNEW;
    private Board board;
    private Piece currentlySelected, temporaryBeaten;

    public RevertMoveIfIncorrect(Board board, Position piecePositionOLD, Position piecePositionNEW, Piece currentlySelected, Piece temporaryBeaten){
        this.board = board;
        this.piecePositionNEW = piecePositionNEW;
        this.piecePositionOLD = piecePositionOLD;
        this.currentlySelected = currentlySelected;
        this.temporaryBeaten = temporaryBeaten;
    }

    public void revertNewMove (Position piecePositionOLD) {
        this.piecePositionOLD = piecePositionOLD;

        this.piecePositionNEW = piecePositionOLD;
        board.setEmptyByPosition(this.currentlySelected.getPosition());
        currentlySelected.setPosition(piecePositionNEW);
        board.setPiece(currentlySelected);
    }

    public void revertNewMoveAndRestoreTempBeaten (Position piecePositionOLD) {
        revertNewMove(piecePositionOLD);
        board.setPiece(temporaryBeaten);
    }
}
