package models.Values;

import models.Types.RefType;
import models.Types.Type;

public class RefValue implements Value{
    int address;
    Type locationType;

    @Override
    public String toString() {
        return "(" + address + "," + locationType + ")";
    }

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    public int getAddress() {
        return address;
    }
}
