package models.Types;

import models.Values.IntValue;
import models.Values.Value;

public class IntType implements Type {
    public boolean equals(Object another){
        return another instanceof IntType;
    }
    public String toString(){
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }
}
