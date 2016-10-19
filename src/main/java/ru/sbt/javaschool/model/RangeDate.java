package ru.sbt.javaschool.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RangeDate {
    private final Date fromDate;
    private final Date toDate;

    public RangeDate(Date fromDate, Date toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }


    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public static Date dateFromString(String s) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(s);
        } catch (ParseException e) {
            throw new RuntimeException("Error parse date. Use format \'dd/MM/yyyy\'");
        }
    }
}