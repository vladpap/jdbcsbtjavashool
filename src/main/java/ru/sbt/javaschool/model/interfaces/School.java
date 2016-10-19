package ru.sbt.javaschool.model.interfaces;

import java.sql.Connection;

public interface School {
    void createTable(Connection connection, boolean defaultField);

    void deleteTable(Connection connection);
}