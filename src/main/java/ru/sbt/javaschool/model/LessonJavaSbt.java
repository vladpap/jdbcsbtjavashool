package ru.sbt.javaschool.model;

import ru.sbt.javaschool.model.interfaces.LessonDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LessonJavaSbt implements LessonDao {
    private final Connection connection;

    public LessonJavaSbt(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Lesson> lessons() {
        try {
            return listLessons(connection.createStatement().executeQuery("SELECT * FROM LESSONS"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Lesson lessonFromId(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM LESSONS WHERE ID_LESSON = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                return new Lesson(resultSet.getInt("id_lesson"), resultSet.getString("subject"), resultSet.getDate("date"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error read database Lesson.", e);
        }
    }

    @Override
    public List<Lesson> lessonsFromListId(List<Integer> list) {
        String sqlQuery = "SELECT * FROM LESSONS WHERE ID_LESSON IN (";
        for (int i : list) {
            sqlQuery += (i + ",");
        }
        sqlQuery = sqlQuery.substring(0, sqlQuery.length() - 1) + ")";
        try {
            return listLessons(connection.createStatement().executeQuery(sqlQuery));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Lesson lessonFromDate(Date date) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM LESSONS WHERE DATE = ?");
            statement.setTimestamp(1, new Timestamp(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                return new Lesson(resultSet.getInt("id_lesson"), resultSet.getString("subject"), resultSet.getDate("date"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error read database Lesson.", e);
        }
    }

    @Override
    public List<Lesson> lessonFromRangeDate(RangeDate rangeDate) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM LESSONS WHERE DATE BETWEEN ? AND ?");
            statement.setTimestamp(1, new Timestamp(rangeDate.getFromDate().getTime()));
            statement.setTimestamp(2, new Timestamp(rangeDate.getToDate().getTime()));
            return listLessons(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateLesson(Lesson lesson) {
        String sqlInsert = "INSERT INTO LESSONS (SUBJECT, DATE) VALUES (?, ?)";
        String sqlUpdate = "UPDATE LESSONS SET SUBJECT=?, DATE=? WHERE ID_LESSON=?";
        try {
            PreparedStatement statement = connection.prepareStatement((lesson.getId() > 0) ? sqlUpdate : sqlInsert);
            statement.setString(1, lesson.getSubject());
            statement.setTimestamp(2, new Timestamp(lesson.getDate().getTime()));
            if (lesson.getId() > 0) {
                statement.setInt(3, lesson.getId());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error save students table", e);
        }
    }

    private List<Lesson> listLessons(ResultSet resultSet) {
        try {
            List<Lesson> lessonsList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_lesson");
                String s = resultSet.getString("subject");
                Date d = resultSet.getDate("date");
                lessonsList.add(new Lesson(id, s, d));
            }
            return lessonsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}