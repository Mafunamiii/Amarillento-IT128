
package FrontEnd;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        new login();
        
        do {
          Scanner scanner = new Scanner(System.in);
        String itemId = null;
        
        String baseURL = "http://localhost:8080/item";
        System.out.print("Enter HTTP method (GET, POST, PUT, DELETE): ");
        String httpMethod = scanner.nextLine().toUpperCase();

        
        if (httpMethod.equals("POST")) {
            baseURL += "/additem";
        } else if (httpMethod.equals("PUT")) {
            System.out.print("Enter item ID: ");
            itemId = scanner.nextLine();
            baseURL += "/updateitem/" + itemId;
        } else if (httpMethod.equals("DELETE")) {
            System.out.print("Enter item ID: ");
            itemId = scanner.nextLine();
            baseURL += "/deleteitem/" + itemId;
        }
        URL url = new URL(baseURL);
        
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(httpMethod);

        if (httpMethod.equals("GET")) {
            int responseCode = con.getResponseCode();
            System.out.println("Response code: " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } else if (httpMethod.equals("POST")) {
            System.out.print("Enter item name: ");
            String itemName = scanner.nextLine();
            System.out.print("Enter item quantity: ");
            int itemQuantity = scanner.nextInt();
            System.out.print("Enter item category: ");
            String itemCategory = scanner.nextLine();
            scanner.nextLine();
            System.out.print("Enter item ID: ");
            itemId = scanner.nextLine();
            String jsonInputString = 
                "{\"itemId\": \"" + itemId +
                "\", \"name\": \"" + itemName +
                "\", \"quantity\": \"" + itemQuantity +
                "\", \"category\": \"" + itemCategory +
                "\"}";

            byte[] postData = jsonInputString.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }


            int responseCode = con.getResponseCode();
            System.out.println("Response code: " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } else if (httpMethod.equals("PUT")) {
            System.out.print("Enter new quantity: ");
            int newQuantity = scanner.nextInt();
            scanner.nextLine();
            
            String jsonInputString = "{\"quantity\": " + newQuantity + "}";
            byte[] postData = jsonInputString.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            int responseCode = con.getResponseCode();
            System.out.println("Response code: " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            
        } else if (httpMethod.equals("DELETE")) {
            baseURL += "/deleteitem/" + itemId;
            con.setRequestMethod("DELETE");
            int responseCode = con.getResponseCode();
            System.out.println("Response code: " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        
        }else {
            System.out.println("Invalid HTTP method.");
        }
          
        } while (true);
        
    }
}
