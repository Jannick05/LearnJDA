package dk.jannick.learnjda.managers.slashcommand;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.jannick.learnjda.Main;
import dk.jannick.learnjda.utils.FiveMUtils;
import net.dv8tion.jda.api.entities.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FiveMManager {
    public static void fetchData() {
        try {
            FiveMUtils fiveMUtils = new FiveMUtils();
            String apiUrl = "http://89.23.86.7:30120/dynamic.json";

            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.toString());

                int clients = jsonNode.get("clients").asInt();
                fiveMUtils.setClients(clients);
                String gametype = jsonNode.get("gametype").asText();
                fiveMUtils.setGametype(gametype);
                String hostname = jsonNode.get("hostname").asText();
                fiveMUtils.setHostname(hostname);
                String mapname = jsonNode.get("mapname").asText();
                fiveMUtils.setMapname(mapname);
                int svMaxClients = jsonNode.get("sv_maxclients").asInt();
                fiveMUtils.setMaxClients(svMaxClients);

                Main.getJDA().getPresence().setActivity(Activity.watching(fiveMUtils.getClients() + " players \uD83D\uDE97"));
            } else {
                System.out.println("HTTP Request failed with response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

