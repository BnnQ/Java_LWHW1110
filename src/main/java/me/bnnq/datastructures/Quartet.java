package me.bnnq.datastructures;

public class Quartet<T> extends Trio<T>
{
    private T fourth;

    public Quartet(T first, T second, T third, T fourth)
    {
        super(first, second, third);
        this.fourth = fourth;
    }


    public T getFourth()
    {
        return fourth;
    }

    public void setFourth(T fourth)
    {
        this.fourth = fourth;
    }

    public Trio<T> toTrio()
    {
        return new Trio<>(getFirst(), getSecond(), getThird());
    }

    @Override
    public String toString()
    {
        return String.format("(%s, %s, %s, %s)", getFirst(), getSecond(), getThird(), getFourth());
    }

}
