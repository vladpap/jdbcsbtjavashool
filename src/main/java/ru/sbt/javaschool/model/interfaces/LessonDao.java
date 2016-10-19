package ru.sbt.javaschool.model.interfaces;

import ru.sbt.javaschool.model.Lesson;
import ru.sbt.javaschool.model.RangeDate;

import java.util.Date;
import java.util.List;

public interface LessonDao {
    List<Lesson> lessons();
    Lesson lessonFromId(int id);
    List<Lesson> lessonsFromListId(List<Integer> list);
    Lesson lessonFromDate(Date date);
    List<Lesson> lessonFromRangeDate(RangeDate rangeDate);
    void updateLesson(Lesson lesson);
}