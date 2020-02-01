package com.wb.demo.chess.model.DataBase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataInTxtFile  {
    String title = "data.txt";

    public DataInTxtFile() {
    }

    public void savingDataFile (ChessDataBase gamesData) throws IOException {
        FileWriter data = null;

        try {
            data = new FileWriter(this.title);
            data.write(gamesData.chessBase.toString());

        } catch (IOException e) {
            System.out.println("Problem");
        } finally {
            if (data == null) {
                System.out.println("Jest problem");
                data.close();
            } else {
                data.close();
            }
        }
    }
    public void printingDataFile () throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(this.title));
            String line = null;
            do {
                line = reader.readLine();
                if (line != null) {
                    System.out.println(line);
                }
            } while (line != null);

        } catch (IOException e) {
            System.out.println("Problem z dostepem do pliku");
        } finally {
            reader.close();
        }
    }
}
