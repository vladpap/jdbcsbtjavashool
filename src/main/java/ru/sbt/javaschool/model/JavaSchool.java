package ru.sbt.javaschool.model;

import ru.sbt.javaschool.model.interfaces.School;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JavaSchool implements School {
    @Override
    public void createTable(Connection connection, boolean defaultField) {
        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS Students " +
                    "(" +
                    "id_student INT AUTO_INCREMENT," +
                    "first_name VARCHAR (255)," +
                    "last_name VARCHAR (255)," +
                    "PRIMARY KEY (id_student)" +
                    ");");

            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS Lessons " +
                    "(" +
                    "id_lesson INT AUTO_INCREMENT," +
                    "subject VARCHAR (255)," +
                    "date DATE," +
                    "PRIMARY KEY (id_lesson)" +
                    ");");

            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS Student_visits " +
                    "(" +
                    "id INT AUTO_INCREMENT," +
                    "student_id INT NOT NULL," +
                    "lesson_id INT NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (student_id) REFERENCES Students (id_student)," +
                    "FOREIGN KEY (lesson_id) REFERENCES Lessons (id_lesson)" +
                    ");");

            if (defaultField) {
                connection.createStatement().executeUpdate("INSERT INTO Students (first_name, last_name) " +
                        "VALUES ('Vladimir', 'Papin')");
                connection.createStatement().executeUpdate("INSERT INTO Students (first_name, last_name) " +
                        "VALUES ('Ivan', 'Sidorov')");
                connection.createStatement().executeUpdate("INSERT INTO Students (first_name, last_name) " +
                        "VALUES ('Petr', 'Orlov')");
                connection.createStatement().executeUpdate("INSERT INTO Students (first_name, last_name) " +
                        "VALUES ('Oleg', 'Smirnov')");
                connection.createStatement().executeUpdate("INSERT INTO Students (first_name, last_name) " +
                        "VALUES ('Sveta', 'Romanova')");


                saveDefaulFieldForLesson(connection, "Java introduction", "20/08/2016");
                saveDefaulFieldForLesson(connection, "Main Java classes and packages", "23/08/2016");
                saveDefaulFieldForLesson(connection, "Java collection framework", "27/08/2016");
                saveDefaulFieldForLesson(connection, "Generics", "30/08/2016");
                saveDefaulFieldForLesson(connection, "Exception handling", "02/09/2016");
                saveDefaulFieldForLesson(connection, "Reflection, Annotations, Proxy", "06/09/2016");
                saveDefaulFieldForLesson(connection, "ClassLoaders", "09/09/2016");
                for (int i = 1; i <= 5; i++) {
                    for (int j = 1; j <= 7; j++) {
                        if (j % i == 0) {
                            saveDefaultFoStudentVizit(connection, i, j);
                        }
                    }
                }
            }
        } catch (SQLException | ParseException e) {
            throw new RuntimeException("Error create table.", e);
        }
    }

    private void saveDefaultFoStudentVizit(Connection connection, int i, int j) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO STUDENT_VISITS (STUDENT_ID, LESSON_ID) VALUES (?, ?)");
        statement.setInt(1, i);
        statement.setInt(2, j);
        statement.executeUpdate();
    }

    @Override
    public void deleteTable(Connection connection) {
        try {
            connection.createStatement().executeUpdate("DROP TABLE STUDENT_VISITS");
            connection.createStatement().executeUpdate("DROP TABLE LESSONS");
            connection.createStatement().executeUpdate("DROP TABLE STUDENTS");
        } catch (SQLException e) {
            System.out.println("Error delete table, lets go ...");
        }
    }

    private void saveDefaulFieldForLesson(Connection connection, String subject, String date) throws SQLException, ParseException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Lessons (subject, date) VALUES (?, ?)");
        statement.setString(1, subject);
        statement.setTimestamp(2, getTimestampFromString(date));
        statement.executeUpdate();
    }

    private Timestamp getTimestampFromString(String s) throws ParseException {
        return new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(s).getTime());
    }
}