package pl.chessWebApp.chess.model.pieces.check;

import pl.chessWebApp.chess.model.board.Board;
import pl.chessWebApp.chess.model.board.PossibleActions;
import pl.chessWebApp.chess.model.piece_properties.Color;
import pl.chessWebApp.chess.model.piece_properties.Position;
import pl.chessWebApp.chess.model.pieces.*;

public class PieceCheckingKing extends PossibleActions implements PositionsOfPiecesThatCouldCheckKings {
    protected Position position;

    public PieceCheckingKing (){
        super();
    }

    @Override
    public PossibleActions piecesPositionsCheckingWhiteKing (Board board, Position position) {
        this.position = position;
        PieceCheckingKing possibleActions = new PieceCheckingKing();

        kingCheckingKing(this.position, board, possibleActions, Color.BLACK);

        knightCheckingKing(this.position, board, possibleActions, Color.BLACK);

        queenBishopCheckingKing(this.position, board, possibleActions, 1, 1, Color.BLACK);
        queenBishopCheckingKing(this.position, board, possibleActions, 1, -1, Color.BLACK);
        queenBishopCheckingKing(this.position, board, possibleActions, -1, 1, Color.BLACK);
        queenBishopCheckingKing(this.position, board, possibleActions, -1, -1, Color.BLACK);

        queenRookCheckingKing(this.position, board, possibleActions, 0, 1, Color.BLACK);
        queenRookCheckingKing(this.position, board, possibleActions, 0, -1, Color.BLACK);
        queenRookCheckingKing(this.position, board, possibleActions, 1, 0, Color.BLACK);
        queenRookCheckingKing(this.position, board, possibleActions, -1, 0, Color.BLACK);

        pawnCheckingKing(this.position, board, possibleActions, -1, 1, Color.BLACK);
        pawnCheckingKing(this.position, board, possibleActions, -1, -1, Color.BLACK);

        return possibleActions;
    }

    @Override
    public PossibleActions piecesPositionsCheckingBlackKing (Board board, Position position) {
        this.position = position;
        PieceCheckingKing possibleActions = new PieceCheckingKing();

        kingCheckingKing(this.position, board, possibleActions, Color.WHITE);

        knightCheckingKing(this.position, board, possibleActions, Color.WHITE);

        queenBishopCheckingKing(this.position, board, possibleActions, 1, 1, Color.WHITE);
        queenBishopCheckingKing(this.position, board, possibleActions, 1, -1, Color.WHITE);
        queenBishopCheckingKing(this.position, board, possibleActions, -1, 1, Color.WHITE);
        queenBishopCheckingKing(this.position, board, possibleActions, -1, -1, Color.WHITE);

        queenRookCheckingKing(this.position, board, possibleActions, 0, 1, Color.WHITE);
        queenRookCheckingKing(this.position, board, possibleActions, 0, -1, Color.WHITE);
        queenRookCheckingKing(this.position, board, possibleActions, 1, 0, Color.WHITE);
        queenRookCheckingKing(this.position, board, possibleActions, -1, 0, Color.WHITE);

        pawnCheckingKing(this.position, board, possibleActions, 1, 1, Color.WHITE);
        pawnCheckingKing(this.position, board, possibleActions, 1, -1, Color.WHITE);

        return possibleActions;
    }

    @Override
    public void kingCheckingKing (Position kingPosition, Board board, PieceCheckingKing possibleActions, Color opponentColor) {
        Position[] checkedByKing = new Position[8];

        checkedByKing[0] = this.position.getNewPositionByVector(0, 1);
        checkedByKing[1] = this.position.getNewPositionByVector(0, -1);
        checkedByKing[2] = this.position.getNewPositionByVector(1, 0);
        checkedByKing[3] = this.position.getNewPositionByVector(-1, 0);
        checkedByKing[4] = this.position.getNewPositionByVector(1, 1);
        checkedByKing[5] = this.position.getNewPositionByVector(1, -1);
        checkedByKing[6] = this.position.getNewPositionByVector(-1, 1);
        checkedByKing[7] = this.position.getNewPositionByVector(-1, -1);
        for (Position kingPossibleMovePosition : checkedByKing) {
            if (kingPossibleMovePosition.isOnBoard()) {
                if (board.isOccupiedBySpecificPiece(kingPossibleMovePosition, opponentColor, King.class)) {
                    possibleActions.addPiecesPositionsWhichAreCheckingTheKing(kingPossibleMovePosition);
                }
            }
        }
    }

    @Override
    public void knightCheckingKing (Position kingPosition, Board board, PieceCheckingKing possibleActions, Color opponentColor) {
        Position[] checkedByKnight = new Position[8];

        checkedByKnight[0] = this.position.getNewPositionByVector(1, 2);
        checkedByKnight[1] = this.position.getNewPositionByVector(1, -2);
        checkedByKnight[2] = this.position.getNewPositionByVector(2, 1);
        checkedByKnight[3] = this.position.getNewPositionByVector(2, -1);
        checkedByKnight[4] = this.position.getNewPositionByVector(-1, 2);
        checkedByKnight[5] = this.position.getNewPositionByVector(-1, -2);
        checkedByKnight[6] = this.position.getNewPositionByVector(-2, 1);
        checkedByKnight[7] = this.position.getNewPositionByVector(-2, -1);
        for (Position knightPossibleMovePosition : checkedByKnight) {
            if (knightPossibleMovePosition.isOnBoard()) {
                if (board.isOccupiedBySpecificPiece(knightPossibleMovePosition, opponentColor, Knight.class)) {
                    possibleActions.addPiecesPositionsWhichAreCheckingTheKing(knightPossibleMovePosition);
                }
            }
        }
    }

    @Override
    public void pawnCheckingKing (Position kingPosition, Board board, PieceCheckingKing possibleActions, int rowShift, int columnShift, Color opponentsColor) {
        Position potentialPawnPositionCheckingKing = kingPosition.getNewPositionByVector(rowShift, columnShift);

        if (potentialPawnPositionCheckingKing.isOnBoard()) {
            if (board.isOccupiedBySpecificPiece(potentialPawnPositionCheckingKing, opponentsColor, Pawn.class)) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(potentialPawnPositionCheckingKing);
            }
        }
    }

    @Override
    public void queenRookCheckingKing (Position kingPosition, Board board, PieceCheckingKing possibleActions, int rowShift, int columnShift, Color opponentsColor) {
        Position potentialQueenRookPositionCheckingKing = kingPosition.getNewPositionByVector(rowShift, columnShift);

        while (potentialQueenRookPositionCheckingKing.isOnBoard()) {
            if (!board.isOccupied(potentialQueenRookPositionCheckingKing)) {
                potentialQueenRookPositionCheckingKing = potentialQueenRookPositionCheckingKing.getNewPositionByVector(rowShift, columnShift);
            } else if (board.isOccupied(potentialQueenRookPositionCheckingKing) &&
                    (board.isOccupiedBySpecificPiece(potentialQueenRookPositionCheckingKing, opponentsColor, Queen.class)
                            || board.isOccupiedBySpecificPiece(potentialQueenRookPositionCheckingKing, opponentsColor, Rook.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(potentialQueenRookPositionCheckingKing);
                break;
            } else {
                break;
            }
        }
    }

    @Override
    public void queenBishopCheckingKing (Position kingPosition, Board board, PieceCheckingKing possibleActions, int rowShift, int columnShift, Color opponentsColor) {
        Position potentialQueenBishopPositionCheckingKing = kingPosition.getNewPositionByVector(rowShift, columnShift);

        while (potentialQueenBishopPositionCheckingKing.isOnBoard()) {
            if (!board.isOccupied(potentialQueenBishopPositionCheckingKing)) {
                potentialQueenBishopPositionCheckingKing = potentialQueenBishopPositionCheckingKing.getNewPositionByVector(rowShift, columnShift);
            } else if (board.isOccupied(potentialQueenBishopPositionCheckingKing) &&
                    (board.isOccupiedBySpecificPiece(potentialQueenBishopPositionCheckingKing, opponentsColor, Queen.class)
                            || board.isOccupiedBySpecificPiece(potentialQueenBishopPositionCheckingKing, opponentsColor, Bishop.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(potentialQueenBishopPositionCheckingKing);
                break;
            } else {
                break;
            }
        }
    }
}
