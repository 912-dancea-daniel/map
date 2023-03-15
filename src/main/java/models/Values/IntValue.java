package models.Values;

import models.Types.IntType;
import models.Types.Type;

public class IntValue implements Value {

    int val;

    public IntValue(int v){
        val = v;
    }

    public int getVal(){
        return val;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "" + val;
    }
}
