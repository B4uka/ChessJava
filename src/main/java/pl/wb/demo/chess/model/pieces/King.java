package pl.wb.demo.chess.model.pieces;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.ValidationForMovesChecksCaptures.CastlingKingMovesValidation;
import pl.wb.demo.chess.model.pieces.ValidationForMovesChecksCaptures.CheckValidation;
import pl.wb.demo.chess.model.pieces.ValidationForMovesChecksCaptures.MoveValidation;

public class King extends Piece implements MoveValidation, CheckValidation {

    public King (Position position, Color color, String code, int countMoves) {
        super(position, color, code, countMoves);
    }

    @Override
    public PossibleActions generatePossibleActions (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        moveValidation(this.position, board, possibleActions,0, 1);
        moveValidation(this.position, board, possibleActions,0, -1);
        moveValidation(this.position, board, possibleActions,1, 0);
        moveValidation(this.position, board, possibleActions,-1, 0);
        moveValidation(this.position, board, possibleActions,1, 1);
        moveValidation(this.position, board, possibleActions,1, -1);
        moveValidation(this.position, board, possibleActions,-1, 1);
        moveValidation(this.position, board, possibleActions,-1, -1);

        CastlingKingMovesValidation possibleKingCastleMoves = new CastlingKingMovesValidation(possibleActions, this.color, board, this.position);
        possibleKingCastleMoves.kingMovesForCastling();

        return possibleActions;
    }

    @Override
    public PossibleActions moveValidation (Position kingPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift) {
        //potential position knowing row and columns shifts
        Position kingPossibleMovePosition = kingPosition.getNewPositionByVector(rowShift, columnShift);

        if (kingPossibleMovePosition.isOnBoard()) {
            if (this.color == Color.WHITE && !board.isOccupied(kingPossibleMovePosition)) {//&& ChessGame.isWhiteKingChecked() ) {
                possibleActions.addPossibleMove(kingPossibleMovePosition);
            } else if (this.color == Color.WHITE && !board.isOccupiedByColor(kingPossibleMovePosition, Color.WHITE) && board.isOccupiedByColor((kingPossibleMovePosition), Color.BLACK)) {
                possibleActions.addPossibleCapture(kingPossibleMovePosition);
            } else if (this.color == Color.BLACK && !board.isOccupied(kingPossibleMovePosition)) {
                possibleActions.addPossibleMove(kingPossibleMovePosition);
            } else if (this.color == Color.BLACK && !board.isOccupiedByColor(kingPossibleMovePosition, Color.BLACK) && board.isOccupiedByColor((kingPossibleMovePosition), Color.WHITE)) {
                possibleActions.addPossibleCapture(kingPossibleMovePosition);
            }
        }
        return possibleActions;
    }

    @Override
    public PossibleActions piecesPositionsCheckingWhiteKing (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        //enemy king checking players king
        kingCheckingKing(board, possibleActions);

        //enemy king checking players king
        knightCheckingKing(board, possibleActions);

        //enemy queen or bishop checking players king
        queenBishopCheckingKing(this.position, board, possibleActions, 1, 1, Color.BLACK);
        queenBishopCheckingKing(this.position, board, possibleActions, 1, -1, Color.BLACK);
        queenBishopCheckingKing(this.position, board, possibleActions, -1, 1, Color.BLACK);
        queenBishopCheckingKing(this.position, board, possibleActions, -1, -1, Color.BLACK);

        //enemy queen or rook checking players king
        queenRookCheckingKing(this.position, board, possibleActions, 0, 1, Color.BLACK);
        queenRookCheckingKing(this.position, board, possibleActions, 0, -1, Color.BLACK);
        queenRookCheckingKing(this.position, board, possibleActions, 1, 0, Color.BLACK);
        queenRookCheckingKing(this.position, board, possibleActions, -1, 0, Color.BLACK);

        //enemy pawn checking players king
        pawnCheckingKing(this.position, board, possibleActions, -1, 1, Color.BLACK);
        pawnCheckingKing(this.position, board, possibleActions, -1, -1, Color.BLACK);

        return possibleActions;
    }

    @Override
    public PossibleActions piecesPositionsCheckingBlackKing (Board board) {
        PossibleActions possibleActions = new PossibleActions();

        //enemy king checking players king
        kingCheckingKing(board, possibleActions);

        //enemy king checking players king
        knightCheckingKing(board, possibleActions);

        //enemy queen or bishop checking players king
        queenBishopCheckingKing(this.position, board, possibleActions, 1, 1, Color.WHITE);
        queenBishopCheckingKing(this.position, board, possibleActions, 1, -1, Color.WHITE);
        queenBishopCheckingKing(this.position, board, possibleActions, -1, 1, Color.WHITE);
        queenBishopCheckingKing(this.position, board, possibleActions, -1, -1, Color.WHITE);

        //enemy queen or rook checking players king
        queenRookCheckingKing(this.position, board, possibleActions, 0, 1, Color.WHITE);
        queenRookCheckingKing(this.position, board, possibleActions, 0, -1, Color.WHITE);
        queenRookCheckingKing(this.position, board, possibleActions, 1, 0, Color.WHITE);
        queenRookCheckingKing(this.position, board, possibleActions, -1, 0, Color.WHITE);

        //enemy pawn checking players king
        pawnCheckingKing(this.position, board, possibleActions, 1, 1, Color.WHITE);
        pawnCheckingKing(this.position, board, possibleActions, 1, -1, Color.WHITE);

        return possibleActions;
    }

    @Override
    public void kingCheckingKing (Board board, PossibleActions possibleActions) {
        Position[] checkedByKing = new Position[8];

        // possible position of enemy king checking players king
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
                if (this.color == Color.BLACK && board.isOccupiedByColor(test, Color.WHITE) && board.isOccupiedBySpecificPiece(test, Color.WHITE, King.class)) {
                    possibleActions.addPiecesPositionsWhichAreCheckingTheKing(test);
                }else if (this.color == Color.WHITE && board.isOccupiedByColor(test, Color.BLACK) && board.isOccupiedBySpecificPiece(test, Color.BLACK, King.class)) {
                    possibleActions.addPiecesPositionsWhichAreCheckingTheKing(test);
                }
            }
        }
    }

    @Override
    public void knightCheckingKing (Board board, PossibleActions possibleActions) {
        Position[] checkedByKnight = new Position[8];

        // possible position of enemy king checking players king
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
                if (this.color == Color.BLACK && board.isOccupiedByColor(knightPossibleMovePosition, Color.WHITE) && board.isOccupiedBySpecificPiece(knightPossibleMovePosition, Color.WHITE, Knight.class)) {
                    possibleActions.addPiecesPositionsWhichAreCheckingTheKing(knightPossibleMovePosition);
                }else if (this.color == Color.WHITE && board.isOccupiedByColor(knightPossibleMovePosition, Color.BLACK) && board.isOccupiedBySpecificPiece(knightPossibleMovePosition, Color.BLACK, Knight.class)) {
                        possibleActions.addPiecesPositionsWhichAreCheckingTheKing(knightPossibleMovePosition);
                }
            }
        }
    }

    @Override
    public void pawnCheckingKing (Position kingPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift, Color opponentsColor) {
        //potential position knowing row and columns shifts
        Position potentialPawnPositionCheckingKing = kingPosition.getNewPositionByVector(rowShift, columnShift);

        if (potentialPawnPositionCheckingKing.isOnBoard()) {
            if (board.isOccupiedBySpecificPiece(potentialPawnPositionCheckingKing, opponentsColor, Pawn.class)) {
                possibleActions.addPiecesPositionsWhichAreCheckingTheKing(potentialPawnPositionCheckingKing);
            }
        }
    }

    @Override
    public void queenRookCheckingKing (Position kingPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift, Color opponentsColor) {
        //first iteration - potential position knowing row and columns shifts
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
    public void queenBishopCheckingKing (Position kingPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift, Color opponentsColor) {
        //first iteration - potential position knowing row and columns shifts
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