package models.Types;

import models.Values.StringValue;
import models.Values.Value;

public class StringType implements Type{
    public boolean equals(Object another){
        return another instanceof StringType;
    }
    public String toString(){
        return "string";
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
}
