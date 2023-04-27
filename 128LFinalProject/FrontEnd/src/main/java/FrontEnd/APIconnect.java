/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FrontEnd;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import java.time.YearMonth;
/**
 *
 * @author Jahn
 */
public class APIconnect {

    public APIconnect(int id, String itemName, String itemSupplier, String itemCategory, String PurchaseDate, String ExpiryDate, double ppu, int quantity) {
        this.id = id;
        this.itemName = itemName;
        this.itemSupplier = itemSupplier;
        this.itemCategory = itemCategory;
        this.PurchaseDate = PurchaseDate;
        this.ExpiryDate = ExpiryDate;
        this.ppu = ppu;
        this.quantity = quantity;
    }
    private String itemName, itemSupplier, itemCategory, PurchaseDate, ExpiryDate;
    private double ppu;
    private int quantity,id;
    
    String baseURL = "http://localhost:8080/item";
    String httpMethod;
    
    public String getData() throws IOException {
        baseURL = "http://localhost:8080/item";
        URL url = new URL(baseURL);
        httpMethod = "GET";
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(httpMethod);
        
        int responseCode = con.getResponseCode();
        System.out.println("Response code: " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        return (response.toString());
    }
    
    public void Createitem(int id, String itemName, String itemSupplier, String itemCategory, String PurchaseDate, String ExpiryDate, double itemPpu, int itemQuantity) throws IOException {
        baseURL = "http://localhost:8080/item";
        baseURL += "/additem";
        URL url = new URL(baseURL);
        httpMethod = "POST";
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(httpMethod);
        
        String jsonInputString = 
                "{\"itemId\": " + id +
                ", \"name\": \"" + itemName +
                "\", \"supplier\": \"" + itemSupplier +
                "\", \"quantity\": " + itemQuantity +
                ", \"category\": \"" + itemCategory +
                "\", \"PurchaseDate\": \"" + PurchaseDate +
                "\", \"ExpiryDate\": \"" + ExpiryDate +
                "\", \"ppu\": "      + itemPpu + "}";
        
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
    }
    
    public void Updateitem(int id, String itemName, String itemSupplier, String itemCategory, String PurchaseDate, String ExpiryDate, double itemPpu, int itemQuantity) throws IOException {
        baseURL = "http://localhost:8080/item";
        baseURL += "/updateitem/" + id;
        URL url = new URL(baseURL);
        httpMethod = "PUT";
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(httpMethod);
        
        String jsonInputString = 
                "{\"itemId\": " + id +
                ", \"name\": \"" + itemName +
                "\", \"supplier\": \"" + itemSupplier +
                "\", \"quantity\": " + itemQuantity +
                ", \"category\": \"" + itemCategory +
                "\", \"PurchaseDate\": \"" + PurchaseDate +
                "\", \"ExpiryDate\": \"" + ExpiryDate +
                "\", \"ppu\": "      + itemPpu + "}";
        
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
    }
    
    public void Deleteitem(int id) throws IOException {
        baseURL = "http://localhost:8080/item";
        baseURL += "/deleteitem/" + id;
        URL url = new URL(baseURL);
        httpMethod = "DELETE";
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(httpMethod);
        
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
        
    }
    
    public String getItem(int id) throws IOException {
        baseURL = "http://localhost:8080/item";
        baseURL += "/"+id;
        URL url = new URL(baseURL);
        httpMethod = "GET";
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(httpMethod);
        
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
        return (response.toString());
        
    }
}
