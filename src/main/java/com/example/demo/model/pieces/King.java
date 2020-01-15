package com.example.demo.model.pieces;

import com.example.demo.model.ChessGame;
import com.example.demo.model.board.Board;
import com.example.demo.model.board.PossibleActions;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;

public class King extends Piece {

    public King (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        Position[] kingMove = new Position[8];

        kingMove[0] = this.position.getNewPositionByVector(0, 1);
        kingMove[1] = this.position.getNewPositionByVector(0, -1);
        kingMove[2] = this.position.getNewPositionByVector(1, 0);
        kingMove[3] = this.position.getNewPositionByVector(-1, 0);
        kingMove[4] = this.position.getNewPositionByVector(1, 1);
        kingMove[5] = this.position.getNewPositionByVector(1, -1);
        kingMove[6] = this.position.getNewPositionByVector(-1, 1);
        kingMove[7] = this.position.getNewPositionByVector(-1, -1);

        for (Position test : kingMove) {
            if (test.isOnBoard()) {
                if (this.color == Color.WHITE && !board.isOccupied(test)) {//&& ChessGame.isWhiteKingChecked() ) {
                    possibleActions.addPossibleMove(test);
                } else if (this.color == Color.WHITE && !board.isOccupiedByColor(test, Color.WHITE) && board.isOccupiedByColor((test), Color.BLACK)) {
                    possibleActions.addPossibleCapture(test);
                } else if (this.color == Color.BLACK && !board.isOccupied(test)) {
                    possibleActions.addPossibleMove(test);
                } else if (this.color == Color.BLACK && !board.isOccupiedByColor(test, Color.BLACK) && board.isOccupiedByColor((test), Color.WHITE)) {
                    possibleActions.addPossibleCapture(test);
                }
            }
        }
        if (this.color == Color.BLACK && !ChessGame.blackCastled && board.getBlackKingPiece().getCountMoves() == 0) {
            Position[] castlingKingMove = new Position[2];
            castlingKingMove[0] = this.position.getNewPosition(0, 6);
            castlingKingMove[1] = this.position.getNewPosition(0, 2);
            if (!ChessGame.isBlackKingChecked() && !board.isBoardOccupiedByPiece(0, 5) && board.getPiece(0, 7).getCountMoves() == 0
                    && !board.isBoardOccupiedByPiece(0, 6) && board.getBlackPiece(0, 7).getClass() == Rook.class) {
                possibleActions.addPossibleCastlingKingMove(castlingKingMove[0]);//King move
            }
            if (!ChessGame.isBlackKingChecked() && !board.isBoardOccupiedByPiece(0, 3) && board.getPiece(0, 0).getCountMoves() == 0
                    && !board.isBoardOccupiedByPiece(0, 2) && !board.isBoardOccupiedByPiece(0, 1) && board.getBlackPiece(0, 0).getClass() == Rook.class) {
                possibleActions.addPossibleCastlingKingMove(castlingKingMove[1]);//King move
            }
        } else if (this.color == Color.WHITE && !ChessGame.whiteCastled  && board.getWhiteKingPiece().getCountMoves() == 0) {
            Position[] castlingKingMove = new Position[2];
            castlingKingMove[0] = this.position.getNewPosition(7, 6);
            castlingKingMove[1] = this.position.getNewPosition(7, 2);
            if (!ChessGame.isWhiteKingChecked() && !board.isBoardOccupiedByPiece(7, 5) && board.getPiece(7, 7).getCountMoves() == 0
                    && !board.isBoardOccupiedByPiece(7, 6) && board.getWhitePiece(7, 7).getClass() == Rook.class) {
                possibleActions.addPossibleCastlingKingMove(castlingKingMove[0]);//King move
            }
            if (!ChessGame.isWhiteKingChecked() && !board.isBoardOccupiedByPiece(7, 3) && board.getPiece(7, 0).getCountMoves() == 0
                    && !board.isBoardOccupiedByPiece(7, 2) && !board.isBoardOccupiedByPiece(7, 1) && board.getWhitePiece(7, 0).getClass() == Rook.class) {
                possibleActions.addPossibleCastlingKingMove(castlingKingMove[1]);//King move
            }
        }
        return possibleActions;
    }

    public PossibleActions piecesPositionsCheckingWhiteKing (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        Position[] checkedByKing = new Position[8];

        checkedByKing[0] = this.position.getNewPositionByVector(0, 1);
        checkedByKing[1] = this.position.getNewPositionByVector(0, -1);
        checkedByKing[2] = this.position.getNewPositionByVector(1, 0);
        checkedByKing[3] = this.position.getNewPositionByVector(-1, 0);
        checkedByKing[4] = this.position.getNewPositionByVector(1, 1);
        checkedByKing[5] = this.position.getNewPositionByVector(1, -1);
        checkedByKing[6] = this.position.getNewPositionByVector(-1, 1);
        checkedByKing[7] = this.position.getNewPositionByVector(-1, -1);
        for (Position test : checkedByKing) {
            if (test.isOnBoard()) {
                if (board.isOccupiedByColor(test, Color.BLACK) && board.isOccupiedByKing(test, Color.BLACK)) {
                    possibleActions.addPiecesPositionsWhichAreCheckingTheKing(test);
                }
            }
        }

        Position[] checkedByKnight = new Position[8];
        checkedByKnight[0] = this.position.getNewPositionByVector(1, 2);
        checkedByKnight[1] = this.position.getNewPositionByVector(1, -2);
        checkedByKnight[2] = this.position.getNewPositionByVector(2, 1);
        checkedByKnight[3] = this.position.getNewPositionByVector(2, -1);
        checkedByKnight[4] = this.position.getNewPositionByVector(-1, 2);
        checkedByKnight[5] = this.position.getNewPositionByVector(-1, -2);
        checkedByKnight[6] = this.position.getNewPositionByVector(-2, 1);
        checkedByKnight[7] = this.position.getNewPositionByVector(-2, -1);
        for (Position test : checkedByKnight) {
            if (test.isOnBoard()) {
                if (board.isOccupiedByColor(test, Color.BLACK) && board.isOccupiedByKnight(test, Color.BLACK)) {
                    possibleActions.addPiecesPositionsWhichAreCheckingTheKing(test);
                }
            }
        }

        Position[] checkedByQueenBishopRook = new Position[8];
        checkedByQueenBishopRook[0] = this.position.getNewPositionByVector(1, 1);
        checkedByQueenBishopRook[1] = this.position.getNewPositionByVector(1, -1);
        checkedByQueenBishopRook[2] = this.position.getNewPositionByVector(-1, 1);
        checkedByQueenBishopRook[3] = this.position.getNewPositionByVector(-1, -1);

        checkedByQueenBishopRook[4] = this.position.getNewPositionByVector(0, 1);
        checkedByQueenBishopRook[5] = this.position.getNewPositionByVector(0, -1);
        checkedByQueenBishopRook[6] = this.position.getNewPositionByVector(1, 0);
        checkedByQueenBishopRook[7] = this.position.getNewPositionByVector(-1, 0);
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[0].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[0]))
                checkedByQueenBishopRook[0] = this.position.getNewPositionByVector(1 + i, 1 + i);
            else if (checkedByQueenBishopRook[0].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[0]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[0], Color.BLACK) || board.isOccupiedByBishop(checkedByQueenBishopRook[0], Color.BLACK))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[0]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[1].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[1]))
                checkedByQueenBishopRook[1] = this.position.getNewPositionByVector(1 + i, -1 - i);
            else if (checkedByQueenBishopRook[1].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[1]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[1], Color.BLACK) || board.isOccupiedByBishop(checkedByQueenBishopRook[1], Color.BLACK))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[1]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[2].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[2]))
                checkedByQueenBishopRook[2] = this.position.getNewPositionByVector(-1 - i, 1 + i);
            else if (checkedByQueenBishopRook[2].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[2]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[2], Color.BLACK) || board.isOccupiedByBishop(checkedByQueenBishopRook[2], Color.BLACK))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[2]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[3].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[3]))
                checkedByQueenBishopRook[3] = this.position.getNewPositionByVector(-1 - i, -1 - i);
            else if (checkedByQueenBishopRook[3].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[3]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[3], Color.BLACK) || board.isOccupiedByBishop(checkedByQueenBishopRook[3], Color.BLACK))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[3]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[4].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[4]))
                checkedByQueenBishopRook[4] = this.position.getNewPositionByVector(0, 1 + i);
            else if (checkedByQueenBishopRook[4].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[4]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[4], Color.BLACK) || board.isOccupiedByRook(checkedByQueenBishopRook[4], Color.BLACK))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[4]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[5].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[5]))
                checkedByQueenBishopRook[5] = this.position.getNewPositionByVector(0, -1 - i);
            else if (checkedByQueenBishopRook[5].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[5]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[5], Color.BLACK) || board.isOccupiedByRook(checkedByQueenBishopRook[5], Color.BLACK))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[5]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[6].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[6]))
                checkedByQueenBishopRook[6] = this.position.getNewPositionByVector(+1 + i, 0);
            else if (checkedByQueenBishopRook[6].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[6]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[6], Color.BLACK) || board.isOccupiedByRook(checkedByQueenBishopRook[6], Color.BLACK))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[6]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[7].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[7]))
                checkedByQueenBishopRook[7] = this.position.getNewPositionByVector(-1 - i, 0);
            else if (checkedByQueenBishopRook[7].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[7]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[7], Color.BLACK) || board.isOccupiedByRook(checkedByQueenBishopRook[7], Color.BLACK))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[7]);
                break;
            } else
                break;
        }
        Position[] checkedByPawn = new Position[2];
        checkedByPawn[0] = this.position.getNewPositionByVector(-1, 1);
        checkedByPawn[1] = this.position.getNewPositionByVector(-1, -1);
        for (Position test : checkedByPawn) {
            if (test.isOnBoard()) {
                if (board.isOccupiedByColor(test, Color.BLACK) && board.isOccupiedByPawn(test, Color.BLACK)) {
                    possibleActions.addPiecesPositionsWhichAreCheckingTheKing(test);
                }
            }
        }
        return possibleActions;
    }

    public PossibleActions piecesPositionsCheckingBlackKing (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        Position[] checkedByKing = new Position[8];

        checkedByKing[0] = this.position.getNewPositionByVector(0, 1);
        checkedByKing[1] = this.position.getNewPositionByVector(0, -1);
        checkedByKing[2] = this.position.getNewPositionByVector(1, 0);
        checkedByKing[3] = this.position.getNewPositionByVector(-1, 0);
        checkedByKing[4] = this.position.getNewPositionByVector(1, 1);
        checkedByKing[5] = this.position.getNewPositionByVector(1, -1);
        checkedByKing[6] = this.position.getNewPositionByVector(-1, 1);
        checkedByKing[7] = this.position.getNewPositionByVector(-1, -1);
        for (Position test : checkedByKing) {
            if (test.isOnBoard()) {
                if (board.isOccupiedByColor(test, Color.WHITE) && board.isOccupiedByKing(test, Color.WHITE)) {
                    possibleActions.addPiecesPositionsWhichAreCheckingTheKing(test);
                }
            }
        }

        Position[] checkedByKnight = new Position[8];
        checkedByKnight[0] = this.position.getNewPositionByVector(1, 2);
        checkedByKnight[1] = this.position.getNewPositionByVector(1, -2);
        checkedByKnight[2] = this.position.getNewPositionByVector(2, 1);
        checkedByKnight[3] = this.position.getNewPositionByVector(2, -1);
        checkedByKnight[4] = this.position.getNewPositionByVector(-1, 2);
        checkedByKnight[5] = this.position.getNewPositionByVector(-1, -2);
        checkedByKnight[6] = this.position.getNewPositionByVector(-2, 1);
        checkedByKnight[7] = this.position.getNewPositionByVector(-2, -1);
        for (Position test : checkedByKnight) {
            if (test.isOnBoard()) {
                if (board.isOccupiedByColor(test, Color.WHITE) && board.isOccupiedByKnight(test, Color.WHITE)) {
                    possibleActions.addPiecesPositionsWhichAreCheckingTheKing(test);
                }
            }
        }

        Position[] checkedByQueenBishopRook = new Position[8];
        checkedByQueenBishopRook[0] = this.position.getNewPositionByVector(1, 1);
        checkedByQueenBishopRook[1] = this.position.getNewPositionByVector(1, -1);
        checkedByQueenBishopRook[2] = this.position.getNewPositionByVector(-1, 1);
        checkedByQueenBishopRook[3] = this.position.getNewPositionByVector(-1, -1);

        checkedByQueenBishopRook[4] = this.position.getNewPositionByVector(0, 1);
        checkedByQueenBishopRook[5] = this.position.getNewPositionByVector(0, -1);
        checkedByQueenBishopRook[6] = this.position.getNewPositionByVector(1, 0);
        checkedByQueenBishopRook[7] = this.position.getNewPositionByVector(-1, 0);
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[0].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[0]))
                checkedByQueenBishopRook[0] = this.position.getNewPositionByVector(1 + i, 1 + i);
            else if (checkedByQueenBishopRook[0].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[0]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[0], Color.WHITE) || board.isOccupiedByBishop(checkedByQueenBishopRook[0], Color.WHITE))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[0]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[1].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[1]))
                checkedByQueenBishopRook[1] = this.position.getNewPositionByVector(1 + i, -1 - i);
            else if (checkedByQueenBishopRook[1].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[1]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[1], Color.WHITE) || board.isOccupiedByBishop(checkedByQueenBishopRook[1], Color.WHITE))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[1]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[2].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[2]))
                checkedByQueenBishopRook[2] = this.position.getNewPositionByVector(-1 - i, 1 + i);
            else if (checkedByQueenBishopRook[2].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[2]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[2], Color.WHITE) || board.isOccupiedByBishop(checkedByQueenBishopRook[2], Color.WHITE))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[2]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[3].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[3]))
                checkedByQueenBishopRook[3] = this.position.getNewPositionByVector(-1 - i, -1 - i);
            else if (checkedByQueenBishopRook[3].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[3]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[3], Color.WHITE) || board.isOccupiedByBishop(checkedByQueenBishopRook[3], Color.WHITE))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[3]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[4].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[4]))
                checkedByQueenBishopRook[4] = this.position.getNewPositionByVector(0, 1 + i);
            else if (checkedByQueenBishopRook[4].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[4]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[4], Color.WHITE) || board.isOccupiedByRook(checkedByQueenBishopRook[4], Color.WHITE))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[4]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[5].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[5]))
                checkedByQueenBishopRook[5] = this.position.getNewPositionByVector(0, -1 - i);
            else if (checkedByQueenBishopRook[5].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[5]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[5], Color.WHITE) || board.isOccupiedByRook(checkedByQueenBishopRook[5], Color.WHITE))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[5]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[6].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[6]))
                checkedByQueenBishopRook[6] = this.position.getNewPositionByVector(+1 + i, 0);
            else if (checkedByQueenBishopRook[6].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[6]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[6], Color.WHITE) || board.isOccupiedByRook(checkedByQueenBishopRook[6], Color.WHITE))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[6]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[7].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[7]))
                checkedByQueenBishopRook[7] = this.position.getNewPositionByVector(-1 - i, 0);
            else if (checkedByQueenBishopRook[7].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[7]) &&
                    (board.isOccupiedByQueen(checkedByQueenBishopRook[7], Color.WHITE) || board.isOccupiedByRook(checkedByQueenBishopRook[7], Color.WHITE))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[7]);
                break;
            } else
                break;
        }
        Position[] checkedByPawn = new Position[2];
        checkedByPawn[0] = this.position.getNewPositionByVector(1, 1);
        checkedByPawn[1] = this.position.getNewPositionByVector(1, -1);
        for (Position test : checkedByPawn) {
            if (test.isOnBoard()) {
                if (board.isOccupiedByColor(test, Color.WHITE) && board.isOccupiedByPawn(test, Color.WHITE)) {
                    possibleActions.addPiecesPositionsWhichAreCheckingTheKing(test);
                }
            }
        }
        return possibleActions;
    }
}