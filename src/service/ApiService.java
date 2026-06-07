package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Country;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {
    private static final String BASE_URL = "https://restcountries.com/v3.1";

    public Country buscarPaisPorNome(String nomePais) {
        try {
            System.out.println("🌐 Buscando país na API REST Countries...");
            
            String urlString = BASE_URL + "/name/" + nomePais.replace(" ", "%20");
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8")
                );
                StringBuilder response = new StringBuilder();
                String linha;
                
                while ((linha = reader.readLine()) != null) {
                    response.append(linha);
                }
                reader.close();

                Country country = converterJsonParaPais(response.toString());
                
                if (country != null) {
                    System.out.println("✓ País encontrado na API!");
                    return country;
                }
            } else if (responseCode == 404) {
                System.out.println("✗ País não encontrado na API.");
            } else {
                System.out.println("✗ Erro na API. Código: " + responseCode);
            }

            conn.disconnect();

        } catch (Exception e) {
            System.err.println("✗ Erro ao buscar país na API: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private Country converterJsonParaPais(String jsonString) {
        try {
            JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();
            
            if (jsonArray.size() == 0) {
                return null;
            }

            JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
            Country pais = new Country();

            if (jsonObject.has("name")) {
                JsonObject name = jsonObject.getAsJsonObject("name");
                if (name.has("common")) {
                    pais.setNome(name.get("common").getAsString());
                }
                if (name.has("official")) {
                    pais.setNomeOficial(name.get("official").getAsString());
                }
            }

            if (jsonObject.has("capital") && jsonObject.get("capital").isJsonArray()) {
                JsonArray capitals = jsonObject.getAsJsonArray("capital");
                if (capitals.size() > 0) {
                    pais.setCapital(capitals.get(0).getAsString());
                }
            }

            if (jsonObject.has("region")) {
                pais.setRegiao(jsonObject.get("region").getAsString());
            }
            if (jsonObject.has("subregion")) {
                pais.setSubRegiao(jsonObject.get("subregion").getAsString());
            }

            if (jsonObject.has("population")) {
                pais.setPopulacao(jsonObject.get("population").getAsLong());
            }

            if (jsonObject.has("area")) {
                pais.setArea(jsonObject.get("area").getAsDouble());
            }

            if (jsonObject.has("flags")) {
                JsonObject flags = jsonObject.getAsJsonObject("flags");
                if (flags.has("png")) {
                    pais.setBandeiraUrl(flags.get("png").getAsString());
                }
            }

            return pais;

        } catch (Exception e) {
            System.err.println("✗ Erro ao converter JSON: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean testarConexaoApi() {
        try {
            URL url = new URL(BASE_URL + "/all");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            
            int responseCode = conn.getResponseCode();
            conn.disconnect();
            
            return responseCode == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
