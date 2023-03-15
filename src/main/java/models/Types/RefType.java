package models.Types;

import models.Values.RefValue;
import models.Values.Value;

public class RefType implements Type{
    Type inner;

    @Override
    public String toString() {
        return "Ref " + inner;
    }

    public Type getInner() {
        return inner;
    }

    public RefType() {
        this.inner = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefType)
            return (inner.equals(((RefType) obj).getInner()) || inner == null || ((RefType)obj).getInner() == null);
        else
            return false;
    }

    public RefType(Type inner) {
        this.inner = inner;
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, inner);
    }
}
