
import entities.Match;
import entities.Player;
import service.Casino;
import service.LocalFileManager;
import service.Mapper;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LocalFileManager fileManager = new LocalFileManager();
        Mapper mapper = new Mapper();
        List<String> matchesData = new ArrayList<>();
        List<String> playersData = new ArrayList<>();

        /// read matches data from the file and copy it in List<String>
        try {
            matchesData =fileManager.fileReader("match_data.txt");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        // read players data from the file and copy it in List<String>
        try {
            playersData =fileManager.fileReader("player_data.txt");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        //extract players entities
        List<Player> players = mapper.extractPlayersEntities(playersData);
        //extract matches entities
        List<Match> matches = mapper.extractMatchesEntities(matchesData);

        Casino casino = Casino.getInstance(players);
        List<String> results =casino.processOperations(playersData,matches);

        //add condition to not bet on the match twice
        var mainClassPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();


        try {
            fileManager.fileWriter("Result.txt",results,mainClassPath);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        for (String s: results
        ) {
            System.out.println(s);
        }








    }

}
