package dk.jannick.learnjda.utils;

public class FiveMUtils {
    private static Integer clients;
    private static String gametype;
    private static String hostname;
    private static String mapname;

    private static Integer maxClients;
    public FiveMUtils() {
        this.clients = null;
        this.gametype = null;
        this.hostname = null;
        this.mapname = null;
        this.maxClients = null;
    }

    public Integer getClients() {
        return clients;
    }
    public String getGametype() {
        return gametype;
    }

    public String getHostname() {
        return hostname;
    }

    public String getMapname() {
        return mapname;
    }

    public Integer getMaxClients() {
        return maxClients;
    }

    public void setClients(Integer clients) {
        this.clients = clients;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setMapname(String mapename) {
        this.mapname = mapename;
    }

    public void setMaxClients(Integer maxClients) {
        this.maxClients = maxClients;
    }
}
