package com.example.components;

import com.example.annotations.JSComponent;
import com.example.annotations.JSRunnable;
import com.example.datatypes.SearchResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JSComponent(name="web")
public class WebOps {

//    Pattern googlePattern = Pattern.compile("a href=\"/url(.*)</a>");
//    Pattern urlPattern = Pattern.compile("q=(.+)\"");
//    Pattern titlePattern = Pattern.compile(">(.+)</a>");
    Pattern bodyPattern = Pattern.compile("(<body.+</body>)");

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

        System.out.println(html.substring(15,html.length()));

        ArrayList<SearchResult> results = new ArrayList<>();
        xmlParse(html.substring(15,html.length()), "a", results, SearchResult.class);

//        Matcher m  = googlePattern.matcher(html);
//        while(m.matches()) {
//            String checking = m.group();
//            System.out.println(checking);
//
//            Matcher url_m = urlPattern.matcher(checking);
//            Matcher title_m = titlePattern.matcher(checking);
//
//            results.add(new SearchResult(url_m.group(), title_m.group(),""));
//        }

        String ret = "";
        for(SearchResult sr : results) {
            ret += sr.toString();
        }

        return ret;
    }

    public void xmlParse(String xml, String tag, List<SearchResult> list, Class c) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml)));

            Element root = doc.getDocumentElement();
            NodeList nl = root.getElementsByTagName(tag);
            for(int i = 0; i < nl.getLength(); i++) {
                Element e = (Element)nl.item(i);
                SearchResult sr = new SearchResult(e.getAttribute("href"), e.getTextContent(), "");
                list.add(sr);
                System.out.println(sr.toString());
            }

        } catch(ParserConfigurationException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(SAXException e) {
            e.printStackTrace();
        }
    }
}
