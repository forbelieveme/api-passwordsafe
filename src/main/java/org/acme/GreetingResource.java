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

        try {
            URL baseURL = new URL("HTTPS", "lab01vuvm.desabpd.popular.local", 443, "/beyondtrust/api/public/v3/");
            URL SignAppInURL = new URL(baseURL, "Auth/SignAppIn");
            HttpURLConnection connection = (HttpURLConnection) SignAppInURL.openConnection();
            connection.setRequestProperty("Authorization",
                    "PS-Auth key=57dd0e20bd52bf0178a68ad86ecede1833041f1b6cf58ea258ed529083109415db9d27cf2be0e229a9c977ff2f3f08f908f3c16b79546edd77c317cd660abdf9; runas=salesforceipsa;");
            String test = "";
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(test.getBytes());
            outputStream.close();
            connection.getInputStream();
            String cookie = connection.getHeaderFields().get("Set-Cookie").get(0);
            System.out.println(cookie);
            connection.disconnect();

            URL managedAccountsURL = new URL(baseURL, "ManagedAccounts");
            connection = (HttpURLConnection) managedAccountsURL.openConnection();
            connection.setRequestProperty("Cookie", cookie);
            connection.setRequestProperty("Authorization",
                    "PS-Auth key=57dd0e20bd52bf0178a68ad86ecede1833041f1b6cf58ea258ed529083109415db9d27cf2be0e229a9c977ff2f3f08f908f3c16b79546edd77c317cd660abdf9; runas=salesforceipsa;");
            InputStream responseStream2 = connection.getInputStream();
            ObjectMapper mapper2 = new ObjectMapper();
            JsonNode neoJsonNode2 = mapper2.readTree(responseStream2);
            JsonNode systemId = neoJsonNode2.get("SystemId");
            JsonNode accountId = neoJsonNode2.get("AccountId");
            System.out.println("SystemId: " + systemId.toString());
            System.out.println("AccountId: " + accountId.toString());
            connection.disconnect();

            // URL requestIdURL = new URL(baseURL, "Requests");
            // connection = (HttpURLConnection) requestIdURL.openConnection();
            // connection.setRequestProperty("Authorization",
            // "PS-Auth
            // key=57dd0e20bd52bf0178a68ad86ecede1833041f1b6cf58ea258ed529083109415db9d27cf2be0e229a9c977ff2f3f08f908f3c16b79546edd77c317cd660abdf9;
            // runas=salesforceipsa;");

            // String requestData =
            // "{\"SystemId\":"+systemId.toString()+",\"AccountId\":"+accountId.toString()+",\"DurationMinutes\":10}";
            // connection.setDoOutput(true);
            // outputStream = connection.getOutputStream();
            // outputStream.write(requestData.getBytes());
            // outputStream.close();
            // responseStream = connection.getInputStream();

            // System.out.println("RequestId: " + responseStream.toString());

            // connection.disconnect();

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
