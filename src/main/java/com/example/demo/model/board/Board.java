package com.example.demo.model.board;

import com.example.demo.communication.field.Field;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;
import com.example.demo.model.pieces.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    protected Piece[][] boardOfPieces;
    public Position blackKingPosition, whiteKingPosition, blacksPositions, whitesPosition;
    public ArrayList<Position> whitePiecesPositions, blackPiecesPositions;
    public ArrayList<String> piecesPositionsWithCode;
    public HashMap<String, String> boardFieldAndCodes;

    public Board () {
        this.boardOfPieces = new Piece[8][8];
        fillBoardWithPieces();
    }

    public Piece[][] getBoard () {
        return boardOfPieces;
    }

    public Color getColorFromTheBoardOnCurrentPosition (Position position) {
        return this.boardOfPieces[position.getRow()][position.getColumn()].getColor();
    }

    public boolean isOccupied (Position position) {

        return this.boardOfPieces[position.getRow()][position.getColumn()] != null;
    }
    public boolean isWhiteKingOnStartingPosition () {
        return this.boardOfPieces[7][4].getClass() == King.class && this.boardOfPieces[7][4].getColor() == Color.WHITE;
    }
    public boolean isBlackKingOnStartingPosition () {
        return this.boardOfPieces[0][4].getClass() == King.class && this.boardOfPieces[0][4].getColor() == Color.WHITE;
    }
    public boolean isBoardOccupiedByPiece(int row, int column){
        return this.boardOfPieces[row][column] != null;
    }

    public boolean isOccupiedByColor (Position position, Color color) {
        Piece piece = this.boardOfPieces[position.getRow()][position.getColumn()];
        if (piece != null && piece.getColor() == color) {
            return true;
        }
        return false;
    }

    // There is also less specific method: isOccupiedByKing which is not including color of the King
    public boolean isOccupiedByKing (Position position, Color color) {
        Piece piece = this.boardOfPieces[position.getRow()][position.getColumn()];
        if (piece.getClass() == King.class && piece.getColor() == color) {
            return true;
        }
        return false;
    }

    public boolean isOccupiedByKnight (Position position, Color color) {
        Piece piece = this.boardOfPieces[position.getRow()][position.getColumn()];
        if (piece.getClass() == Knight.class && piece.getColor() == color) {
            return true;
        }
        return false;
    }

    public boolean isOccupiedByRook (Position position, Color color) {
        Piece piece = this.boardOfPieces[position.getRow()][position.getColumn()];
        if (piece.getClass() == Rook.class && piece.getColor() == color) {
            return true;
        }
        return false;
    }

    public boolean isOccupiedByBishop (Position position, Color color) {
        Piece piece = this.boardOfPieces[position.getRow()][position.getColumn()];
        if (piece.getClass() == Bishop.class && piece.getColor() == color) {
            return true;
        }
        return false;
    }

    public boolean isOccupiedByQueen (Position position, Color color) {
        Piece piece = this.boardOfPieces[position.getRow()][position.getColumn()];
        if (piece.getClass() == Queen.class && piece.getColor() == color) {
            return true;
        }
        return false;
    }

    public boolean isOccupiedByPawn (Position position, Color color) {
        Piece piece = this.boardOfPieces[position.getRow()][position.getColumn()];
        if (piece.getClass() == Pawn.class && piece.getColor() == color) {
            return true;
        }
        return false;
    }

    public Position getBlackKingPosition () {

        for (int i = 0; i < this.boardOfPieces.length; i++) {
            for (int j = 0; j < this.boardOfPieces.length; j++) {
                try {
                    this.boardOfPieces[i][j].getClass();
                    if (boardOfPieces[i][j].getClass() == King.class && this.boardOfPieces[i][j].getColor() == Color.BLACK) {
                        blackKingPosition = new Position(i, j);
                    }
                } catch (NullPointerException e) {
                }
            }
        }
        return blackKingPosition;
    }

    public Position getWhiteKingPosition () {

        for (int i = 0; i < this.boardOfPieces.length; i++) {
            for (int j = 0; j < this.boardOfPieces.length; j++) {
                try {
                    this.boardOfPieces[i][j].getClass();
                    if (this.boardOfPieces[i][j].getClass() == King.class && this.boardOfPieces[i][j].getColor() == Color.WHITE) {
                        whiteKingPosition = new Position(i, j);
                    }
                } catch (NullPointerException e) {
                }
            }
        }
        return whiteKingPosition;
    }

    //TODO all white Pieces
    public ArrayList<Position> getAllWhitePiecesPosition () {
        whitePiecesPositions = new ArrayList<>();

        for (int i = 0; i < this.boardOfPieces.length; i++) {
            for (int j = 0; j < this.boardOfPieces.length; j++) {
                try {
                    this.boardOfPieces[i][j].getClass();
                    if (this.boardOfPieces[i][j].getClass() != null && this.boardOfPieces[i][j].getColor() == Color.WHITE) {
                        whitesPosition = new Position(i, j);
                        whitePiecesPositions.add(whitesPosition);
                    }
                } catch (NullPointerException e) {
                }
            }
        }
        return whitePiecesPositions;
    }

    //TODO all black Pieces
    public ArrayList<Position> getAllBlackPiecesPosition () {
        blackPiecesPositions = new ArrayList<>();
        for (int i = 0; i < this.boardOfPieces.length; i++) {
            for (int j = 0; j < this.boardOfPieces.length; j++) {
                try {
                    this.boardOfPieces[i][j].getClass();
                    if (this.boardOfPieces[i][j].getClass() != null && this.boardOfPieces[i][j].getColor() == Color.BLACK) {
                        blacksPositions = new Position(i, j);
                        blackPiecesPositions.add(blacksPositions);
                    }
                } catch (NullPointerException e) {
                }
            }
        }
        return blackPiecesPositions;
    }

    public ArrayList<String> getBoardWithPiecesActualCodes () {
        piecesPositionsWithCode = new ArrayList<>();
        String codeOfThePiece;
        for (int i = 0; i < this.boardOfPieces.length; i++) {
            for (int j = 0; j < this.boardOfPieces.length; j++) {
                try {
                    if (this.boardOfPieces[i][j].getCode() == null) {
                        codeOfThePiece = "";
                        piecesPositionsWithCode.add(codeOfThePiece);
                    } else if (this.boardOfPieces[i][j].getCode() != null) {
                        codeOfThePiece = boardOfPieces[i][j].getCode();
                        piecesPositionsWithCode.add(codeOfThePiece);
                    }
                } catch (NullPointerException e) {
                }
            }
        }
        return piecesPositionsWithCode;
    }

    // There is more specific method: isOccupiedByKing that also includes color of the King
    public boolean isOccupiedByKingOfAnyColor (Position position) {
        Piece piece = this.boardOfPieces[position.getRow()][position.getColumn()];
        if (piece.getClass() == King.class) {
            return true;
        }
        return false;
    }

    public Piece getWhitePiece (int row, int column) {
        if (this.boardOfPieces[row][column].getColor() == Color.WHITE) {
            return boardOfPieces[row][column];
        }
        return null;
    }

    public Piece getBlackPiece (int row, int column) {
        if (this.boardOfPieces[row][column].getColor() == Color.BLACK) {
            return boardOfPieces[row][column];
        }
        return null;
    }

    public Piece getPiece (int row, int column) {
        return boardOfPieces[row][column];
    }

    public void setPiece (Piece piece) {
        boardOfPieces[piece.getPosition().getRow()][piece.getPosition().getColumn()] = piece;
    }

    public void setEmptyByPosition (Position position) {
        boardOfPieces[position.getRow()][position.getColumn()] = null;
    }
    public void setEmptyByRowAndColumn (int row, int column) {
        boardOfPieces[row][column] = null;
    }

    public String getPieceCode (Piece piece) {
        return boardOfPieces[piece.getPosition().getRow()][piece.getPosition().getColumn()].getCode();
    }

    private void fillBoardWithPieces () {
//        this.boardOfPieces[0][0] = new King(new Position(0, 0), Color.BLACK);
//        this.boardOfPieces[7][7] = new King(new Position(7, 7), Color.WHITE);
//
//        this.boardOfPieces[6][6] = new Queen(new Position(6, 6), Color.WHITE);
//        this.boardOfPieces[1][1] = new Queen(new Position(1, 1), Color.BLACK);
//
//        this.boardOfPieces[6][0] = new Rook(new Position(6, 0), Color.BLACK);
//        this.boardOfPieces[4][1] = new Rook(new Position(4, 1), Color.WHITE);

        this.boardOfPieces[0][4] = new King(new Position(0, 4), Color.BLACK, "&#9818;");
        this.boardOfPieces[7][4] = new King(new Position(7, 4), Color.WHITE, "&#9812;");

//        this.boardOfPieces[0][3] = new Queen(new Position(0, 3), Color.BLACK, "&#9819;");
//        this.boardOfPieces[7][3] = new Queen(new Position(7, 3), Color.WHITE, "&#9813;");

        this.boardOfPieces[0][0] = new Rook(new Position(0, 0), Color.BLACK, "&#9820;");
        this.boardOfPieces[0][7] = new Rook(new Position(0, 7), Color.BLACK, "&#9820;");
        this.boardOfPieces[7][0] = new Rook(new Position(7, 0), Color.WHITE, "&#9814;");
        this.boardOfPieces[7][7] = new Rook(new Position(7, 7), Color.WHITE, "&#9814;");

//        this.boardOfPieces[0][1] = new Knight(new Position(0, 1), Color.BLACK, "&#9822;");
//        this.boardOfPieces[0][6] = new Knight(new Position(0, 6), Color.BLACK, "&#9822;");
//        this.boardOfPieces[7][1] = new Knight(new Position(7, 1), Color.WHITE, "&#9816;");
//        this.boardOfPieces[7][6] = new Knight(new Position(7, 6), Color.WHITE, "&#9816;");

//        this.boardOfPieces[0][2] = new Bishop(new Position(0, 2), Color.BLACK, "&#9821;");
//        this.boardOfPieces[0][5] = new Bishop(new Position(0, 5), Color.BLACK, "&#9821;");
//        this.boardOfPieces[7][2] = new Bishop(new Position(7, 2), Color.WHITE, "&#9815;");
//        this.boardOfPieces[7][5] = new Bishop(new Position(7, 5), Color.WHITE, "&#9815;");

        this.boardOfPieces[1][0] = new Pawn(new Position(1, 0), Color.BLACK, "&#9823;");
        this.boardOfPieces[1][1] = new Pawn(new Position(1, 1), Color.BLACK, "&#9823;");
        this.boardOfPieces[1][2] = new Pawn(new Position(1, 2), Color.BLACK, "&#9823;");
        this.boardOfPieces[1][3] = new Pawn(new Position(1, 3), Color.BLACK, "&#9823;");
        this.boardOfPieces[1][4] = new Pawn(new Position(1, 4), Color.BLACK, "&#9823;");
        this.boardOfPieces[1][5] = new Pawn(new Position(1, 5), Color.BLACK, "&#9823;");
        this.boardOfPieces[1][6] = new Pawn(new Position(1, 6), Color.BLACK, "&#9823;");
        this.boardOfPieces[1][7] = new Pawn(new Position(1, 7), Color.BLACK, "&#9823;");

        this.boardOfPieces[6][0] = new Pawn(new Position(6, 0), Color.WHITE, "&#9817;");
        this.boardOfPieces[6][1] = new Pawn(new Position(6, 1), Color.WHITE, "&#9817;");
        this.boardOfPieces[6][2] = new Pawn(new Position(6, 2), Color.WHITE, "&#9817;");
        this.boardOfPieces[6][3] = new Pawn(new Position(6, 3), Color.WHITE, "&#9817;");
        this.boardOfPieces[6][4] = new Pawn(new Position(6, 4), Color.WHITE, "&#9817;");
        this.boardOfPieces[6][5] = new Pawn(new Position(6, 5), Color.WHITE, "&#9817;");
        this.boardOfPieces[6][6] = new Pawn(new Position(6, 6), Color.WHITE, "&#9817;");
        this.boardOfPieces[6][7] = new Pawn(new Position(6, 7), Color.WHITE, "&#9817;");

//        for (int i = 2; i < 6; i++) {
//            for (int j = 0; j < 8; j++){
//                boardOfPieces[i][j].setCode("");
//            }
//        }
    }

    public void printBoard () {
        for (int i = 0; i < boardOfPieces.length; i++) {
            for (int j = 0; j < boardOfPieces.length; j++) {
                try {
                    boardOfPieces[i][j].getClass();

                    if (boardOfPieces[i][j].getClass() == Rook.class && boardOfPieces[i][j].getColor() != Color.WHITE) {
                        System.out.print("r  ");
                    } else if (boardOfPieces[i][j].getClass() == Pawn.class && boardOfPieces[i][j].getColor() != Color.WHITE) {
                        System.out.print("p  ");
                    } else if (boardOfPieces[i][j].getClass() == Queen.class && boardOfPieces[i][j].getColor() != Color.WHITE) {
                        System.out.print("q  ");
                    } else if (boardOfPieces[i][j].getClass() == Knight.class && boardOfPieces[i][j].getColor() != Color.WHITE) {
                        System.out.print("n  ");
                    } else if (boardOfPieces[i][j].getClass() == King.class && boardOfPieces[i][j].getColor() != Color.WHITE) {
                        System.out.print("k  ");
                    } else if (boardOfPieces[i][j].getClass() == Bishop.class && boardOfPieces[i][j].getColor() != Color.WHITE) {
                        System.out.print("b  ");
                    } else if (boardOfPieces[i][j].getClass() == Rook.class && boardOfPieces[i][j].getColor() != Color.BLACK) {
                        System.out.print("R  ");
                    } else if (boardOfPieces[i][j].getClass() == Pawn.class && boardOfPieces[i][j].getColor() != Color.BLACK) {
                        System.out.print("P  ");
                    } else if (boardOfPieces[i][j].getClass() == Queen.class && boardOfPieces[i][j].getColor() != Color.BLACK) {
                        System.out.print("Q  ");
                    } else if (boardOfPieces[i][j].getClass() == Knight.class && boardOfPieces[i][j].getColor() != Color.BLACK) {
                        System.out.print("N  ");
                    } else if (boardOfPieces[i][j].getClass() == King.class && boardOfPieces[i][j].getColor() != Color.BLACK) {
                        System.out.print("K  ");
                    } else if (boardOfPieces[i][j].getClass() == Bishop.class && boardOfPieces[i][j].getColor() != Color.BLACK) {
                        System.out.print("B  ");

                    }
                } catch (NullPointerException e) {
                    System.out.print(".  ");
                    continue;
                }
            }
            System.out.println();
        }
    }

    public void printBoardWithCodes () {
        for (int i = 0; i < boardOfPieces.length; i++) {
            for (int j = 0; j < boardOfPieces.length; j++) {
                try {
                    System.out.print(boardOfPieces[i][j].getCode() + "\t");

                } catch (NullPointerException e) {
                    System.out.print("\t . \t");
                    continue;
                }
            }
            System.out.println();
        }
    }

    //TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public HashMap<String, String> getBoardFieldAndCodes () {
        boardFieldAndCodes = new HashMap<>();

        for (int i = 0; i < boardOfPieces.length; i++) {
            for (int j = 0; j < boardOfPieces.length; j++) {
                try {
                    if (this.boardOfPieces[i][j].getCode() != null) {
                        boardFieldAndCodes.put(Field.getFieldByPosition(i, j), boardOfPieces[i][j].getCode());
                    } else {
                        boardFieldAndCodes.put(Field.getFieldByPosition(i, j), "");
                    }
                } catch (NullPointerException f) {
                    boardFieldAndCodes.put(Field.getFieldByPosition(i, j), "");
                }
            }
        }
//        String jsonResponse = new Gson().toJson(boardFieldAndCodes);
//        System.out.printf( "JSON: %s", jsonResponse );
//        System.out.println(boardFieldAndCodes);
        return boardFieldAndCodes;
    }
}