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
    public void setOperationType(){
        setOperationType("BET");
    }

    private BettingResult getFinalBettingResult(){
        if (this.match.getFinalResult().equals(MatchResult.DRAW)){
            return BettingResult.NOT_COUNTED;
        }else if(this.match.getFinalResult().equals(this.playerSelectedSide)){
            return BettingResult.WON;
        }else {
            return BettingResult.LOST;
        }
    }

    public void calculateGain(){
        if (bettingResult.equals(BettingResult.WON)){
            if (playerSelectedSide.equals(MatchResult.A)){
                this.gain= (int) (getAmount()*match.getSideAValue());
            }else {
                this.gain= (int) (getAmount()*match.getSideBValue());
            }
        }else {
            this.gain=0;
        }
    }

}
