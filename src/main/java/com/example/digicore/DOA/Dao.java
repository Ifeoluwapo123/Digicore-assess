package com.example.digicore.DOA;

import java.util.Optional;

public interface Dao<T,M> {
    Optional<T> find(int index);
    M findAll();
    T save(T t);
    void delete(T t);
}
