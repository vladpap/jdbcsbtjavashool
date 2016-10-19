package ru.sbt.javaschool;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.sbt.javaschool.model.*;
import ru.sbt.javaschool.model.interfaces.LessonDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LessonTest {
    private Connection connection;

    @Before
    public void createConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:~/H2/db", "admin", "password");
        JavaSchool javaSchool = new JavaSchool();
        javaSchool.deleteTable(connection);
        javaSchool.createTable(connection, true);
    }

    @Test
    public void testLessons() {
        LessonDao lessonJavaSbt = new LessonJavaSbt(connection);
        List<Lesson> lessons = lessonJavaSbt.lessons();
        assertEquals(7, lessons.size());
    }

    @Test
    public void testFindLessonById() {
        LessonDao lessonJavaSbt = new LessonJavaSbt(connection);
        Lesson temp = lessonJavaSbt.lessonFromId(7);
        assertEquals("09.09.2016", temp.getDateToString());
    }

    @Test
    public void testFindLessonsByListId() {
        LessonDao lessonsJavaSbt = new LessonJavaSbt(connection);
        List<Lesson> lessons = lessonsJavaSbt.lessonsFromListId(new ArrayList<Integer>(Arrays.asList(1, 3, 6)));
        assertEquals(3, lessons.size());
        assertEquals("20.08.2016", lessons.get(0).getDateToString());
        assertEquals("27.08.2016", lessons.get(1).getDateToString());
        assertEquals("06.09.2016", lessons.get(2).getDateToString());
    }

    @Test
    public void testFindLessonByDate() throws ParseException {
        LessonDao lessonsJavaSbt = new LessonJavaSbt(connection);
        Lesson lesson = lessonsJavaSbt.lessonFromDate(new SimpleDateFormat("dd/MM/yyyy").parse("30/08/2016"));
        assertEquals("Generics", lesson.getSubject());
    }

    @Test
    public void testFindLessonsByDateRange() {
        LessonDao lessonJavaSbt = new LessonJavaSbt(connection);
        List<Lesson> lessons = lessonJavaSbt.lessonFromRangeDate(new RangeDate(
                RangeDate.dateFromString("28/08/2016"), RangeDate.dateFromString("02/09/2016")));
        assertEquals(2, lessons.size());
        assertEquals("Generics", lessons.get(0).getSubject());
    }

    @Test
    public void testUpdateAndAddLesson() {
        LessonDao lessonJavaSbt = new LessonJavaSbt(connection);
        lessonJavaSbt.updateLesson(new Lesson(0, "Java Script", RangeDate.dateFromString("12/09/2016")));
        List<Lesson> lessons = lessonJavaSbt.lessons();
        assertEquals(8, lessons.size());
        lessonJavaSbt.updateLesson(new Lesson(lessons.get(7).getId(), "Module. Build tools. Testing", lessons.get(7).getDate()));
        Lesson temp = lessonJavaSbt.lessonFromId(lessons.get(7).getId());
        assertEquals("Module. Build tools. Testing", temp.getSubject());
        assertEquals("12.09.2016", temp.getDateToString());
    }

    @After
    public void closeConnection() throws SQLException {
        connection.close();
    }
}