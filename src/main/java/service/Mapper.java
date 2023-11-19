package service;

import entities.Match;
import entities.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Mapper {

    public List<Match> extractMatchesEntities(List<String> MatchesFileData){
        List<Match> matches = new ArrayList<>();
        for (String dataRow:MatchesFileData){
            String[] matchData = dataRow.split(",");
            if (matchData.length == 4) {
                matches.add(new Match(matchData[0], Float.parseFloat(matchData[1]),
                        Float.parseFloat(matchData[2]), matchData[3]));
            } else {
                throw new RuntimeException("Invalid match data format");
            }
        }
        return matches;
    }

    public List<Player> extractPlayersEntities (List<String> playersData){
        List<Player> players = new ArrayList<>();
        for (String dataRow : playersData) {
            String [] s = dataRow.split(",");
            if (players.stream().noneMatch(player -> player.getId().equals(UUID.fromString(s[0])))) {
                players.add(new Player(s[0]));
            };
        }
        return players;
    }



}
