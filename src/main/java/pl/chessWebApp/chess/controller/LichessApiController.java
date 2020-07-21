package pl.chessWebApp.chess.controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.chessWebApp.chess.liveStreamerLichess.StreamersLichess;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

@RestController
public class LichessApiController {

    @GetMapping("/streamer")
    public StreamersLichess[] getTopStreamers() throws IOException {
        URL url = new URL("https://lichess.org/streamer/live");
        InputStreamReader reader = new InputStreamReader(url.openStream());
        StreamersLichess[] allStreamersFromLichess = new Gson().fromJson(reader, StreamersLichess[].class);

        return Arrays.copyOf(allStreamersFromLichess, 10);
    }
}
