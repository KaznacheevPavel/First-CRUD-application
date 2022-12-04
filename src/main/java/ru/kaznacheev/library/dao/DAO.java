package ru.kaznacheev.library.dao;

import java.util.List;

public interface DAO<T> {

    List<T> getAll();
    void add(T object);
    T get(int id);
    void update(T object);
    void delete(int id);

}
