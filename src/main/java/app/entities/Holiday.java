package app.entities;

import java.util.Objects;

public class Holiday implements Comparable<Holiday> {

    private final int day;
    private final int month;
    private final int year;


    public Holiday(int year, int month, int day) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s",year,month,day);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holiday holiday = (Holiday) o;
        return day == holiday.day && month == holiday.month && year == holiday.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    @Override
    public int compareTo(Holiday o) {
        int result = this.getYear() - o.getYear();

        if (result == 0)
            result = this.getMonth() - o.getMonth();
        else
            return result;

        if (result == 0)
            result = this.getDay() - o.getDay();

        return result;
    }
}
