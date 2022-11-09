package org.acme;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello() throws IOException {

        // GET TEST
        // try {
        // URL url = new URL("http://www.javatpoint.com/java-tutorial");
        // HttpURLConnection con = (HttpURLConnection) url.openConnection();
        // con.setRequestMethod("POST");
        // con.setRequestProperty("Content-Type", "application/json");
        // for (int i = 1; i <= 10; i++) {
        // System.out.println(con.getHeaderFieldKey(i) + " = " + con.getHeaderField(i));
        // }
        // con.disconnect();
        // } catch (Exception e) {
        // System.out.println(e);
        // }

        // POST TEST

        // String baseURL =
        // "https://lab01vuvm.desabpd.popular.local/beyondtrust/api/public/v3/";
        // String apiKey =
        // "74e592aaec7d75a6b73421a1368c48e68454f9ca0321d8ff2257e1c84192767874dd32c684b449444557e54738504b0ddadc3ffbdcba0afe1db81d88b3f0f3a5";
        // String runAsUser = "_api_GEOPS";
        // String AuthorizationHeader1 = "PS-Auth key=" + apiKey + ";";
        // String AuthorizationHeader2 = "runas=" + runAsUser + ";";

        try {
            URL baseURL = new URL("HTTPS", "lab01vuvm.desabpd.popular.local", 443, "/beyondtrust/api/public/v3/");
            URL url = new URL(baseURL, "Auth/SignAppIn");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", "PS-Auth key=74e592aaec7d75a6b73421a1368c48e68454f9ca0321d8ff2257e1c84192767874dd32c684b449444557e54738504b0ddadc3ffbdcba0afe1db81d88b3f0f3a5; runas=_api_GEOPS;");
            // javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
            // new javax.net.ssl.HostnameVerifier() {
            // public boolean verify(String hostname,
            // javax.net.ssl.SSLSession sslSession) {
            // return hostname.equals("10.96.36.15");
            // }
            // });
            // HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // connection.setRequestMethod("POST");
            // connection.setRequestProperty("Content-Type", "application/json");
            // connection.setRequestProperty("Authorization",
            // "PS-Auth
            // key=74e592aaec7d75a6b73421a1368c48e68454f9ca0321d8ff2257e1c84192767874dd32c684b449444557e54738504b0ddadc3ffbdcba0afe1db81d88b3f0f3a5;
            // runas=_api_GEOPS;");
            // connection.setRequestProperty("Content-Length", String.valueOf(5));
            String test = "";
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(test.getBytes());
            outputStream.close();
            InputStream responseStream = connection.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode neoJsonNode = mapper.readTree(responseStream);
            JsonNode bpi = neoJsonNode.get("code");
            System.out.println("bpi.toString()");
            System.out.println(bpi.toString());
            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }

        return Response.ok().build();
    }
}

// URL baseURL = new URL("HTTPS", "the-server", 443,
// "/BeyondTrust/api/public/v3/");
// URL url = new URL(baseURL, "Auth/SignAppIn");
// HttpURLConnection conn = (HttpURLConnection)url.openConnection();
// conn.setRequestProperty("Authorization","PS-Auth key=c479a66f…c9484d;
// runas=doe-main\johndoe;");
