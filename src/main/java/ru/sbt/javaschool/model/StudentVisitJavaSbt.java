package ru.sbt.javaschool.model;

import ru.sbt.javaschool.model.interfaces.StudentVisitDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentVisitJavaSbt implements StudentVisitDao {
    private final Connection connection;

    public StudentVisitJavaSbt(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Integer> getAllLessonFromVisit() {
        try {
            Statement statement = connection.createStatement();
            return listIntegers(statement.executeQuery("SELECT DISTINCT LESSON_ID FROM STUDENT_VISITS"));
        } catch (SQLException e) {
            throw new RuntimeException("Error read table Student_visits", e);
        }
    }

    @Override
    public List<Integer> showStudentByLessonId(int idLesson) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT STUDENT_ID FROM STUDENT_VISITS WHERE LESSON_ID = ?");
            statement.setInt(1, idLesson);
            return listIntegers(statement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException("Error read table Student_visits", e);
        }
    }

    @Override
    public List<Integer> showLessonsAttendanceByStudentId(int idStudent) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT LESSON_ID FROM STUDENT_VISITS WHERE STUDENT_ID = ?");
            statement.setInt(1, idStudent);
            return listIntegers(statement.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException("Error read table Student_visits", e);
        }
    }

    @Override
    public double showAttendanceInPercentByLessonId(int isLesson) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT (DISTINCT STUDENT_ID) FROM STUDENT_VISITS");
            int totalStudents = (resultSet.first() ? resultSet.getInt(1) : 0);

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT (LESSON_ID) FROM STUDENT_VISITS WHERE LESSON_ID = ?");
            preparedStatement.setInt(1, isLesson);
            resultSet = preparedStatement.executeQuery();
            int attendanceStudents = (resultSet.first() ? resultSet.getInt(1) : 0);
            if (attendanceStudents == 0.0) return 0.0;
            return (double) attendanceStudents / totalStudents * 100.0;
        } catch (SQLException e) {
            throw new RuntimeException("Error read table Student_visits", e);
        }
    }

    @Override
    public double showAttendanceInPercentByStudent(int idStudent) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT (DISTINCT LESSON_ID) FROM STUDENT_VISITS");
            int totalLessons = (resultSet.first() ? resultSet.getInt(1) : 0);

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT (STUDENT_ID) FROM STUDENT_VISITS WHERE STUDENT_ID = ?");
            preparedStatement.setInt(1, idStudent);
            resultSet = preparedStatement.executeQuery();
            int attendanceLessons = (resultSet.first() ? resultSet.getInt(1) : 0);
            if (attendanceLessons == 0.0) return 0.0;
            return (double) attendanceLessons / totalLessons * 100.0;
        } catch (SQLException e) {
            throw new RuntimeException("Error read table Student_visits", e);
        }
    }

    private List<Integer> listIntegers(ResultSet resultSet) {
        try {
            List<Integer> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getInt(1));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}