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
            String[] s = dataRow.split(",");
            matches.add(new Match(s[0],Float.parseFloat(s[1]),Float.parseFloat(s[2]),s[3]));
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
