package pl.chessWebApp.chess.model.pieces.check;

import pl.chessWebApp.chess.model.board.Board;
import pl.chessWebApp.chess.model.board.PossibleActions;
import pl.chessWebApp.chess.model.piece_properties.Color;
import pl.chessWebApp.chess.model.piece_properties.Position;

public interface PositionsOfPiecesThatCouldCheckKings {

    PossibleActions piecesPositionsCheckingBlackKing (Board board, Position position);

    PossibleActions piecesPositionsCheckingWhiteKing (Board board, Position position);

    void kingCheckingKing (Position kingPosition, Board board, PieceCheckingKing possibleActions, Color opponentsColor);

    void knightCheckingKing (Position kingPosition, Board board, PieceCheckingKing possibleActions, Color opponentsColor);

    void pawnCheckingKing (Position kingPosition, Board board, PieceCheckingKing possibleActions, int rowShift, int columnShift, Color opponentsColor);

    void queenRookCheckingKing (Position kingPosition, Board board, PieceCheckingKing possibleActions, int rowShift, int columnShift, Color opponentsColor);

    void queenBishopCheckingKing (Position kingPosition, Board board, PieceCheckingKing possibleActions, int rowShift, int columnShift, Color opponentsColor);
}
