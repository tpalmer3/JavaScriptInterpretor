package com.example.components;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.datatypes.SearchResult;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.awt.Desktop;

@JSComponent(name="web")
public class WebOps {

//    Pattern googlePattern = Pattern.compile("a href=\"/url(.*)</a>");
//    Pattern urlPattern = Pattern.compile("q=(.+)\"");
//    Pattern titlePattern = Pattern.compile(">(.+)</a>");
//    Pattern aPattern = Pattern.compile("(<a.+</\\a>)");
    HashMap<Integer, SearchResult> searchResults = new HashMap<>();

    @JSRunnable
    public String readPage(String s) {
        try {
            URL url;
            URLConnection uc;
            StringBuilder parsedContentFromUrl = new StringBuilder();
            System.out.println("Getting content for URl : " + s);
            url = new URL(s);
            uc = url.openConnection();
            uc.connect();
            uc = url.openConnection();
            uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            uc.getInputStream();
            BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
            int ch;
            while ((ch = in.read()) != -1) {
                parsedContentFromUrl.append((char) ch);
            }
            return parsedContentFromUrl.toString();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @JSRunnable
    public String google(String s) {
        String search = "";

        for(String str: s.split(" ")) {if(!str.equals(""))
                search += str + "+";
        }

        String html = readPage("https://www.google.com/search?q=" + search.substring(0, search.length()-1) + "&ie=utf-8&oe=utf-8&client=firefox-b-1");
        String[] htmWTF = html.substring(15,html.length()).trim().replaceAll("\n", "").replaceAll("\r", "").split("<a href=\"/url\\?q=");

        ArrayList<SearchResult> results = new ArrayList<>();

        for(int i = 1; i < htmWTF.length; i++) {
            try {
                String[] u = htmWTF[i].split("\"");
                if (u.length <= 1 || u[1].substring(0, 5).equals("><img"))
                    continue;
                String[] t = repair(u).substring(1, repair(u).length()).split("</a>");
                if (t.length <= 1)
                    continue;
                String d = repair(t).substring(1, repair(t).length()).split("<span class=st>")[1].split("</span")[0];

                SearchResult sr = new SearchResult(u[0].split("&amp")[0], t[0].replaceAll("<b>","").replaceAll("</b>", ""), d);
                results.add(sr);
            } catch(Throwable e) {}
        }

        int count = 1;
        for(SearchResult sr : results) {
            searchResults.put(count++, sr);
        }

        return printResults();
    }

    @JSRunnable
    public String printResults() {
        String ret = "";
        for(int i = 1; i < searchResults.size(); i++) {
            ret += i + ".) " + searchResults.get(i).getTitle() + "\n";
        }
        return ret;
    }

    @JSRunnable
    public String getPage(int p) {return readPage(getURL(p));}

    @JSRunnable
    public String getURL(int p) {return searchResults.get(p).getUrl();}

    private String repair(String[] s) {
        String ret = "";
        if(s.length > 1)
            for(int i = 1; i < s.length; i++)
                ret += s[i];
        return ret;
    }

    @JSRunnable
    public void openPage(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.err.println("Default browser not supported!");
            }
        } catch (URISyntaxException e) {
            System.err.println(e.getMessage() + " : " + e.getCause() + " -> " + e.getReason());
        } catch (IOException e) {
            System.err.println(e.getMessage() + " : " + e.getCause());
        }
    }
}
