package me.nikolyukin;

public class TestGenericClass<T> {
    private T fieldT;
    int fieldInt;

    public T get(){
        return fieldT;
    }
    protected void PlusInt(int n) {
        fieldInt += n;
    }

    private void inc() {
        fieldInt++;
    }
}
