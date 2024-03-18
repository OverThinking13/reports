package app.entities;

import java.util.Calendar;
import java.util.Objects;

public class Petition implements Comparable<Petition>{
    private final int mspId;
    private final String mspName;
    private final int categoryId;
    private final String categoryName;
    private final String link;
    private final Calendar date;
    private int days;
    private final Calendar dateDone;
    private final Calendar finalDate;
    public Calendar getDateDone() {
        return dateDone;
    }

    public Calendar getFinalDate() {
        return finalDate;
    }

    public Petition(int mspId, String mspName, int categoryId, String categoryName, String link, Calendar date, Calendar dateDone) {
        this.mspId = mspId;
        this.mspName = mspName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.link = link;
        this.date = date;
        this.finalDate= (Calendar) date.clone();
        this.dateDone = dateDone;
    }


    public void setDays(int days) {
        this.days = days;
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

    public String getLink() {
        return link;
    }

    public Calendar getDate() {
        return date;
    }

    public int getDays() {
        return days;
    }

    @Override
    public String toString() {
        return "Petition{" +
                "msp_id=" + mspId +
                ", msp_name='" + mspName + '\'' +
                ", category_id=" + categoryId +
                ", category_name='" + categoryName + '\'' +
                ", link='" + link + '\'' +
                ", date=" + date.getTime()+
                ", days=" + days +
                ", finalDate=" + finalDate.getTime() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Petition petition = (Petition) o;
        return link == petition.link;
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }

    @Override
    public int compareTo(Petition o) {
        return Integer.compare(this.days,o.days);
    }
}