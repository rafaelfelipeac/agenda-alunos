package com.example.rafae.agenda;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by rafae on 07/10/2016.
 */

public class WebClient {
    public String post(String json){
        try {
            URL url = new URL("https://www.caelum.com.br/mobile");
            HttpURLConnection connnection = (HttpURLConnection) url.openConnection();
            connnection.setRequestProperty("Content-type", "application/json");
            connnection.setRequestProperty("Accept", "application/json");

            connnection.setDoOutput(true);

            PrintStream output = new PrintStream(connnection.getOutputStream());
            output.println(json);

            connnection.connect();

            Scanner scanner = new Scanner(connnection.getInputStream());
            //Scanner scanner = new Scanner(connection.getOutputStream());

            String resposta = scanner.next();

            return resposta;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
