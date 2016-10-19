package ru.sbt.javaschool.model.interfaces;

import ru.sbt.javaschool.model.Student;

import java.util.List;

public interface StudentDao {
    List<Student> students();
    List<Student> findByName(String name);
    Student studentById(int id);
    List<Student> findByListId(List<Integer> list);
    void update(Student student);
}