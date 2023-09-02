package dk.jannick.learnjda.managers;

import java.sql.SQLException;
public interface Manager {

    interface SQLiteManager extends Manager {
        DatabaseConnectionManager getDatabase();

        String[] getTables();

        void setTables(String[] tables);

        default void createTables(Runnable runnable) {
            getDatabase().connect(connection -> {
                try {
                    connection.createStatement().executeUpdate(String.join(" ", getTables()));
                    runnable.run();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
