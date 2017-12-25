package ru.akuna.observer;

/**
 * Created by Los Pepes on 12/25/2017.
 */
public interface Observable<T>
{
    void addObserver(Observer<T> observer);

    void removeObserver(Observer<T> observer);

    void notifyObservers();
}
