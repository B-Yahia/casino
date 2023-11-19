package entities.operations;

import lombok.Data;

@Data
public class WithdrawalOperation extends Operation {

    public WithdrawalOperation (int amount){
        super(amount);
        setOperationType();
    }

    public void setOperationType(){
        setOperationType("WITHDRAW");
    }
}
