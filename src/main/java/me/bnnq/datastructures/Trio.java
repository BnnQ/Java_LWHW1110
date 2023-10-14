package me.bnnq.datastructures;

public class Trio<T> extends Pair<T>
{
    private T third;

    public Trio(T first, T second, T third)
    {
        super(first, second);
        this.third = third;
    }

    public T getThird()
    {
        return third;
    }

    public void setThird(T third)
    {
        this.third = third;
    }

    public Quartet<T> toQuartet(T fourth)
    {
        return new Quartet<>(getFirst(), getSecond(), getThird(), fourth);
    }

    public Pair<T> toPair()
    {
        return new Pair<>(getFirst(), getSecond());
    }

    @Override
    public String toString()
    {
        return String.format("(%s, %s, %s)", getFirst(), getSecond(), getThird());
    }

}
