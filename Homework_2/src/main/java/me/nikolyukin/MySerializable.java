package me.nikolyukin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  Объекты классов, реализующих MySerializable, можно записать и считать из потока.
 */
public interface MySerializable {
    /**
     * Выводит объект в поток.
     *
     * @param out поток вывода
     * @throws IOException бросается при неудачной попытке записи
     */
    void serialize(OutputStream out) throws IOException;

    /**
     * Чтения объекта из потока. При этом старый объект заменяется новым.
     *
     * @param in поток ввода
     * @throws IOException бросается при неудачной попытке чтения
     */
    void deserialize(InputStream in) throws IOException;
}
