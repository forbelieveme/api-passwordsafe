package org.acme;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

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
        // for (int i = 1; i <= 8; i++) {
        // System.out.println(con.getHeaderFieldKey(i) + " = " + con.getHeaderField(i));
        // }
        // con.disconnect();
        // } catch (Exception e) {
        // System.out.println(e);
        // }

        // POST TEST
        try {
            String test = "{\"email\":\"Developer5@gmail.com\",\"password\":123456}";
            URL url = new URL("http://restapi.adequateshop.com/api/authaccount/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization",
                    "Basic " + Base64.getEncoder().encodeToString(("userName" + ":" + "password").getBytes()));
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