package pl.wb.demo.chess.model.pieces;

import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;

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
            if(!board.isBoardOccupiedByAnyPiece(0, 6) && board.getBlackPiece(0, 7).getClass() == Rook.class) {
                Position castlingKingMove1 = this.position.setNewPosition(0, 6);
                if (!ChessGame.isBlackKingChecked() && !board.isBoardOccupiedByAnyPiece(0, 5) && board.getPiece(0, 7).getCountMoves() == 0) {
                    possibleActions.addPossibleCastlingKingMove(castlingKingMove1);//King move
                }
            }
            if(!board.isBoardOccupiedByAnyPiece(0, 3) & !board.isBoardOccupiedByAnyPiece(0, 2) && !board.isBoardOccupiedByAnyPiece(0, 1) && board.getBlackPiece(0, 0).getClass() == Rook.class) {
                Position castlingKingMove2 = this.position.setNewPosition(0, 2);
                if (!ChessGame.isBlackKingChecked() && !board.isBoardOccupiedByAnyPiece(0, 3) && board.getPiece(0, 0).getCountMoves() == 0) {
                    possibleActions.addPossibleCastlingKingMove(castlingKingMove2);//King move
                }
            }
        } else if (this.color == Color.WHITE && !ChessGame.whiteCastled && board.getWhiteKingPiece().getCountMoves() == 0) {
            if (!board.isBoardOccupiedByAnyPiece(7, 6) && !board.isBoardOccupiedByAnyPiece(7, 5)) {
                Position castlingKingMove1 = this.position.setNewPosition(7, 6);
                if (!ChessGame.isWhiteKingChecked() && board.getPiece(7, 7).getCountMoves() == 0
                        && board.getWhitePiece(7, 7).getClass() == Rook.class) {
                    possibleActions.addPossibleCastlingKingMove(castlingKingMove1);//King move
                }
            }
            if (!board.isBoardOccupiedByAnyPiece(7, 3) && !board.isBoardOccupiedByAnyPiece(7, 2) && !board.isBoardOccupiedByAnyPiece(7, 1) && board.getWhitePiece(7, 0).getClass() == Rook.class) {
                Position castlingKingMove2 = this.position.setNewPosition(7, 2);
                if (!ChessGame.isWhiteKingChecked() && !board.isBoardOccupiedByAnyPiece(7, 3) && board.getPiece(7, 0).getCountMoves() == 0) {
                    possibleActions.addPossibleCastlingKingMove(castlingKingMove2);//King move
                }
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
                if (board.isOccupiedByColor(test, Color.BLACK) && board.isOccupiedBySpecificPiece(test, Color.BLACK, King.class)) {
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
                if (board.isOccupiedByColor(test, Color.BLACK) && board.isOccupiedBySpecificPiece(test, Color.BLACK, Knight.class)) {
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
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[0], Color.BLACK, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[0], Color.BLACK, Bishop.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[0]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[1].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[1]))
                checkedByQueenBishopRook[1] = this.position.getNewPositionByVector(1 + i, -1 - i);
            else if (checkedByQueenBishopRook[1].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[1]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[1], Color.BLACK, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[1], Color.BLACK, Bishop.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[1]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[2].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[2]))
                checkedByQueenBishopRook[2] = this.position.getNewPositionByVector(-1 - i, 1 + i);
            else if (checkedByQueenBishopRook[2].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[2]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[2], Color.BLACK, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[2], Color.BLACK, Bishop.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[2]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[3].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[3]))
                checkedByQueenBishopRook[3] = this.position.getNewPositionByVector(-1 - i, -1 - i);
            else if (checkedByQueenBishopRook[3].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[3]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[3], Color.BLACK, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[3], Color.BLACK, Bishop.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[3]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[4].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[4]))
                checkedByQueenBishopRook[4] = this.position.getNewPositionByVector(0, 1 + i);
            else if (checkedByQueenBishopRook[4].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[4]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[4], Color.BLACK, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[4], Color.BLACK, Rook.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[4]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[5].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[5]))
                checkedByQueenBishopRook[5] = this.position.getNewPositionByVector(0, -1 - i);
            else if (checkedByQueenBishopRook[5].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[5]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[5], Color.BLACK, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[5], Color.BLACK, Rook.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[5]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[6].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[6]))
                checkedByQueenBishopRook[6] = this.position.getNewPositionByVector(+1 + i, 0);
            else if (checkedByQueenBishopRook[6].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[6]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[6], Color.BLACK, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[6], Color.BLACK, Rook.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[6]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[7].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[7]))
                checkedByQueenBishopRook[7] = this.position.getNewPositionByVector(-1 - i, 0);
            else if (checkedByQueenBishopRook[7].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[7]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[7], Color.BLACK, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[7], Color.BLACK, Rook.class))) {
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
                if (board.isOccupiedByColor(test, Color.BLACK) && board.isOccupiedBySpecificPiece(test, Color.BLACK, Pawn.class)) {
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
                if (board.isOccupiedByColor(test, Color.WHITE) && board.isOccupiedBySpecificPiece(test, Color.WHITE, King.class)) {
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
                if (board.isOccupiedByColor(test, Color.WHITE) && board.isOccupiedBySpecificPiece(test, Color.WHITE, Knight.class)) {
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
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[0], Color.WHITE, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[0], Color.WHITE, Bishop.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[0]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[1].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[1]))
                checkedByQueenBishopRook[1] = this.position.getNewPositionByVector(1 + i, -1 - i);
            else if (checkedByQueenBishopRook[1].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[1]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[1], Color.WHITE, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[1], Color.WHITE, Bishop.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[1]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[2].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[2]))
                checkedByQueenBishopRook[2] = this.position.getNewPositionByVector(-1 - i, 1 + i);
            else if (checkedByQueenBishopRook[2].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[2]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[2], Color.WHITE, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[2], Color.WHITE, Bishop.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[2]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[3].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[3]))
                checkedByQueenBishopRook[3] = this.position.getNewPositionByVector(-1 - i, -1 - i);
            else if (checkedByQueenBishopRook[3].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[3]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[3], Color.WHITE, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[3], Color.WHITE, Bishop.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[3]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[4].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[4]))
                checkedByQueenBishopRook[4] = this.position.getNewPositionByVector(0, 1 + i);
            else if (checkedByQueenBishopRook[4].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[4]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[4], Color.WHITE, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[4], Color.WHITE, Rook.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[4]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[5].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[5]))
                checkedByQueenBishopRook[5] = this.position.getNewPositionByVector(0, -1 - i);
            else if (checkedByQueenBishopRook[5].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[5]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[5], Color.WHITE, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[5], Color.WHITE, Rook.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[5]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[6].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[6]))
                checkedByQueenBishopRook[6] = this.position.getNewPositionByVector(+1 + i, 0);
            else if (checkedByQueenBishopRook[6].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[6]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[6], Color.WHITE, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[6], Color.WHITE, Rook.class))) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(checkedByQueenBishopRook[6]);
                break;
            } else
                break;
        }
        for (int i = 1; i < 8; i++) {
            if (checkedByQueenBishopRook[7].isOnBoard() && !board.isOccupied(checkedByQueenBishopRook[7]))
                checkedByQueenBishopRook[7] = this.position.getNewPositionByVector(-1 - i, 0);
            else if (checkedByQueenBishopRook[7].isOnBoard() && board.isOccupied(checkedByQueenBishopRook[7]) &&
                    (board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[7], Color.WHITE, Queen.class) || board.isOccupiedBySpecificPiece(checkedByQueenBishopRook[7], Color.WHITE, Rook.class))) {
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
                if (board.isOccupiedByColor(test, Color.WHITE) && board.isOccupiedBySpecificPiece(test, Color.WHITE, Pawn.class)) {
                    possibleActions.addPiecesPositionsWhichAreCheckingTheKing(test);
                }
            }
        }
        return possibleActions;
    }
}