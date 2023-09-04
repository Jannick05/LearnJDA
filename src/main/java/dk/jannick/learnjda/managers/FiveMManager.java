package dk.jannick.learnjda.managers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.jannick.learnjda.Main;
import dk.jannick.learnjda.utils.FiveMUtils;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.entities.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FiveMManager {
    private static final String IP = Dotenv.configure().load().get("IP");
    public static void fetchData() {
        try {

            URL url = new URL("http://"+IP+"/dynamic.json");

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
                String gametype = jsonNode.get("gametype").asText();
                String hostname = jsonNode.get("hostname").asText();
                String mapname = jsonNode.get("mapname").asText();
                int svMaxClients = jsonNode.get("sv_maxclients").asInt();

                FiveMUtils fiveMUtils = new FiveMUtils(clients, gametype, hostname, mapname, svMaxClients);

                Main.getJDA().getPresence().setActivity(Activity.watching(fiveMUtils.clients + " players \uD83D\uDE97"));
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

