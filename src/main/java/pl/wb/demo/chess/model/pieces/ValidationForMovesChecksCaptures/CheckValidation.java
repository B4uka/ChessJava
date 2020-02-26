package pl.wb.demo.chess.model.pieces.ValidationForMovesChecksCaptures;

import pl.wb.demo.chess.model.board.Board;
import pl.wb.demo.chess.model.board.PossibleActions;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;

public interface CheckValidation {

    PossibleActions piecesPositionsCheckingBlackKing (Board board);

    PossibleActions piecesPositionsCheckingWhiteKing (Board board);

    void kingCheckingKing (Board board, PossibleActions possibleActions);

    void knightCheckingKing (Board board, PossibleActions possibleActions);

    void pawnCheckingKing (Position kingPosition, Board board, PossibleActions possibleActions, int rowShift, int columnShift, Color opponentsColor);

    void queenRookCheckingKing (Position potentialQueenRookPositionCheckingKing, Board board, PossibleActions possibleActions, int rowShift, int columnShift, Color opponentsColor);

    void queenBishopCheckingKing (Position potentialQueenBishopPositionCheckingKing, Board board, PossibleActions possibleActions, int rowShift, int columnShift, Color opponentsColor);

}
