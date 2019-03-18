package me.nikolyukin.homework5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

/**
 * Класс предоставляет доступ к записи и редактированию телефонной записной книжки
 * в базе данных посредством java.sql.Connection
 */
public class PhoneBook implements AutoCloseable {
    private Connection connection;

    /**
     * @param connection соединение с базой данных, NotNull
     * @throws SQLException если не удалось создать Statement
     */
    public PhoneBook(@NotNull Connection connection) throws SQLException {
        final String sql = "CREATE TABLE IF NOT EXISTS phoneBook (\n"
            + "	id integer PRIMARY KEY,\n"
            + "	name text NOT NULL,\n"
            + "	number text NOT NULL\n"
            + ");";
        this.connection = connection;

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }

    }

    /**
     * Добавляет запись (имя и телефон) в базу данных.
     *
     * @param name имя владельца телефона, NotNull
     * @param number номер телефона, NotNull
     * @throws SQLException если не удалось создать PreparedStatement
     */
    public void add(@NotNull String name, @NotNull String number) throws SQLException {
        final String sql = "INSERT INTO phoneBook(name,number) VALUES(?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, number);
            preparedStatement.executeUpdate();
        }

    }

    /**
     * Возвращает список содержащий имена всех владельцев данного номера.
     *
     * @param number номер, владельцев которого нужно вернуть, NotNull
     * @return Список всех владельцев номера
     * @throws SQLException если не удалось создать PreparedStatement
     */
    public ArrayList<String> findNames(@NotNull String number) throws SQLException {
        final String sql = "SELECT name, number FROM phoneBook WHERE number = ?";
        var result = new ArrayList<String>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, number);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString("name"));
                }
            }
        }
        return result;
    }

    /**
     * Возвращает список содержащий все номера данного владельца.
     *
     * @param name человек, номера которого мнужно вернуть, NotNull
     * @return все номера данного владельца
     * @throws SQLException если не удалось создать PreparedStatement
     */
    public ArrayList<String> findNumbers(@NotNull String name) throws SQLException {
        final String sql = "SELECT name, number FROM phoneBook WHERE name = ?";
        var result = new ArrayList<String>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString("number"));
                }
            }
        }
        return result;
    }

    /**
     * Удаляет заданную пару имя-телефон.
     * Если такой пары нет, ничего не делает.
     *
     * @param name имя владельца, NotNull
     * @param number номер телефона, NotNull
     * @throws SQLException если не удалось создать PreparedStatement
     */
    public void delete(@NotNull String name, @NotNull String number) throws SQLException {
        final String sql = "DELETE FROM phoneBook WHERE name = ? AND number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, number);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Меняет имя в указанной пары "имя-телефон"
     *
     * @param name владелец, которого нужно заметить, NotNull
     * @param number номер, владельца которого нужно заменить, NotNull
     * @param newNumber новый владелец, NotNull
     * @throws SQLException если не удалось создать PreparedStatement
     */
    public void changeNumber(@NotNull String name,
            @NotNull String number,
            @NotNull String newNumber) throws SQLException {

        final String sql = "UPDATE phoneBook SET number = ? WHERE name = ? AND number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newNumber);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, number);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Меняет номер телефона в указанной пары "имя-телефон"
     * @param name владелец, номер которого нужно заменить, NotNull
     * @param number номер, который нужно заменить, NotNull
     * @param newName новый номер, NotNull
     * @throws SQLException если не удалось создать PreparedStatement
     */
    public void changeName(@NotNull String name,
        @NotNull String number,
        @NotNull String newName) throws SQLException {

        final String sql = "UPDATE phoneBook SET name = ? WHERE name = ? AND number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, number);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Возвращает все пары имя-телефон в справочнике
     * @return все пары имя-телефон
     * @throws SQLException если не удалось создать Statement
     */
    public List<Pair<String, String>> getAll() throws SQLException {
        final String sql = "SELECT name, number FROM phoneBook";
        var result = new ArrayList<Pair<String, String>>();

        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    result.add(new Pair<>(rs.getString("name"),
                                          rs.getString("number")));
                }
            }
        }
        return result;
    }

    /**
     * Закрывает переданный на вход Connection
     */
    @Override
    public void close() throws Exception {
        connection.close();
    }
}
