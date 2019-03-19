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
        final String sqlCreate = "CREATE TABLE IF NOT EXISTS Names (\n"
            + "Id INTEGER NOT NULL PRIMARY KEY,\n"
            + "Name TEXT NOT NULL"
            + ");\n"

            + "CREATE TABLE IF NOT EXISTS Numbers (\n"
            + "Id INTEGER NOT NULL PRIMARY KEY,\n"
            + "Number TEXT NOT NULL"
            + ");\n"

            + "CREATE TABLE IF NOT EXISTS NameNumber (\n"
            + "NameId INTEGER NOT NULL,\n"
            + "NumberId INTEGER NOT NULL,\n"
            + "FOREIGN KEY (NameId) REFERENCES Names(Id)\n"
            + "FOREIGN KEY (NumberId) REFERENCES Numbers(Id)\n"
            + ");";

        this.connection = connection;

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlCreate);
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
        final String sqlInsertName = "INSERT INTO Names(Name)"
            + "SELECT ? WHERE NOT EXISTS (SELECT * FROM Names WHERE Name = ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertName)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        }

        final String sqlInsertNumber = "INSERT INTO Numbers(Number)"
            + "SELECT ? WHERE NOT EXISTS (SELECT * FROM Numbers WHERE Number = ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertNumber)) {
            preparedStatement.setString(1, number);
            preparedStatement.setString(2, number);
            preparedStatement.executeUpdate();
        }

        final String sqlInsertRelation = "INSERT INTO NameNumber(NameId, NumberId)\n"
            + "SELECT Names.Id, Numbers.Id\n"
            + "FROM Names, Numbers\n"
            + "WHERE Names.Name = ? AND Numbers.Number = ?\n";
//            + "AND NOT EXISTS (SELECT * FROM NameNumber WHERE NameId = Names.Id AND NumberId = Numbers.Id);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertRelation)) {
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
        final String sqlTakeNames = "SELECT Names.Name"
            + "FROM Names, NameNumber, Numbers"
            + "WHERE Names.Id = NameNumber.NameId AND NameNumber.NumberId = Numbers.Id"
            + "AND Numbers.Number = ?";

        var result = new ArrayList<String>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlTakeNames)) {
            preparedStatement.setString(1, number);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString("Name"));
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
        final String sqlTakeNumbers = "SELECT Numbers.Number"
            + "FROM Names, NameNumber, Numbers"
            + "WHERE Names.Id = NameNumber.NameId AND NameNumber.NumberId = Numbers.Id"
            + "AND Names.name = ?";

        var result = new ArrayList<String>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlTakeNumbers)) {
            preparedStatement.setString(1, name);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString("Number"));
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
        final String sqlDeleteRelation = "DELETE ab\n"
            + "FROM Names AS a, NameNumber AS ab, Numbers AS b\n"
            + "WHERE a.Id = ab.NameId AND ab.NumberId = b.Id\n"
            + "AND a.Name = ? AND b.Number = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDeleteRelation)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, number);
            preparedStatement.executeUpdate();
        }

        final String sqlClearNames = "DELETE\n"
            + "FROM Names\n"
            + "WHERE Id NOT IN (SELECT NameId FROM NameNumber);";

        final String sqlClearNumbers = "DELETE FROM Numbers\n"
            + "WHERE Id NOT IN (SELECT NumbersId FROM NameNumber);";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlClearNames);
            stmt.executeUpdate(sqlClearNumbers);
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

        delete(name, number);
        add(name, newNumber);
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

        delete(name, number);
        add(newName, number);
    }

    /**
     * Возвращает все пары имя-телефон в справочнике
     * @return все пары имя-телефон
     * @throws SQLException если не удалось создать Statement
     */
    public List<Pair<String, String>> getAll() throws SQLException {
        final String sqlGetAll = "SELECT a.Name, b.Number\n"
            + "FROM Names AS a, NameNumber AS ab, Numbers AS b\n"
            + "WHERE a.Id = ab.NameId AND ab.NumberId = b.Id\n;";
        var result = new ArrayList<Pair<String, String>>();

        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(sqlGetAll)) {
                while (rs.next()) {
                    result.add(new Pair<>(rs.getString("name"),
                                          rs.getString("number")));
                }
            }
        }
        return result;
    }

    private void drop() throws SQLException {
        final String sqlDropAll = "DROP TABLE IF EXISTS Names; DROP TABLE IF EXISTS NameNumber; DROP TABLE IF EXISTS Numbers";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlDropAll);
        }
    }

    /**
     * Закрывает переданный на вход Connection
     */
    @Override
    public void close() throws Exception {
        connection.close();
    }
}
