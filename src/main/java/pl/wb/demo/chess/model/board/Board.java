package pl.wb.demo.chess.model.board;

import pl.wb.demo.chess.communication.field.Field;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;
import pl.wb.demo.chess.model.pieces.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class Board {

    protected Piece[][] boardOfPieces;
    public Position blackKingPosition, whiteKingPosition, blacksPositions, whitesPosition;
    public ArrayList<Position> whitePiecesPositions, blackPiecesPositions;
    public ArrayList<String> piecesPositionsWithCode;
    private HashMap<String, String> boardFieldAndCodes;

    public Board() {
        this.boardOfPieces = new Piece[8][8];
        fillBoardWithPieces();
    }

    public Piece[][] getBoard() {
        return boardOfPieces;
    }

    public void setBoardFieldAndCodes(HashMap<String, String> boardFieldAndCodes) {
        this.boardFieldAndCodes = boardFieldAndCodes;
    }


    public Color getColorFromTheBoardOnCurrentPosition(Position position) {
        return this.boardOfPieces[position.getRow()][position.getColumn()].getColor();
    }

    public boolean isOccupied(Position position) {
        return this.boardOfPieces[position.getRow()][position.getColumn()] != null;
    }

    public boolean isBoardOccupiedByAnyPiece(int row, int column) {
        return this.boardOfPieces[row][column] != null;
    }

    public boolean isOccupiedByColor(Position position, Color color) {
        Piece piece = this.boardOfPieces[position.getRow()][position.getColumn()];
        return piece != null && piece.getColor() == color;
    }

    public boolean isOccupiedBySpecificPiece(Position position, Color color, Class pieceClass) {
        Piece piece = this.boardOfPieces[position.getRow()][position.getColumn()];
        return piece != null && piece.getClass() == pieceClass && piece.getColor() == color;
    }

    public Position getBlackKingPosition() {

        for (int i = 0; i < this.boardOfPieces.length; i++) {
            for (int j = 0; j < this.boardOfPieces.length; j++) {
                if (this.boardOfPieces[i][j] != null && boardOfPieces[i][j].getClass() == King.class && this.boardOfPieces[i][j].getColor() == Color.BLACK) {
                    blackKingPosition = new Position(i, j);
                }
            }
        }
        return blackKingPosition;
    }

    public Piece getBlackKingPiece() {

        for (int i = 0; i < this.boardOfPieces.length; i++) {
            for (int j = 0; j < this.boardOfPieces.length; j++) {
                if (this.boardOfPieces[i][j] != null && boardOfPieces[i][j].getClass() == King.class && this.boardOfPieces[i][j].getColor() == Color.BLACK) {
                    blackKingPosition = new Position(i, j);
                }
            }
        }
        return boardOfPieces[blackKingPosition.getRow()][blackKingPosition.getColumn()];
    }

    public Piece getWhiteKingPiece() {

        for (int i = 0; i < this.boardOfPieces.length; i++) {
            for (int j = 0; j < this.boardOfPieces.length; j++) {
                if (this.boardOfPieces[i][j] != null && boardOfPieces[i][j] != null && boardOfPieces[i][j].getClass() == King.class && this.boardOfPieces[i][j].getColor() == Color.WHITE) {
                    whiteKingPosition = new Position(i, j);
                }
            }
        }
        return boardOfPieces[whiteKingPosition.getRow()][whiteKingPosition.getColumn()];
    }

    public Position getWhiteKingPosition() {

        for (int i = 0; i < this.boardOfPieces.length; i++) {
            for (int j = 0; j < this.boardOfPieces.length; j++) {
                if (this.boardOfPieces[i][j] != null && this.boardOfPieces[i][j].getClass() == King.class && this.boardOfPieces[i][j].getColor() == Color.WHITE) {
                    whiteKingPosition = new Position(i, j);
                }
            }
        }
        return whiteKingPosition;
    }

    //TODO all white Pieces
    public ArrayList<Position> getAllWhitePiecesPosition() {
        whitePiecesPositions = new ArrayList<>();

        for (int i = 0; i < this.boardOfPieces.length; i++) {
            for (int j = 0; j < this.boardOfPieces.length; j++) {
                if (this.boardOfPieces[i][j] != null && this.boardOfPieces[i][j].getClass() != null && this.boardOfPieces[i][j].getColor() == Color.WHITE) {
                    whitesPosition = new Position(i, j);
                    whitePiecesPositions.add(whitesPosition);
                }
            }
        }
        return whitePiecesPositions;
    }

    //TODO all black Pieces
    public ArrayList<Position> getAllBlackPiecesPosition() {
        blackPiecesPositions = new ArrayList<>();

        for (int i = 0; i < this.boardOfPieces.length; i++) {
            for (int j = 0; j < this.boardOfPieces.length; j++) {
                if (this.boardOfPieces[i][j] != null && this.boardOfPieces[i][j].getClass() != null && this.boardOfPieces[i][j].getColor() == Color.BLACK) {
                    blacksPositions = new Position(i, j);
                    blackPiecesPositions.add(blacksPositions);
                }
            }
        }
        return blackPiecesPositions;
    }

    public ArrayList<String> getBoardWithPiecesCodes() {
        piecesPositionsWithCode = new ArrayList<>();
        String codeOfThePiece;

        for (int i = 0; i < this.boardOfPieces.length; i++) {
            for (int j = 0; j < this.boardOfPieces.length; j++) {
                if (this.boardOfPieces[i][j].getCode() == null) {
                    codeOfThePiece = "";
                    piecesPositionsWithCode.add(codeOfThePiece);
                } else if (this.boardOfPieces[i][j].getCode() != null) {
                    codeOfThePiece = boardOfPieces[i][j].getCode();
                    piecesPositionsWithCode.add(codeOfThePiece);
                }
            }
        }
        return piecesPositionsWithCode;
    }

    public Piece getPieceByColor(int row, int column, Color color) {
        if (this.boardOfPieces[row][column].getColor() == color) {
            return boardOfPieces[row][column];
        }
        return null;
    }

    public Piece getWhitePiece(int row, int column) {
        if (this.boardOfPieces[row][column].getColor() == Color.WHITE) {
            return boardOfPieces[row][column];
        }
        return null;
    }

    public Piece getBlackPiece(int row, int column) {
        if (this.boardOfPieces[row][column].getColor() == Color.BLACK) {
            return boardOfPieces[row][column];
        }
        return null;
    }

    public Piece getPiece(int row, int column) {
        return boardOfPieces[row][column];
    }

    public String getPieceNameByFieldID(Field fieldID) {
        return boardOfPieces[fieldID.getRow()][fieldID.getColumn()].getClass().getSimpleName();
    }

    public void setPiece(Piece piece) {
        boardOfPieces[piece.getPosition().getRow()][piece.getPosition().getColumn()] = piece;
    }

    public void setEmptyByPosition(Position position) {
        boardOfPieces[position.getRow()][position.getColumn()] = null;
    }

    public Piece[][] clearBoardAndSetPieceOnStartingPositions() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardOfPieces[i][j] = null;
            }
        }
        fillBoardWithPieces();
        return boardOfPieces;
    }

    public void fillBoardWithPieces() {

        this.boardOfPieces[0][4] = new King(new Position(0, 4), Color.BLACK, "&#9818;", 0);
        this.boardOfPieces[7][4] = new King(new Position(7, 4), Color.WHITE, "&#9812;", 0);

        this.boardOfPieces[0][3] = new Queen(new Position(0, 3), Color.BLACK, "&#9819;", 0);
        this.boardOfPieces[7][3] = new Queen(new Position(7, 3), Color.WHITE, "&#9813;", 0);

        this.boardOfPieces[0][0] = new Rook(new Position(0, 0), Color.BLACK, "&#9820;", 0);
        this.boardOfPieces[0][7] = new Rook(new Position(0, 7), Color.BLACK, "&#9820;", 0);
        this.boardOfPieces[7][0] = new Rook(new Position(7, 0), Color.WHITE, "&#9814;", 0);
        this.boardOfPieces[7][7] = new Rook(new Position(7, 7), Color.WHITE, "&#9814;", 0);

        this.boardOfPieces[0][1] = new Knight(new Position(0, 1), Color.BLACK, "&#9822;", 0);
        this.boardOfPieces[0][6] = new Knight(new Position(0, 6), Color.BLACK, "&#9822;", 0);
        this.boardOfPieces[7][1] = new Knight(new Position(7, 1), Color.WHITE, "&#9816;", 0);
        this.boardOfPieces[7][6] = new Knight(new Position(7, 6), Color.WHITE, "&#9816;", 0);

        this.boardOfPieces[0][2] = new Bishop(new Position(0, 2), Color.BLACK, "&#9821;", 0);
        this.boardOfPieces[0][5] = new Bishop(new Position(0, 5), Color.BLACK, "&#9821;", 0);
        this.boardOfPieces[7][2] = new Bishop(new Position(7, 2), Color.WHITE, "&#9815;", 0);
        this.boardOfPieces[7][5] = new Bishop(new Position(7, 5), Color.WHITE, "&#9815;", 0);

        this.boardOfPieces[1][0] = new Pawn(new Position(1, 0), Color.BLACK, "&#9823;", 0);
        this.boardOfPieces[1][1] = new Pawn(new Position(1, 1), Color.BLACK, "&#9823;", 0);
        this.boardOfPieces[1][2] = new Pawn(new Position(1, 2), Color.BLACK, "&#9823;", 0);
        this.boardOfPieces[1][3] = new Pawn(new Position(1, 3), Color.BLACK, "&#9823;", 0);
        this.boardOfPieces[1][4] = new Pawn(new Position(1, 4), Color.BLACK, "&#9823;", 0);
        this.boardOfPieces[1][5] = new Pawn(new Position(1, 5), Color.BLACK, "&#9823;", 0);
        this.boardOfPieces[1][6] = new Pawn(new Position(1, 6), Color.BLACK, "&#9823;", 0);
        this.boardOfPieces[1][7] = new Pawn(new Position(1, 7), Color.BLACK, "&#9823;", 0);

        this.boardOfPieces[6][0] = new Pawn(new Position(6, 0), Color.WHITE, "&#9817;", 0);
        this.boardOfPieces[6][1] = new Pawn(new Position(6, 1), Color.WHITE, "&#9817;", 0);
        this.boardOfPieces[6][2] = new Pawn(new Position(6, 2), Color.WHITE, "&#9817;", 0);
        this.boardOfPieces[6][3] = new Pawn(new Position(6, 3), Color.WHITE, "&#9817;", 0);
        this.boardOfPieces[6][4] = new Pawn(new Position(6, 4), Color.WHITE, "&#9817;", 0);
        this.boardOfPieces[6][5] = new Pawn(new Position(6, 5), Color.WHITE, "&#9817;", 0);
        this.boardOfPieces[6][6] = new Pawn(new Position(6, 6), Color.WHITE, "&#9817;", 0);
        this.boardOfPieces[6][7] = new Pawn(new Position(6, 7), Color.WHITE, "&#9817;", 0);
    }

    public void addNewPieceOnTheBoard(String piece, int row, int column, String code, Color color) {
        Piece newPiece = null;
        switch (piece) {
            case "King":
                newPiece = new King(new Position(row, column), color, code, 0);
                break;
            case "Queen":
                newPiece = new Queen(new Position(row, column), color, code, 0);
                break;
            case "Rook":
                newPiece = new Rook(new Position(row, column), color, code, 0);
                break;
            case "Bishop":
                newPiece = new Bishop(new Position(row, column), color, code, 0);
                break;
            case "Knight":
                newPiece = new Knight(new Position(row, column), color, code, 0);
                break;
            case "Pawn":
                newPiece = new Pawn(new Position(row, column), color, code, 0);
                break;
        }
        this.boardOfPieces[row][column] = newPiece;
    }

    public void printBoard() {
        for (int i = 0; i < boardOfPieces.length; i++) {
            for (int j = 0; j < boardOfPieces.length; j++) {
                if (boardOfPieces[i][j] == null) {
                    System.out.print(".  ");
                } else if (boardOfPieces[i][j].getClass() == Rook.class && boardOfPieces[i][j].getColor() != Color.WHITE) {
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
            }
            System.out.println();
        }
    }

    public void printBoardWithCodes() {
        for (int i = 0; i < boardOfPieces.length; i++) {
            for (int j = 0; j < boardOfPieces.length; j++) {
                if (boardOfPieces[i][j] == null) {
                    System.out.print("\t . \t");
                } else {
                    System.out.print(boardOfPieces[i][j].getCode() + "\t");
                }
            }
            System.out.println();
        }
    }

    public HashMap<String, String> getBoardFieldAndCodes() {
        boardFieldAndCodes = new HashMap<>();

        for (int i = 0; i < boardOfPieces.length; i++) {
            for (int j = 0; j < boardOfPieces.length; j++) {
                if (this.boardOfPieces[i][j] == null) {
                    boardFieldAndCodes.put(Field.getFieldByPosition(i, j), "");
                } else {
                    boardFieldAndCodes.put(Field.getFieldByPosition(i, j), boardOfPieces[i][j].getCode());
                }
            }
        }
        return boardFieldAndCodes;
    }
}