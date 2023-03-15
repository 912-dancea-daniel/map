package models.Values;

import models.Types.StringType;
import models.Types.Type;

public class StringValue implements Value{
    String value;

    public StringValue(String v){
        value = v;
    }

    public String getValue(){
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "" + value;

    }
}
