package drain.api.provider;

import drain.api.controller.OpenSSLController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ProviderApi {
    public static String sendRequest(String mode, String URL) throws Exception {
        URL url = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(2000);
        connection.setReadTimeout(2000);
        connection.connect();
        Scanner scanner = new Scanner(url.openStream());
        JSONParser parser = new JSONParser();
        String data = OpenSSLController.decrypt_openssl_data(scanner.nextLine());
        JSONObject jsonObject = (JSONObject) parser.parse(data);

        if(jsonObject != null){
            switch (mode){
                case "login":
                    return (String) jsonObject.get("status");
                case "uid":
                    return (String) jsonObject.get("uid");
                case "role":
                    return (String) jsonObject.get("role");
                case "expire":
                    return (String) jsonObject.get("expire");
                default:
                    return "Ошибка в запросе";
            }
        }
        else {
            return "Ошибка " + connection.getResponseCode();
        }
    }

}
