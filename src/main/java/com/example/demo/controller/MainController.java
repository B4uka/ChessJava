package com.example.demo.controller;

import com.example.demo.communication.field.Field;
import com.example.demo.model.ChessGame;
import com.example.demo.model.piece_properties.Color;
import com.example.demo.model.piece_properties.Position;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
        color = chessGame.board.getColorFromTheBoardOnCurrentPosition(piecePositionThatIsSelected);

        if (whitePlayer) {
            chessGame.selectWhitePiece(selectedPiece.getRow(), selectedPiece.getColumn());
            if (color == Color.WHITE) {
                whitePlayer = false;
            }else return new ResponseEntity(new Error(), HttpStatus.BAD_REQUEST);
        } else {
            chessGame.selectBlackPiece(selectedPiece.getRow(), selectedPiece.getColumn());
            if (color == Color.BLACK) {
                whitePlayer = true;
            }else return new ResponseEntity(new Error(), HttpStatus.BAD_REQUEST);
        }
        ArrayList<Position> possibleMovesToMake = chessGame.possibleActions.getPossibleMoves();
        ArrayList<Position> possibleCapturesToMake = chessGame.possibleActions.getPossibleCaptures();

        allPossibleActions.addAll(possibleCapturesToMake);
        allPossibleActions.addAll(possibleMovesToMake);

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
        ArrayList<String> fieldsToMark = new ArrayList<>();

        for (Position position : this.allPossibleActions) {
            this.chessGame.possibleActions.addPossibleMove(position);
        }

        this.chessGame.newPiecePositionByCapture(new Position(Field.getFieldByString(fieldId).getRow(), Field.getFieldByString(fieldId).getColumn()));
        this.chessGame.newPiecePositionByMove(new Position(Field.getFieldByString(fieldId).getRow(), Field.getFieldByString(fieldId).getColumn()));

        fieldsToMark.add(Field.getFieldByPosition(this.chessGame.piecePositionNEW.getRow(), this.chessGame.piecePositionNEW.getColumn()));
        if (!fieldsToMark.contains(fieldId)) {
            whitePlayer = !whitePlayer;
            return new ResponseEntity(new Error(), HttpStatus.BAD_REQUEST);
        } else {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Access-Control-Allow-Origin", "*");
            String jsonResponse = new Gson().toJson(fieldsToMark);
            return ResponseEntity.ok().headers(responseHeaders).body(jsonResponse);
        }
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