package com.openxc.units;

import com.google.common.base.Objects;

public class State<T extends Enum<?>> implements Unit {
    private T mValue;

    public State(T value) {
        mValue = value;
    }

    public boolean equalTo(T otherValue) {
        // TODO  this is incorrect - need to check cast and look at mValue
        return mValue.equals(otherValue);
    }

    public T enumValue() {
        return mValue;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("value", mValue)
            .toString();
    }
}
