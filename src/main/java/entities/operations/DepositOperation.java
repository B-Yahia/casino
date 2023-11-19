package entities.operations;

import lombok.Data;

@Data
public class DepositOperation extends Operation {

    public DepositOperation (int amount){
        super(amount);
        setOperationType();
    }

    public void setOperationType(){
        setOperationType("DEPOSIT");
    }
}
