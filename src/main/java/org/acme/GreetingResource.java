package org.acme;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Path("/hello")
public class GreetingResource {

        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public Response hello() {
                /* Desarrollo */
                String host = "apipam-lab.desabpd.popular.local";
                /* QA */
                // String host = "apipam-qa.qaintdom.local";
                /* Produccion */
                // String host = "apipam.corp.popular.local";
                String apiBase = "/beyondtrust/api/public/v3/";
                String psAuthKey = "57dd0e20bd52bf0178a68ad86ecede1833041f1b6cf58ea258ed529083109415db9d27cf2be0e229a9c977ff2f3f08f908f3c16b79546edd77c317cd660abdf9";
                String runAs = "salesforceipsa";

                try {
                        System.out.println(passwordSafeCredentials(host, apiBase, psAuthKey, runAs));
                } catch (Exception e) {
                        System.out.println(e);
                }

                return Response.ok().build();
        }

        public String passwordSafeCredentials(String host, String apiBase, String psAuthKey, String runAs)
                        throws IOException {
                // System.out.println("System IP Address : " +
                // (localhost.getHostAddress()).trim());

                InetAddress localhost = InetAddress.getLocalHost();
                String authorizationHeader = "PS-Auth key=" + psAuthKey + "; runas=" + runAs + ";";
                URL baseURL = new URL("HTTPS", host, 443, apiBase);
                HttpURLConnection connection;
                String requestDataString = "";
                String cookieString = "";
                OutputStream outputStream;
                InputStream responseStream;
                String credentialsResponse = "";

                /*
                 **********
                 **********
                 */
                URL SignAppInURL = new URL(baseURL, "Auth/SignAppIn");
                connection = (HttpURLConnection) SignAppInURL.openConnection();
                connection.setRequestProperty("X-Forwarded-For", (localhost.getHostAddress()).trim());
                connection.setRequestProperty("Authorization", authorizationHeader);
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(requestDataString.getBytes());
                outputStream.close();
                connection.getInputStream();
                cookieString = connection.getHeaderFields().get("Set-Cookie").get(0);
                connection.disconnect();

                /*
                 **********
                 **********
                 */
                URL managedAccountsURL = new URL(baseURL, "ManagedAccounts");
                connection = (HttpURLConnection) managedAccountsURL.openConnection();
                connection.setRequestProperty("Cookie", cookieString);
                connection.setRequestProperty("X-Forwarded-For", (localhost.getHostAddress()).trim());
                connection.setRequestProperty("Authorization", authorizationHeader);
                responseStream = connection.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode managedAccountArray = (ArrayNode) mapper.readTree(responseStream);
                JsonNode systemId = null;
                JsonNode accountId = null;

                for (JsonNode jsonNode : managedAccountArray) {
                        if (jsonNode.get("AccountName").asText().equals("_IPSACRM")) {
                                systemId = jsonNode.get("SystemId");
                                accountId = jsonNode.get("AccountId");
                        }
                }
                connection.disconnect();

                /*
                 **********
                 **********
                 */
                URL requestIdURL = new URL(baseURL, "Requests");
                connection = (HttpURLConnection) requestIdURL.openConnection();
                connection.setRequestProperty("Cookie", cookieString);
                connection.setRequestProperty("X-Forwarded-For", (localhost.getHostAddress()).trim());
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Authorization", authorizationHeader);

                requestDataString = "{\"SystemId\":" + systemId + ",\"AccountId\":" + accountId
                                + ",\"DurationMinutes\":10}";
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(requestDataString.getBytes());
                outputStream.close();
                responseStream = connection.getInputStream();
                byte[] data = new byte[1024];
                responseStream.read(data);
                String requestsResponse = new String(data);
                connection.disconnect();

                /*
                 **********
                 **********
                 */
                URL credentialsURL = new URL(baseURL, "Credentials/" + requestsResponse);
                connection = (HttpURLConnection) credentialsURL.openConnection();
                connection.setRequestProperty("Cookie", cookieString);
                connection.setRequestProperty("X-Forwarded-For", (localhost.getHostAddress()).trim());
                connection.setRequestProperty("Authorization", authorizationHeader);
                responseStream = connection.getInputStream();
                byte[] responseData = new byte[1024];
                responseStream.read(responseData);
                credentialsResponse = new String(responseData);
                connection.disconnect();

                /*
                 **********
                 **********
                 */
                URL SignOutURL = new URL(baseURL, "Auth/Signout");
                connection = (HttpURLConnection) SignOutURL.openConnection();
                connection.setRequestProperty("Cookie", cookieString);
                connection.setRequestProperty("X-Forwarded-For", (localhost.getHostAddress()).trim());
                connection.setRequestProperty("Authorization", authorizationHeader);
                requestDataString = "";
                connection.setDoOutput(true);
                outputStream = connection.getOutputStream();
                outputStream.write(requestDataString.getBytes());
                outputStream.close();
                connection.getInputStream();
                connection.disconnect();

                return credentialsResponse;
        }
}
