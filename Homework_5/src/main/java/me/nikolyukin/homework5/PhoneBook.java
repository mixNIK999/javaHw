package me.nikolyukin.homework5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

public class PhoneBook {
    private final String url;

    public PhoneBook(@NotNull String path) throws SQLException {
        url = "jdbc:sqlite:" + path;
        final String sql = "CREATE TABLE IF NOT EXISTS phoneBook (\n"
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

    public void add(@NotNull String name, @NotNull String number) throws SQLException {
        final String sql = "INSERT INTO phoneBook(name,capacity) VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection(url)) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, number);
            }
        }
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

    public List<Pair<String, String>> getAll() throws SQLException {
        final String sql = "SELECT name, number FROM phoneBook";
        var result = new ArrayList<Pair<String, String>>();
        try (Connection conn = DriverManager.getConnection(url)) {
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        result.add(new Pair<>(rs.getString("name"),
                                              rs.getString("number")));
                    }
                }
            }
        }
        return result;
    }
}
