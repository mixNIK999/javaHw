package me.nikolyukin.homework5;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhoneBookTest {
    PhoneBook emptyPhoneBook;

    @BeforeEach
    void init()
        throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
//        Class.forName("com.mysql.jdbc.Driver").newInstance();
        emptyPhoneBook = new PhoneBook(DriverManager.getConnection("jdbc:sqlite::memory:"));
    }

    @AfterEach
    void close() throws Exception {
        emptyPhoneBook.close();
    }

    @Test
    void getAllFromEmpty() throws SQLException {
        assertEquals(0, emptyPhoneBook.getAll().size());
    }

    @Test
    void addOne() throws SQLException {
        emptyPhoneBook.add("foo", "8 800 555 35 35");
        var res = emptyPhoneBook.getAll();
        assertEquals(1, res.size());
        assertEquals("foo", res.get(0).getFirst());
        assertEquals("8 800 555 35 35", res.get(0).getSecond());
    }
}