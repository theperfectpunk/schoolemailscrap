/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school.email.scraper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.Integer.parseInt;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author mohit
 */
public class SchoolEmailScraper {

    /**
     * @param args the command line arguments
     * @throws java.net.MalformedURLException
     * @throws java.io.UnsupportedEncodingException
     */
    public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException, IOException, InterruptedException {
        // TODO code application logic here
        URL mycity4kids = new URL("http://www.mycity4kids.com/Delhi-NCR/Play-School__Dwarka_bl");
        URLConnection yc = mycity4kids.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
        int s=1;
        BufferedWriter out = new BufferedWriter(new FileWriter("list.txt"));
        while(s<4) {
            String inputLine;
            StringBuilder a = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                a.append(inputLine);
            in.close();
            String str = a.toString();
            String[] parts = str.split("<a href=\"/Delhi-NCR/Play-School/");
            int n=1;
            String[] link;
            while(n<21) {
                link = parts[n].split("\">");
                URL school = new URL("http://www.mycity4kids.com/Delhi-NCR/Play-School/".concat(link[0]));
                URLConnection conn = school.openConnection();
                BufferedReader inschool = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String inputLine2;
                StringBuilder b = new StringBuilder();
                while ((inputLine2 = inschool.readLine()) != null)
                    b.append(inputLine2);
                in.close();
                String str2 = b.toString();
                if(!str2.contains("mailto:")) {
                    System.out.println("enter captcha and press enter");
                    System.in.read();
                }
                else {
                    System.out.println(link[0]);
                    out.write(link[0].concat("\r\n"));
                    String[] emailsubstr = str2.split("mailto:");
                    String[] email = emailsubstr[1].split("\">");
                    System.out.println(email[0]);
                    out.write(email[0].concat("\r\n"));
                    out.flush();
                    n++;
                }
            }
            out.close();
            s++;
            mycity4kids = new URL("http://www.mycity4kids.com/Delhi-NCR/Play-School__Dwarka_bl".concat("?page=".concat(Integer.toString(s))));
            yc = mycity4kids.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            System.out.println(mycity4kids.toString());
        }
    }
    
}
