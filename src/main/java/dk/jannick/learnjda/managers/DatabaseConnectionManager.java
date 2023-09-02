package dk.jannick.learnjda.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class DatabaseConnectionManager {
    private final String connectionString;
    private final Lock lock = new ReentrantLock(true);
    private Connection connection;

    public DatabaseConnectionManager() {
        this.connectionString = "jdbc:sqlite:LearnJDA.db";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void connect(Consumer<Connection> callback) {
        asyncFuture(() -> {
            if (connection == null) {
                try {
                    connection = DriverManager.getConnection(connectionString);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            callback.accept(connection);
        });
    }

    public void syncConnect(Consumer<Connection> callback) {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(connectionString);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        callback.accept(connection);
    }

    public void close() throws SQLException {
        connection.close();
    }

    private CompletableFuture<Void> asyncFuture(Runnable runnable) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {
            lock.lock();
            try {
                runnable.run();
            } finally {
                lock.unlock();
            }
            future.complete(null);
        });
        return future;
    }
}
