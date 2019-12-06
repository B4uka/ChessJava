package com.example.demo.model.pieces;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

public class Pawn extends Piece {

    public Pawn(Position position, Color color){
        super(position, color);
    }


    @Override
    public PossibleActions generatePossibleActions(Board board) {

        PossibleActions possibleActions = new PossibleActions();
        if(this.color == Color.BLACK) {
            Position pawnMove1 = this.position.getNewPositionByVector(1,0);
            Position pawnMove2 = this.position.getNewPositionByVector(2,0);
            if(!board.isOccupied(pawnMove1)) {
                possibleActions.addPossibleMove(pawnMove1);
                if (!board.isOccupied(pawnMove2) && this.position.getRow() == 1)
                    possibleActions.addPossibleMove(pawnMove2);
            }
            Position pawnCapture1 = this.position.getNewPositionByVector(1,1);
            Position pawnCapture2 = this.position.getNewPositionByVector(1,-1);
            if(pawnCapture1.isOnBoard() && board.isOccupiedByColor(pawnCapture1, Color.WHITE)) {
                possibleActions.addPossibleCapture(pawnCapture1);
            }
            if(pawnCapture2.isOnBoard() && board.isOccupiedByColor(pawnCapture2, Color.WHITE)) {
                possibleActions.addPossibleCapture(pawnCapture2);
            }
        }

        if(this.color == Color.WHITE) {
            Position pawnMove1 = this.position.getNewPositionByVector(-1,0);
            Position pawnMove2 = this.position.getNewPositionByVector(-2,0);
            if(!board.isOccupied(pawnMove1)) {
                possibleActions.addPossibleMove(pawnMove1);
                if (!board.isOccupied(pawnMove2) && this.position.getRow() == 6)
                    possibleActions.addPossibleMove(pawnMove2);
            }
            Position pawnCapture1 = this.position.getNewPositionByVector(-1,1);
            Position pawnCapture2 = this.position.getNewPositionByVector(-1,-1);
            if(pawnCapture1.isOnBoard() && board.isOccupiedByColor(pawnCapture1, Color.BLACK)) {
                possibleActions.addPossibleCapture(pawnCapture1);
            }
            if(pawnCapture2.isOnBoard() && board.isOccupiedByColor(pawnCapture2, Color.BLACK)) {
                possibleActions.addPossibleCapture(pawnCapture2);
            }
        }
        return possibleActions;
    }
}
