package entities.operations;

import entities.Match;
import entities.MatchResult;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BettingOperation extends Operation{
    private Match match;
    private MatchResult playerSelectedSide;
    private BettingResult bettingResult;
    private int gain;


    public BettingOperation (int amount , Match match, MatchResult playerSelectedSide){
        super(amount);
        this.match = match;
        this.playerSelectedSide = playerSelectedSide;
        this.bettingResult= getFinalBettingResult();
        calculateGain();
        setOperationType();
    }
    private void setOperationType(){
        setOperationType("BET");
    }

    private BettingResult getFinalBettingResult(){
        if (match.getFinalResult().equals(MatchResult.DRAW)){
            return BettingResult.NOT_COUNTED;
        }else if(match.getFinalResult().equals(playerSelectedSide)){
            return BettingResult.WON;
        }else {
            return BettingResult.LOST;
        }
    }

    private void calculateGain(){
        if (bettingResult.equals(BettingResult.WON)){
            if (playerSelectedSide.equals(MatchResult.A)){
                gain= (int) (getAmount()*match.getSideAValue());
            }else {
                gain= (int) (getAmount()*match.getSideBValue());
            }
        }else {
            gain=0;
        }
    }

}
