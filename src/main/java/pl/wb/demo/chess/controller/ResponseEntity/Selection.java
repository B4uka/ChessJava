package pl.wb.demo.chess.controller.ResponseEntity;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wb.demo.chess.communication.field.Field;
import pl.wb.demo.chess.model.ChessGame;
import pl.wb.demo.chess.model.piece_properties.Color;
import pl.wb.demo.chess.model.piece_properties.Position;

import java.util.ArrayList;

@Slf4j
public class Selection {
    private ChessGame chessGame;

    public Selection (ChessGame chessGame){
        this.chessGame = chessGame;
    }

    public ResponseEntity<String> select(int player, String fieldId) {
        log.info("processing " + fieldId);

        ArrayList<String> fieldsToMark = new ArrayList<>();
        ArrayList<Position> allPossibleActions = new ArrayList<>();
        Field selectedPiece = Field.valueOf(fieldId);
        Color selectedPieceColor = chessGame.getBoard().getColorFromTheBoardOnCurrentPosition(new Position(selectedPiece.getRow(), selectedPiece.getColumn()));

        if (!chessGame.selectPiece(selectedPiece.getRow(), selectedPiece.getColumn(), selectedPieceColor)) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Access-Control-Allow-Origin", "*");
            return ResponseEntity
                    .status(403)
                    .headers(responseHeaders)
                    .body("It is not your move or you already lost!");
        }
        ArrayList<Position> possibleMovesToMake = chessGame.getPossibleActions().getPossibleMoves();
        ArrayList<Position> possibleCapturesToMake = chessGame.getPossibleActions().getPossibleCaptures();
        ArrayList<Position> possibleCastling = chessGame.getPossibleActions().getKingCastlingActions();

        allPossibleActions.addAll(possibleCapturesToMake);
        allPossibleActions.addAll(possibleMovesToMake);
        allPossibleActions.addAll(possibleCastling);

        for (Position position : allPossibleActions) {
            fieldsToMark.add(Field.getFieldByPosition(position.getRow(), position.getColumn()));
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String jsonResponse = new Gson().toJson(fieldsToMark);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(jsonResponse);
    }
}
