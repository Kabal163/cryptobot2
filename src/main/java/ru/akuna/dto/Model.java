package ru.akuna.dto;

import ru.akuna.observer.Observable;
import ru.akuna.observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Los Pepes on 12/25/2017.
 */
public abstract class Model<T> implements Observable<T>
{
    private List<Observer<T>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<T> observer)
    {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<T> observer)
    {
        observers.remove(observer);
    }

    protected List<Observer<T>> getObservers()
    {
        return observers;
    }
}
