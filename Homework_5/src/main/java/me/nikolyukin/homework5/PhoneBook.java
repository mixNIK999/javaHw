package me.nikolyukin.homework5;

import java.io.Closeable;
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

public class PhoneBook implements AutoCloseable{
    private Connection connection;

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

    public void add(@NotNull String name, @NotNull String number) throws SQLException {
        final String sql = "INSERT INTO phoneBook(name,number) VALUES(?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
        }

    }

    public ArrayList<String> findNames(@NotNull String number) throws SQLException {
        final String sql = "SELECT name, number FROM phoneBook WHERE number = ?";
        var result = new ArrayList<String>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, number);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString("name"));
                }
            }
        }
        return result;
    }

    public ArrayList<String> findNumbers(@NotNull String name) throws SQLException {
        final String sql = "SELECT name, number FROM phoneBook WHERE name = ?";
        var result = new ArrayList<String>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString("number"));
                }
            }
        }
        return result;
    }

    public void delete(@NotNull String name, @NotNull String number) throws SQLException {
        final String sql = "DELETE FROM phoneBook WHERE name = ? AND number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
        }
    }

    public void changeNumber(@NotNull String name,
            @NotNull String number,
            @NotNull String newNumber) throws SQLException {

        final String sql = "UPDATE phoneBook SET number = ? WHERE name = ? AND number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newNumber);
            pstmt.setString(2, name);
            pstmt.setString(3, number);
            pstmt.executeUpdate();
        }
    }

    public void changeName(@NotNull String name,
        @NotNull String number,
        @NotNull String newName) throws SQLException {

        final String sql = "UPDATE phoneBook SET name = ? WHERE name = ? AND number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, name);
            pstmt.setString(3, number);
            pstmt.executeUpdate();
        }
    }

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
     * Closes this resource, relinquishing any underlying resources. This method is invoked
     * automatically on objects managed by the {@code try}-with-resources statement.
     *
     * <p>While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to declare concrete implementations
     * of the {@code close} method to throw more specific exceptions, or to throw no exception at
     * all if the close operation cannot fail.
     *
     * <p> Cases where the close operation may fail require careful
     * attention by implementers. It is strongly advised to relinquish the underlying resources and
     * to internally <em>mark</em> the resource as closed, prior to throwing the exception. The
     * {@code close} method is unlikely to be invoked more than once and so this ensures that the
     * resources are released in a timely manner. Furthermore it reduces problems that could arise
     * when the resource wraps, or is wrapped, by another resource.
     *
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link InterruptedException}.</em>
     *
     * This exception interacts with a thread's interrupted status, and runtime misbehavior is
     * likely to occur if an {@code InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     *
     * More generally, if it would cause problems for an exception to be suppressed, the {@code
     * AutoCloseable.close} method should not throw it.
     *
     * <p>Note that unlike the {@link Closeable#close close}
     * method of {@link Closeable}, this {@code close} method is <em>not</em> required to be
     * idempotent.  In other words, calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is required to have no effect if
     * called more than once.
     *
     * However, implementers of this interface are strongly encouraged to make their {@code close}
     * methods idempotent.
     *
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws Exception {
        connection.close();
    }
}
