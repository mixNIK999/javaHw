package me.nikolyukin.homework5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

public class PhoneBook {
    private final String url;

    public PhoneBook(@NotNull String path) throws SQLException {
        url = "jdbc:sqlite:" + path;
        String sql = "CREATE TABLE IF NOT EXISTS phoneBook (\n"
            + "	id integer PRIMARY KEY,\n"
            + "	name text NOT NULL,\n"
            + "	number text NOT NULL\n"
            + ");";
        try (Connection conn = DriverManager.getConnection(url)) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
            }
        }
    }

    public void add(@NotNull String name, @NotNull String number) {

    }

    public ArrayList<String> findNames(@NotNull String number) {
        return null;
    }

    public ArrayList<String> findNubers(@NotNull String name) {
        return null;
    }

    public void delete(@NotNull String name, @NotNull String number) {}

    public void changeNumber(@NotNull String name, @NotNull String number) {}

    public void changeName(@NotNull String name, @NotNull String number) {}

    public ArrayList<Pair<String, String>>  getAll() {
        return null;
    }
}
