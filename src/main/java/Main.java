
import entities.Match;
import entities.Player;
import service.Casino;
import service.LocalFileManager;
import service.Mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LocalFileManager fileManager = new LocalFileManager();
        Mapper mapper = new Mapper();

        List<String> matchesData = readFile("match_data.txt", fileManager);
        List<String> playersData = readFile("player_data.txt", fileManager);

        List<Player> players = mapper.extractPlayersEntities(playersData);
        List<Match> matches = mapper.extractMatchesEntities(matchesData);

        Casino casino = Casino.getInstance(players);
        List<String> results = casino.processOperations(playersData, matches);

        String mainClassPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        writeFile("Result.txt", results, mainClassPath, fileManager);
//        System.out.println(mainClassPath);
        //the file Result will be in the path : pathToProjectOnYourComputer/PlaytechInternTask/target/classes/

    }

    private static List<String> readFile(String fileName, LocalFileManager fileManager) {
        try {
            return fileManager.fileReader(fileName);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static void writeFile(String fileName, List<String> data, String path, LocalFileManager fileManager) {
        try {
            fileManager.fileWriter(fileName, data, path);
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
