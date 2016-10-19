package ru.sbt.javaschool;

import org.junit.Test;
import ru.sbt.javaschool.model.JavaSchool;
import ru.sbt.javaschool.model.Student;
import ru.sbt.javaschool.model.StudentJavaSbt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class CreateAndDeleteTest {
    @Test
    public void testCreateAndDeleteTable() {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/H2/db", "admin", "password")) {
            JavaSchool javaSchool = new JavaSchool();
            javaSchool.deleteTable(connection);
            javaSchool.createTable(connection, true);

            StudentJavaSbt studentJavaSbt = new StudentJavaSbt(connection);
            List<Student> students = studentJavaSbt.students();
            assertEquals(5, students.size());
            assertEquals(1, students.get(0).getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}