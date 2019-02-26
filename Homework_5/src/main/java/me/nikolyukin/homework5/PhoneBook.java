package me.nikolyukin.homework5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

public class PhoneBook {
    private String url = "jdbc:sqlite:phoneBook.db";

    public void add(@NotNull String name, @NotNull String number) {}

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
