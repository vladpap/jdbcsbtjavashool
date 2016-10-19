package ru.sbt.javaschool;

import ru.sbt.javaschool.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/H2/db", "admin", "password")) {
            JavaSchool javaSchool = new JavaSchool();
            javaSchool.deleteTable(connection);
            javaSchool.createTable(connection, true);


            StudentVisitJavaSbt studentVisitJavaSbt = new StudentVisitJavaSbt(connection);

            List<Lesson> lessonList = new LessonJavaSbt(connection).lessonsFromListId(studentVisitJavaSbt.getAllLessonFromVisit());
            System.out.print("        Name        |");
            for (Lesson lesson : lessonList) {
                System.out.printf(" %5s |", lesson.getShortDateToString());
            }
            System.out.print("    %   |");

            List<Student> studentList = new StudentJavaSbt(connection).students();
            for (Student student : studentList) {
                System.out.printf("\n%19s |", student.toString());
                List<Integer> tempStudentVisitLesson = studentVisitJavaSbt.showLessonsAttendanceByStudentId(student.getId());
                for (Lesson lesson : lessonList) {
                    System.out.print((tempStudentVisitLesson.contains(lesson.getId())) ? "   V   |" : "   -   |");
                }
                System.out.printf(" %6.2f |", studentVisitJavaSbt.showAttendanceInPercentByStudent(student.getId()));
            }
            printLine();
            System.out.print("                  % |");
            for (Lesson lesson : lessonList) {
                System.out.printf("%7.2f|", studentVisitJavaSbt.showAttendanceInPercentByLessonId(lesson.getId()));
            }
            printLine();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printLine() {
        System.out.println("\n--------------------------------------------------------------------------------------");
    }

}