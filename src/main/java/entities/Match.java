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
        if(finalResult.equals("DRAW")){
            return MatchResult.DRAW;
        }else if (finalResult.equals("A")){
            return MatchResult.A;
        }else if (finalResult.equals("B")){
            return MatchResult.B;
        }else {
            throw new RuntimeException(" The match final result value is invalid");
        }
    }
}
