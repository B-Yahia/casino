package service;

import entities.Match;
import entities.MatchResult;
import entities.Player;
import entities.operations.BettingOperation;
import entities.operations.DepositOperation;
import entities.operations.WithdrawalOperation;

import java.util.*;


public final class Casino {
    private static Casino INSTANCE;
    private long balance ;
    private List<Player> players;

    private Casino(List<Player> players){
        this.balance = 0;
        this.players=players;

    }
    public static Casino getInstance(List<Player> players){
        if (INSTANCE==null){
            INSTANCE= new Casino(players);
        }
        return INSTANCE;
    }

    public void creditCasinoBalance( int creditAmount){
        this.balance+= creditAmount;
    }

    public void debitCasinoBalance( int debitAmount){
        this.balance-= debitAmount;
    }

    public List<String> processOperations(List<String> playersData, List<Match> matches){
        for (String playerData: playersData) {
            int amount= 0;
            String [] str = playerData.split(",");
            var playerInRole = players.stream().filter(player -> player.getId().equals(UUID.fromString(str[0]))).findFirst().get();
            if (str[1].equals("DEPOSIT")) {
                processPlayerDepositOperation(str, playerInRole);

            }else if (str[1].equals("WITHDRAW")){
                processPlayerWithdrawOperation(str, playerInRole);
            }else if (str[1].equals("BET")){
                processPlayerBettingOperation(matches, str, playerInRole);
            }
        }
        return getFinalResultsInStringList();
    }

    private List<String> getFinalResultsInStringList() {
        List<String> results = new ArrayList<>();
        for(Player p : players){
            if (p.isLegitimate()){
                results.add(p.getId()+" "+p.getBalance()+" "+p.getPlayerWinRate());
            }else {
                getResultForIllegalPlayers(results, p);
            }
        }
        results.add(String.valueOf(this.balance));
        return results;
    }

    private void getResultForIllegalPlayers(List<String> results, Player p) {
        if (p.getPlayerFirstIllegalGame().getOperationType().equals("BET")){
            BettingOperation bettingOperation = (BettingOperation) p.getPlayerFirstIllegalGame();
            results.add(p.getId()+" "+bettingOperation.getOperationType()+" "
                    +bettingOperation.getMatch().getId()+" "+ p.getBalance()+" "+bettingOperation.getPlayerSelectedSide().toString());
        }else{
            results.add(p.getId()+" "+ p.getPlayerFirstIllegalGame().getOperationType()+" null "
                    + p.getBalance()+" null");
        }
    }

    private void processPlayerBettingOperation(List<Match> matches, String[] str, Player playerInRole) {
        int amount= Integer.parseInt(str[3]);
        Match match = getTheMatchForBetting(str[2], matches);
        BettingOperation bettingOperation = new BettingOperation(amount,match,getPlayerSelectedSide(str[4]));
        if (playerInRole.checkIfPlayerAlreadyBetOnMatchWithID(bettingOperation.getMatch().getId())){
            if (checkProcessLegality(playerInRole,bettingOperation,amount)){
                playerInRole.increaseNumberOfBets();
                playerInRole.debitPlayerAccount(amount);
                creditCasinoBalance(amount);
                switch (bettingOperation.getBettingResult()){
                    case WON -> {
                        playerInRole.increaseNumWiningBets();
                        debitCasinoBalance(amount);
                        playerInRole.creditPlayerAccount(amount);
                        debitCasinoBalance(bettingOperation.getGain());
                        playerInRole.creditPlayerAccount(bettingOperation.getGain());
                    }
                    case NOT_COUNTED -> {
                        debitCasinoBalance(amount);
                        playerInRole.creditPlayerAccount(amount);
                    }
                }
            }
        }else {
            System.out.println("You already bet on the same match");
        }

        playerInRole.addOperation(bettingOperation);
    }

    private boolean checkProcessLegality(Player player, BettingOperation operation, int amount){
        if (!player.isLegitimate()){
            operation.setLegal(false);
            return false;
        }
        if (amount>player.getBalance()){
            player.setLegitimate(false);
            operation.setLegal(false);
            return false;
        }
        return true;
    }

    private void processPlayerWithdrawOperation(String[] str, Player playerInRole) {
        int amount=Integer.parseInt(str[3]);
        WithdrawalOperation withdrawalOperation = new WithdrawalOperation(amount);
        if (playerInRole.isLegitimate()){
            if (amount> playerInRole.getBalance()){
                playerInRole.setLegitimate(false);
                withdrawalOperation.setLegal(false);
            }else {
                playerInRole.debitPlayerAccount(amount);
            }
        }else {
            withdrawalOperation.setLegal(false);
        }
        playerInRole.addOperation(withdrawalOperation);
    }

    private void processPlayerDepositOperation(String[] str, Player playerInRole) {
        int amount =Integer.parseInt(str[3]);
        DepositOperation depositOperation = new DepositOperation(amount);
        if (playerInRole.isLegitimate()){
            playerInRole.creditPlayerAccount(amount);
        }else {
            depositOperation.setLegal(false);
        }
        playerInRole.addOperation(depositOperation);
    }

    public Match getTheMatchForBetting(String id,List<Match> matches){
        return matches.stream()
                .filter(match -> match.getId().equals(UUID.fromString(id)))
                .findFirst().get();
    }

    public MatchResult getPlayerSelectedSide(String side){
        if (side.equals("A")){
            return MatchResult.A;
        }else if (side.equals("B")){
            return MatchResult.B;
        }else {
            throw new RuntimeException("Player selected option for the bet is not valid,Player can only select A or B");
        }
    }
}
