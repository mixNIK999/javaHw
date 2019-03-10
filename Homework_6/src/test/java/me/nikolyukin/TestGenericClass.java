package me.nikolyukin;

public class TestGenericClass<T> {
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

    protected void PlusInt(int n) {
        fieldInt += n;
    }

    private void inc() {
        fieldInt++;
    }
}
