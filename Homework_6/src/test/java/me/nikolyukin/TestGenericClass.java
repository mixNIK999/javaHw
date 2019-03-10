package me.nikolyukin;

import java.util.List;

public class TestGenericClass<T extends Object> {
    private T fieldT;
    int fieldInt;

    public TestGenericClass() {
        this(null, 0);
    }

    public TestGenericClass(T fieldT, int fieldInt) {
        this.fieldT = fieldT;
        this.fieldInt = fieldInt;
    }

    public T get(){
        return fieldT;
    }
    public void set(T newF) {
        fieldT = newF;
    }
    final <T, E extends Object> void extObject(T a, E b, List<? extends E> c, List<? extends Object> d) {}

    protected void PlusInt(int n) {
        fieldInt += n;
    }

    private void inc() {
        fieldInt++;
    }
}
