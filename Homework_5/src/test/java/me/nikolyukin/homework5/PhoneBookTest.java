package me.nikolyukin.homework5;

import static org.junit.jupiter.api.Assertions.*;

import com.intellij.openapi.util.Pair;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhoneBookTest {
    private PhoneBook emptyPhoneBook;
    private PhoneBook fullPhoneBook;

    @BeforeEach
    void init() throws SQLException {
        emptyPhoneBook = new PhoneBook(DriverManager.getConnection("jdbc:sqlite::memory:"));
        fullPhoneBook = new PhoneBook(DriverManager.getConnection("jdbc:sqlite::memory:"));
        fullPhoneBook.add("foo", "1");
        fullPhoneBook.add("foo", "2");
        fullPhoneBook.add("bar", "2");
        fullPhoneBook.add("bar", "3");
        fullPhoneBook.add("baz", "3");
        fullPhoneBook.add("baz", "1");
    }

    @AfterEach
    void close() throws Exception {
        emptyPhoneBook.close();
        fullPhoneBook.close();
    }

    @Test
    void getAllFromEmpty() throws SQLException {
        assertEquals(0, emptyPhoneBook.getAll().size());
    }

    @Test
    void getAllFromFull() throws SQLException {
        var fullList = fullPhoneBook.getAll();
        assertEquals(6, fullList.size());
        assertTrue(fullList.contains(new Pair<>("bar", "2")));
        assertTrue(fullList.contains(new Pair<>("bar", "3")));
        assertFalse(fullList.contains(new Pair<>("bar", "1")));
    }

    @Test
    void addTwo() throws SQLException {
        emptyPhoneBook.add("foo", "8 800 555 35 35");
        var res = emptyPhoneBook.getAll();
        assertEquals(1, res.size());
        assertEquals("foo", res.get(0).getFirst());
        assertEquals("8 800 555 35 35", res.get(0).getSecond());
        emptyPhoneBook.add("bar", "123456789");
        assertTrue(emptyPhoneBook.getAll().contains(new Pair<>("bar", "123456789")));
    }


    @Test
    void findNamesFromEmpty() throws SQLException {
        assertTrue(emptyPhoneBook.findNames("123").isEmpty());
    }

    @Test
    void findNamesFromFull() throws SQLException {
        var namesList = fullPhoneBook.findNames("1");
        assertEquals(2, namesList.size());
        assertTrue(namesList.contains("foo"));
        assertFalse(namesList.contains("bar"));
        assertTrue(namesList.contains("baz"));
    }

    @Test
    void findNumbersFromEmpty() throws SQLException {
        assertTrue(emptyPhoneBook.findNumbers("foo").isEmpty());
    }

    @Test
    void findNumbersFromFull() throws SQLException {
        var numbersList = fullPhoneBook.findNumbers("bar");
        assertEquals(2, numbersList.size());
        assertFalse(numbersList.contains("1"));
        assertTrue(numbersList.contains("2"));
        assertTrue(numbersList.contains("3"));
    }

    @Test
    void deleteFromFull() throws SQLException {
        fullPhoneBook.delete("foo", "1");
        assertEquals(5, fullPhoneBook.getAll().size());
        fullPhoneBook.delete("foo", "2");
        assertEquals(4, fullPhoneBook.getAll().size());
        fullPhoneBook.delete("bar", "2");
        assertEquals(3, fullPhoneBook.getAll().size());
    }

    @Test
    void changeNumberFromFull() throws SQLException {
        fullPhoneBook.changeNumber("foo", "1", "3");
        var fullList = fullPhoneBook.getAll();
        assertTrue(fullList.contains(new Pair<>("foo", "3")));
        assertFalse(fullList.contains(new Pair<>("foo", "1")));
    }

    @Test
    void changeName() throws SQLException {
        fullPhoneBook.changeName("baz", "3", "foo");
        var fullList = fullPhoneBook.getAll();
        assertTrue(fullList.contains(new Pair<>("foo", "3")));
        assertFalse(fullList.contains(new Pair<>("baz", "3")));
    }
}