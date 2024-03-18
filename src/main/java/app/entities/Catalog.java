package app.entities;

import java.util.*;

public class Catalog implements Comparable<Catalog> {
    private final int mspId;
    private final String mspName;
    private final int categoryId;
    private final String categoryName;
    private boolean isCalendar;
    private int days;

    public Catalog(int mspId, String mspName, int categoryId, String categoryName, boolean isCalendar, int days) {
        this.mspId = mspId;
        this.mspName = mspName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.isCalendar = isCalendar;
        this.days = days;
    }

    public Catalog(int mspId, String mspName, int categoryId, String categoryName) {
        this.mspId = mspId;
        this.mspName = mspName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public void setCalendar(boolean calendar) {
        isCalendar = calendar;
    }

    public void setDays(int days) {
        this.days = Math.max(days, 0);
    }

    public int getMspId() {
        return mspId;
    }

    public String getMspName() {
        return mspName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public boolean isCalendar() {
        return isCalendar;
    }

    public int getDays() {
        return days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return getMspId() == catalog.getMspId() && getCategoryId() == catalog.getCategoryId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMspId(), getCategoryId());
    }

    @Override
    public int compareTo(Catalog o) {
        int result = this.getMspId() - o.getMspId();
        if (result == 0)
            return this.getCategoryId() - o.getCategoryId();
        else
            return result;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s;%s", mspId, mspName, categoryId, categoryName, isCalendar, days);
    }


}
