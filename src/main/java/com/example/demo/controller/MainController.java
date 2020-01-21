package com.example.demo.controller;

import com.example.demo.communication.field.Field;
import com.example.demo.model.ChessGame;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;

@RestController
public class MainController {

    private ChessGame chessGame = new ChessGame();
    private ArrayList<Position> allPossibleActions;
    private Boolean whitePlayer = true;
    private Color color;
    private Field selectedPiece;

    @RequestMapping(value = {"/selection"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> selection (@RequestParam int player, @RequestParam String fieldId) {

        ArrayList<String> fieldsToMark = new ArrayList<>();
        this.allPossibleActions = new ArrayList<>();

        selectedPiece = Field.getFieldByString(fieldId);
        Position piecePositionThatIsSelected = new Position(selectedPiece.getRow(), selectedPiece.getColumn());
        color = ChessGame.board.getColorFromTheBoardOnCurrentPosition(piecePositionThatIsSelected);

        if (whitePlayer) {
            chessGame.selectWhitePiece(selectedPiece.getRow(), selectedPiece.getColumn());
            if (color == Color.WHITE) {
                whitePlayer = false;
            } else throw new EmptyStackException();
        } else {
            chessGame.selectBlackPiece(selectedPiece.getRow(), selectedPiece.getColumn());
            if (color == Color.BLACK) {
                whitePlayer = true;
            } else throw new EmptyStackException();
        }
        ArrayList<Position> possibleMovesToMake = chessGame.possibleActions.getPossibleMoves();
        ArrayList<Position> possibleCapturesToMake = chessGame.possibleActions.getPossibleCaptures();
        ArrayList<Position> possibleCastling = chessGame.possibleActions.getKingCastlingActions();

        allPossibleActions.addAll(possibleCapturesToMake);
        allPossibleActions.addAll(possibleMovesToMake);
        allPossibleActions.addAll(possibleCastling);

        for (Position position : allPossibleActions) {
            fieldsToMark.add(Field.getFieldByPosition(position.getRow(), position.getColumn()));
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(fieldsToMark);

        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }

    @RequestMapping(value = {"/move"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> move (@RequestParam int player, @RequestParam String fieldId) throws Exception {
        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();

        Position positionWhereWeWantToMove = new Position(Field.getFieldByString(fieldId).getRow(), Field.getFieldByString(fieldId).getColumn());
        if (ChessGame.board.isOccupied(positionWhereWeWantToMove)) {
            if (!chessGame.newPiecePositionByCapture(positionWhereWeWantToMove)) {
                whitePlayer = !whitePlayer;
                throw new EmptyStackException();
            }
        } else if (!chessGame.newPiecePositionByMove(positionWhereWeWantToMove)) {
            whitePlayer = !whitePlayer;
            throw new EmptyStackException();
        }
        ChessGame.board.getBoardFieldAndCodes();
        codeOfTheFieldsWithPiecesOnThem.putAll(ChessGame.board.boardFieldAndCodes);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);
        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }

    @RequestMapping(value = {"/mate"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> isMated (@RequestParam int player) {
        ArrayList<String> isMated = new ArrayList<>();

        if (whitePlayer){
            Boolean isWhiteMated = chessGame.isKingMated(Color.WHITE);
             if (isWhiteMated) {
                 isMated.add("true");
             }else isMated.add("false");
        }else {
            Boolean isBlackMated = chessGame.isKingMated(Color.BLACK);
            if (isBlackMated) {
                isMated.add("true");
            }else isMated.add("false");
        }
        if (ChessGame.isStalameted){
            isMated.add("stalamate");
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(isMated);
        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }

    @RequestMapping(value = {"/actualBoard"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> actualBoard  (@RequestParam int player) throws Exception {
        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();
        ChessGame.board.getBoard();
        ChessGame.board.getBoardFieldAndCodes();
        codeOfTheFieldsWithPiecesOnThem.putAll(ChessGame.board.boardFieldAndCodes);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);
        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }
    @RequestMapping(value = {"/newGame"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<String> newGame  (@RequestParam int player) throws Exception {
        HashMap<String, String> codeOfTheFieldsWithPiecesOnThem = new HashMap<>();
        new ChessGame();
        whitePlayer = true;
        ChessGame.board.getBoard();
        ChessGame.board.getBoardFieldAndCodes();
        codeOfTheFieldsWithPiecesOnThem.putAll(ChessGame.board.boardFieldAndCodes);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(codeOfTheFieldsWithPiecesOnThem);
        return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
    }
}

//        if (fieldsToMark.isEmpty()) {
//                HttpHeaders responseHeaders = new HttpHeaders();
//                responseHeaders.set("Access-Control-Allow-Origin", "*");
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error Message");
//                } else {
//                HttpHeaders responseHeaders = new HttpHeaders();
//                responseHeaders.set("Access-Control-Allow-Origin", "*");
//                String jsonResponse = new Gson().toJson(fieldsToMark);
//
//                return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
//                }
//                }
//        fieldsToMark.add("A7"); fieldsToMark.add("A6"); fieldsToMark.add("A5"); fieldsToMark.add("A4"); fieldsToMark.add("A3"); fieldsToMark.add("A2"); fieldsToMark.add("A1");
//        fieldsToMark.add("B8"); fieldsToMark.add("B7"); fieldsToMark.add("B6"); fieldsToMark.add("B5"); fieldsToMark.add("B4"); fieldsToMark.add("B3"); fieldsToMark.add("B2"); fieldsToMark.add("B1");
//        fieldsToMark.add("C8"); fieldsToMark.add("C7"); fieldsToMark.add("C6"); fieldsToMark.add("C5"); fieldsToMark.add("C4"); fieldsToMark.add("C3"); fieldsToMark.add("C2"); fieldsToMark.add("C1");
//        fieldsToMark.add("D8"); fieldsToMark.add("D7"); fieldsToMark.add("D6"); fieldsToMark.add("D5"); fieldsToMark.add("D4"); fieldsToMark.add("D3"); fieldsToMark.add("D2"); fieldsToMark.add("D1");
//        fieldsToMark.add("E8"); fieldsToMark.add("E7"); fieldsToMark.add("E6"); fieldsToMark.add("E5"); fieldsToMark.add("E4"); fieldsToMark.add("E3"); fieldsToMark.add("E2"); fieldsToMark.add("E1");
//        fieldsToMark.add("F8"); fieldsToMark.add("F7"); fieldsToMark.add("F6"); fieldsToMark.add("F5"); fieldsToMark.add("F4"); fieldsToMark.add("F3"); fieldsToMark.add("F2"); fieldsToMark.add("F1");
//        fieldsToMark.add("G8"); fieldsToMark.add("G7"); fieldsToMark.add("G6"); fieldsToMark.add("G5"); fieldsToMark.add("G4"); fieldsToMark.add("G3"); fieldsToMark.add("G2"); fieldsToMark.add("G1");
//        fieldsToMark.add("H8"); fieldsToMark.add("H7"); fieldsToMark.add("H6"); fieldsToMark.add("H5"); fieldsToMark.add("H4"); fieldsToMark.add("H3"); fieldsToMark.add("H2"); fieldsToMark.add("H1");
