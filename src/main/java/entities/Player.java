package entities;

import entities.operations.BettingOperation;
import lombok.Data;
import entities.operations.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class Player {

    private UUID id ;
    private long balance ;
    private boolean legitimate;
    private int numberOfBets;
    private int numberOfWinningBets;
    private List<Operation> playerOperations;

    public Player(String id){
        this.id = UUID.fromString(id);
        this.legitimate= true;
        this.numberOfBets=0;
        this.numberOfWinningBets=0;
        this.playerOperations=new ArrayList<>();
    }

    public void debitPlayerAccount(int amount){
        balance-=amount;
    }

    public void creditPlayerAccount(int amount){
        balance+=amount;
    }

    public void increaseNumWinningBets(){
        numberOfWinningBets++;
    }

    public void increaseNumberOfBets(){
        numberOfBets++;
    }

    public void addOperation(Operation operation){
        playerOperations.add(operation);
    }

    public Operation getPlayerFirstIllegalGame(){
        if (legitimate){
            return null;
        }else {
            return playerOperations.stream().filter(operation -> !operation.isLegal()).findFirst().get();
        }
    }

    public double getPlayerWinRate (){
        if (numberOfBets!=0){
            BigDecimal rate = new BigDecimal((double) numberOfWinningBets/numberOfBets);
            return rate.setScale(2, RoundingMode.DOWN).doubleValue();
        }
        return 0.00;
    }

    public boolean checkIfPlayerAlreadyBetOnMatchWithID(UUID uuid){
        List<BettingOperation> playerBettingOperations =
                this.playerOperations.stream().filter(operation -> operation.getOperationType().equals("BET"))
                        .map(operation -> (BettingOperation) operation)
                        .collect(Collectors.toList());
        return playerBettingOperations.stream().noneMatch(bettingOperation -> bettingOperation.getMatch().getId().equals(uuid));
    }


}
