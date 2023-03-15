package models.Values;

import models.Types.BoolType;
import models.Types.Type;

public class BoolValue implements Value {
    boolean value;

    public BoolValue(boolean v){
        value = v;
    }

    public boolean getValue(){
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return "" + value;

    }
}
