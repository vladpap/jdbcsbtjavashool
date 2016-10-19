package ru.sbt.javaschool;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.sbt.javaschool.model.*;
import ru.sbt.javaschool.model.interfaces.StudentVisitDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StudentVisitTest {
    private Connection connection;
    private static final double DELTA = 1e-2;

    @Before
    public void createConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:~/H2/db", "admin", "password");
        JavaSchool javaSchool = new JavaSchool();
        javaSchool.deleteTable(connection);
        javaSchool.createTable(connection, true);
    }

    @Test
    public void testAllLessonFromVisit() {
        StudentVisitDao studentVisitJavaSbt = new StudentVisitJavaSbt(connection);
        List<Integer> list = studentVisitJavaSbt.getAllLessonFromVisit();
        assertEquals(7, list.size());
    }

    @Test
    public void testShowStudentThatVisitLecture() {
        StudentVisitDao studentVisitJavaSbt = new StudentVisitJavaSbt(connection);
        List<Integer> list = studentVisitJavaSbt.showStudentByLessonId(4);
        assertEquals(3, list.size());
    }

    @Test
    public void testShowLessonThatVisitStudent() {
        StudentVisitDao studentVisitJavaSbt = new StudentVisitJavaSbt(connection);
        List<Integer> list = studentVisitJavaSbt.showLessonsAttendanceByStudentId(1);
        assertEquals(7, list.size());
    }

    @Test
    public void testAttendanceInPercentByStudent() {
        StudentVisitDao studentVisitJavaSbt = new StudentVisitJavaSbt(connection);
        double attendance = studentVisitJavaSbt.showAttendanceInPercentByStudent(1);
        assertEquals(100.0, attendance, DELTA);
        attendance = studentVisitJavaSbt.showAttendanceInPercentByStudent(2);
        assertEquals(42.85, attendance, DELTA);
        attendance = studentVisitJavaSbt.showAttendanceInPercentByStudent(3);
        assertEquals(28.57, attendance, DELTA);
    }

    @Test
    public void testAttendanceInPercentByLesson() {
        StudentVisitDao studentVisitJavaSbt = new StudentVisitJavaSbt(connection);
        double attendance = studentVisitJavaSbt.showAttendanceInPercentByLessonId(1);
        assertEquals(20.0, attendance, DELTA);
        attendance = studentVisitJavaSbt.showAttendanceInPercentByLessonId(4);
        assertEquals(60.0, attendance, DELTA);

    }

    @After
    public void closeConnection() throws SQLException {
        connection.close();
    }
}