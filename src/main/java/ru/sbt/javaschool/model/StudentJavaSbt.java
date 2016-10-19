package ru.sbt.javaschool.model;

import ru.sbt.javaschool.model.interfaces.StudentDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentJavaSbt implements StudentDao {
    private final Connection connection;

    public StudentJavaSbt(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Student studentById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM STUDENTS WHERE ID_STUDENT = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                return new Student(resultSet.getInt("id_student"), resultSet.getString("first_name"), resultSet.getString("last_name"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error read database Students.", e);
        }
    }

    @Override
    public List<Student> findByListId(List<Integer> list) {
        String sqlQuery = "SELECT * FROM STUDENTS WHERE ID_STUDENT IN (";
        for (int i : list) {
            sqlQuery += (i + ",");
        }
        sqlQuery = sqlQuery.substring(0, sqlQuery.length() - 1) + ")";
        try {
            return listStudents(connection.createStatement().executeQuery(sqlQuery));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> findByName(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM STUDENTS WHERE FIRST_NAME = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return listStudents(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> students() {
        try {
            return listStudents(connection.createStatement().executeQuery("SELECT * FROM STUDENTS"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Student> listStudents(ResultSet resultSet) {
        try {
            List<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_student");
                String fn = resultSet.getString("first_name");
                String ln = resultSet.getString("last_name");
                studentList.add(new Student(id, fn, ln));
            }
            return studentList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Student student) {
        String sqlInsert = "INSERT INTO STUDENTS (FIRST_NAME, LAST_NAME) VALUES (?, ?)";
        String sqlUpdate = "UPDATE STUDENTS SET FIRST_NAME=?, LAST_NAME=? WHERE ID_STUDENT=?";
        try {
            PreparedStatement statement = connection.prepareStatement((student.getId() > 0) ? sqlUpdate : sqlInsert);
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            if (student.getId() > 0) {
                statement.setInt(3, student.getId());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error save students table", e);
        }
    }

}