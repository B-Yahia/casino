package entities.operations;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Operation {
    private int amount;
    private boolean legal;
    private String operationType;


    public Operation (int amount ){
        this.amount=amount;
        this.legal=true;
    }
}
