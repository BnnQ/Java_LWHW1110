package me.bnnq.datastructures;

public class Pair<T>
{
    private T first;
    private T second;

    public Pair(T first, T second)
    {
        this.first = first;
        this.second = second;
    }

    public T getFirst()
    {
        return first;
    }

    public T getSecond()
    {
        return second;
    }

    public void setFirst(T first)
    {
        this.first = first;
    }

    public void setSecond(T second)
    {
        this.second = second;
    }

    public Trio<T> toTrio(T third)
    {
        return new Trio<>(getFirst(), getSecond(), third);
    }

    @Override
    public String toString()
    {
        return String.format("(%s, %s)", getFirst(), getSecond());
    }
}
