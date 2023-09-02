package dk.jannick.learnjda.managers;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class TicketManager extends SQLiteManager {
    Map<Long, String> ticketMap = new HashMap<>();
    public TicketManager(DatabaseConnectionManager databaseConnectionManager) {
        super(databaseConnectionManager);
        setTables(
                new String[]{
                        "CREATE TABLE IF NOT EXISTS tickets (\n" +
                                "  discord_id INTEGER PRIMARY KEY NOT NULL, \n" +
                                "  channel_id INTEGER NOT NULL\n" +
                                ");"}
        );
        createTables(() -> databaseConnectionManager.connect(connection -> {
            try {
                ResultSet resultSet = connection.prepareStatement("SELECT * FROM tickets;").executeQuery();
                int count = 0;
                while (resultSet.next()) {
                    count++;
                    Long discordId = resultSet.getLong("discord_id");
                    String channelId = resultSet.getString("channel_id");
                    ticketMap.put(discordId, channelId);
                }
                System.out.println("Loaded " + count + " tickets from database");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

    public Map<Long, String> getTicketMap() {
        return this.ticketMap;
    }

    public String getTicket(Long discordId) {
        return this.ticketMap.get(discordId);
    }
    public Long getTicket(String channelId) {
        for (Long discordId : this.ticketMap.keySet()) {
            if (this.ticketMap.get(discordId).equals(channelId)) {
                return discordId;
            }
        }
        return null;
    }

    public void addTicket(Long discordId, String channelId) {
        this.ticketMap.put(discordId, channelId);
        saveTicket(discordId, channelId) ;
    }

    private void saveTicket(Long discordId, String channelId) {
        getDatabase().connect(connection -> {
            try {
                connection.prepareStatement("INSERT INTO tickets (discord_id, channel_id) VALUES ('" + discordId + "', '" + channelId + "');").executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void removeTicket(Long discordId) {
        this.ticketMap.remove(discordId);
        deleteTicket(discordId);
    }

    public void removeTicket(String channelId) {
        for (Long discordId : this.ticketMap.keySet()) {
            if (this.ticketMap.get(discordId).equals(channelId)) {
                this.ticketMap.remove(discordId);
                deleteTicket(discordId);
                return;
            }
        }
    }

    private void deleteTicket(Long discordId) {
        getDatabase().connect(connection -> {
            try {
                connection.prepareStatement("DELETE FROM tickets WHERE discord_id = '" + discordId + "';").executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public boolean hasTicket(Long discordId) {
        return this.ticketMap.containsKey(discordId);
    }

    public boolean hasTicket(String channelId) {
        return this.ticketMap.containsValue(channelId);
    }

}
