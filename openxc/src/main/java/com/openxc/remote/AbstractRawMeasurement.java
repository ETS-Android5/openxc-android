package com.openxc.remote;

import com.google.common.base.Objects;

import android.os.Parcel;

public abstract class AbstractRawMeasurement<TheUnit> {
    protected TheUnit mValue;

    public AbstractRawMeasurement() { }

    public AbstractRawMeasurement(TheUnit value) {
        mValue = value;
    }

    public boolean isValid() {
        return mValue != null;
    }

    public int describeContents() {
        return 0;
    }

    public TheUnit getValue() {
        return mValue;
    }

    public void setValue(TheUnit value) {
        mValue = value;
    }

    public abstract void writeToParcel(Parcel out, int flags);

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("value", getValue())
            .add("valid", isValid())
            .toString();
    }
}
