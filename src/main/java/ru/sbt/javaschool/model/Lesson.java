package ru.sbt.javaschool.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Lesson {
    private final int id;
    private final String subject;
    private final Date date;

    public Lesson(int id, String subject, Date date) {
        this.id = id;
        this.subject = subject;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return getDateToString() + " - \'" + subject + "\'";
    }

    public String getDateToString() {
        return getDateToString("dd.MM.yyyy");
    }

    public String getShortDateToString() {
        return getDateToString("dd.MM");
    }

    public String getDateToString(String format) {
        return new SimpleDateFormat(format).format(date);
    }
}