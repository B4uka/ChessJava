package pl.wb.demo.chess.model.pieces.ValidationForMovesChecksCaptures;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;

public interface CheckValidation {

    PossibleActions piecesPositionsCheckingBlackKing (Board board, Position position);

    PossibleActions piecesPositionsCheckingWhiteKing (Board board, Position position);

    void kingCheckingKing (Position kingPosition, Board board, CheckingPiecesPositions possibleActions);

    void knightCheckingKing (Position kingPosition, Board board, CheckingPiecesPositions possibleActions);

    void pawnCheckingKing (Position kingPosition, Board board, CheckingPiecesPositions possibleActions, int rowShift, int columnShift, Color opponentsColor);

    void queenRookCheckingKing (Position kingPosition, Board board, CheckingPiecesPositions possibleActions, int rowShift, int columnShift, Color opponentsColor);

    void queenBishopCheckingKing (Position kingPosition, Board board, CheckingPiecesPositions possibleActions, int rowShift, int columnShift, Color opponentsColor);

}
