package pl.wb.demo.chess.model.board;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.King;
import pl.wb.demo.chess.model.pieces.Pawn;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(MockitoJUnitRunner.class)
class BoardTest {

//    @Mock
    private Board board;
    private Position blackPawnPosition, positionWithoutPiece, blackKingPosition, whiteKingPosition;
    private ArrayList<Position> whitePiecesPositions, blackPiecesPositions;

    @BeforeEach
    void setUp () {
        board = new Board();
        // Black pawn position
        blackPawnPosition = new Position(1, 1);
        // Empty field on the board
        positionWithoutPiece = new Position(3, 1);
        // Black King Position
        blackKingPosition = new Position(0,4);
        // White King Position
        whiteKingPosition = new Position(7,4);
    }

    @Test
    @DisplayName("No piece on board with given position ")
    void shouldNotFindPieceOnPosition () {
        assertThrows(NullPointerException.class, () -> board.getColorFromTheBoardOnCurrentPosition(positionWithoutPiece));
    }

    @Test
    void shouldNotFindPieceOfThatColor () {
        assertNotEquals(board.getColorFromTheBoardOnCurrentPosition(blackPawnPosition), Color.WHITE);
    }

    @Test
    void shouldFindPieceOfThatColor () {
        assertEquals(board.getColorFromTheBoardOnCurrentPosition(blackPawnPosition), Color.BLACK);
    }

    @Test
    void shouldNotFindPieceOnBoard () {
        assertFalse(board.isOccupied(positionWithoutPiece));
    }

    @Test
    void shouldFindPieceOnBoard () {
        assertTrue(board.isOccupied(blackPawnPosition));
    }

    @Test
    void shouldFindAnyPieceOnGivenPosition () {
        assertTrue(board.isBoardOccupiedByAnyPiece(blackPawnPosition.getRow(), blackPawnPosition.getColumn()));
    }

    @Test
    void shouldNotFindAnyPieceOnGivenPosition () {
        assertFalse(board.isBoardOccupiedByAnyPiece(positionWithoutPiece.getRow(), positionWithoutPiece.getColumn()));
    }

    @Test
    void shouldBeOccupiedByBlackPawn () {
        assertTrue(board.isOccupiedBySpecificPiece(blackPawnPosition, Color.BLACK, Pawn.class));
    }

    @Test
    void shouldNotBeOccupiedByBlackKing () {
        assertFalse(board.isOccupiedBySpecificPiece(blackPawnPosition, Color.BLACK, King.class));
    }

    @Test
    void shouldNotBeOccupiedByWhitePawn () {
        assertFalse(board.isOccupiedBySpecificPiece(blackPawnPosition, Color.WHITE, Pawn.class));
    }

    @Test
    void shouldReturnBlackKingPosition () {
        assertEquals(blackKingPosition, board.getBlackKingPosition());
    }

    @Test
    void shouldNotReturnBlackKingPosition () {
        assertEquals(blackKingPosition, board.getBlackKingPosition());
    }

    @Test
    void shouldReturnWhiteKingPosition () {
        assertEquals(whiteKingPosition, board.getWhiteKingPosition());
    }

    @Test
    void shouldNotBlackPieceBeInCollectionOfWhitePieces () {
        whitePiecesPositions = board.getAllWhitePiecesPosition();
        assertFalse(whitePiecesPositions.contains(blackPawnPosition));
    }

    @Test
    void shouldBlackPieceBeInCollectionOBlackPieces () {
        blackPiecesPositions = board.getAllBlackPiecesPosition();
        assertTrue(blackPiecesPositions.contains(blackPawnPosition));
    }
}