package ru.akuna.observer;

/**
 * Created by Los Pepes on 12/25/2017.
 */
public interface Observer<T>
{
    void update(T object);
}
