package entities;

import entities.operations.BettingOperation;
import lombok.Data;
import entities.operations.Operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class Player {

    private UUID id ;
    private double balance ;
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
        if (amount>this.balance){
            System.out.println("this operation can not be made");
        }
        this.balance-=amount;
    }

    public void creditPlayerAccount(int amount){
        this.balance+=amount;
    }

    public void increaseNumWiningBets(){
        this.numberOfWinningBets++;
    }

    public void increaseNumberOfBets(){
        this.numberOfBets++;
    }

    public void addOperation(Operation operation){
        this.playerOperations.add(operation);
    }

    public Operation getPlayerFirstIllegalGame(){
        if (legitimate){
            return null;
        }else {
            return playerOperations.stream().filter(operation -> !operation.isLegal()).findFirst().get();
        }
    }

    public double getPlayerWinRate (){
        if (numberOfBets!=0 && numberOfWinningBets!=0 ){
            double winPercentage = (this.numberOfWinningBets*100)/numberOfBets;
            return winPercentage/100;
        }
        return 0;
    }

    public boolean checkIfPlayerAlreadyBetOnMatchWithID(UUID uuid){
        List<BettingOperation> playerBettingOperations =
                this.playerOperations.stream().filter(operation -> operation.getOperationType().equals("BET"))
                        .map(operation -> (BettingOperation) operation)
                        .collect(Collectors.toList());
        return playerBettingOperations.stream().noneMatch(bettingOperation -> bettingOperation.getMatch().getId().equals(uuid));
    }


}
