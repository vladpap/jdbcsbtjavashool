package ru.sbt.javaschool;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.sbt.javaschool.model.JavaSchool;
import ru.sbt.javaschool.model.Student;
import ru.sbt.javaschool.model.StudentJavaSbt;
import ru.sbt.javaschool.model.interfaces.StudentDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StudentTest {
    private Connection connection;

    @Before
    public void createConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:~/H2/db", "admin", "password");
        JavaSchool javaSchool = new JavaSchool();
        javaSchool.deleteTable(connection);
        javaSchool.createTable(connection, true);
    }

    @Test
    public void testAllStudent() {
        StudentDao studentJavaSbt = new StudentJavaSbt(connection);
        List<Student> students = studentJavaSbt.students();
        assertEquals(5, students.size());
    }

    @Test
    public void testAddStudent() {
        StudentDao studentJavaSbt = new StudentJavaSbt(connection);
        studentJavaSbt.update(new Student(0, "Vladimir", "Kozlov"));
        List<Student> students = studentJavaSbt.students();
        assertEquals(6, students.size());
    }

    @Test
    public void testFindStudentByName() {
        StudentDao studentJavaSbt = new StudentJavaSbt(connection);
        studentJavaSbt.update(new Student(0, "Vladimir", "Kozlov"));
        List<Student> students = studentJavaSbt.findByName("Vladimir");
        assertEquals(2, students.size());
    }

    @Test
    public void testFindStudentById() {
        StudentDao studentJavaSbt = new StudentJavaSbt(connection);
        Student temp = studentJavaSbt.studentById(3);
        assertEquals("Petr Orlov", temp.toString());
    }

    @Test
    public void testFindStudentByListId() {
        StudentDao studentJavaSbt = new StudentJavaSbt(connection);
        List<Student> students = studentJavaSbt.findByListId(new ArrayList<Integer>(Arrays.asList(1, 3, 5)));
        assertEquals(3, students.size());
        assertEquals("Vladimir Papin", students.get(0).toString());
        assertEquals("Petr Orlov", students.get(1).toString());
        assertEquals("Sveta Romanova", students.get(2).toString());
    }

    @Test
    public void testUpdateStudent() {
        StudentDao studentJavaSbt = new StudentJavaSbt(connection);
        studentJavaSbt.update(new Student(3, "Vladislav", "Orlov"));
        Student temp = studentJavaSbt.studentById(3);
        assertEquals("Vladislav Orlov", temp.toString());
    }

    @After
    public void closeConnection() throws SQLException {
        connection.close();
    }
}