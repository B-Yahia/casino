package entities;

import lombok.Data;

import java.util.UUID;

@Data
public class Match {
    private UUID id ;
    private float sideAValue;
    private float sideBValue;
    private MatchResult finalResult;
    public Match (String id , float A , float B, String finalResult ){
        this.id = UUID.fromString(id);
        this.sideAValue= A;
        this.sideBValue=B;
        this.finalResult = convertStringToMatchResult(finalResult);
    }

    private MatchResult convertStringToMatchResult(String finalResult) throws RuntimeException{
        switch (finalResult){
            case "DRAW" -> {return MatchResult.DRAW;}
            case "A" -> {return MatchResult.A;}
            case "B" -> {return MatchResult.B;}
            default -> throw new RuntimeException(" The match final result value is invalid");
        }
    }
}
