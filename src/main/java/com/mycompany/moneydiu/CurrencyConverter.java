/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.moneydiu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Santiago Higuita
 */
public class CurrencyConverter {
    private static HttpURLConnection con;
    private static String valor;
    
    public static float getExchangeRate(boolean toDollar){
        try{
            URL url;
            if(toDollar){
                url = new URL("https://api.exchangeratesapi.io/latest?symbols=USD");
            }else{
                url = new URL("https://api.exchangeratesapi.io/latest?base=USD&symbols=EUR");
            }
            con = (HttpURLConnection) url.openConnection();
        
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            //System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                                    con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            in.close();
            Pattern pat = Pattern.compile("\\d+\\.\\d+");
            Matcher m = pat.matcher(response.toString());
            if (m.find()) valor = m.group();
            // print result
                Float f = Float.parseFloat(valor);
                //System.out.println(f);
                return f;
            } else {
                System.out.println("GET request not worked");
                return -1;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
