package ru.sbt.javaschool.model.interfaces;

import java.util.List;

public interface StudentVisitDao {
    List<Integer> getAllLessonFromVisit();

    List<Integer> showStudentByLessonId(int idLesson);

    List<Integer> showLessonsAttendanceByStudentId(int idStudent);

    double showAttendanceInPercentByLessonId(int isLesson);

    double showAttendanceInPercentByStudent(int idStudent);
}