package dk.jannick.learnjda.utils;

public class FiveMUtils {
    public Integer clients;
    public String gametype;
    public String hostname;
    public String mapname;
    public Integer maxClients;

    public FiveMUtils(Integer clients, String gametype, String hostname, String mapname, Integer maxClients) {
        this.clients = clients;
        this.gametype = gametype;
        this.hostname = hostname;
        this.mapname = mapname;
        this.maxClients = maxClients;
    }
}
