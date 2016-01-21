/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clonescriptscrapper.crawler;

import com.clonescriptscrapper.dao.CloneScriptDirectoryDaoImpl;
import com.clonescriptscrapper.entity.Categories;
import com.clonescriptscrapper.entity.CategoriesData;
import com.clonescriptscrapper.utility.GetRequestHandler;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author GLB-214
 */
public class CrawlingEachUrlData implements Callable<String> {
    
    Categories objCategories = null;
    CategoriesData objCategoriesData;
    CloneScriptDirectoryDaoImpl objCloneScriptDirectoryDaoImpl;
    
    public CrawlingEachUrlData(Categories objCategories, CloneScriptDirectoryDaoImpl objCloneScriptDirectoryDaoImpl) {
        this.objCategories = objCategories;
        this.objCloneScriptDirectoryDaoImpl = objCloneScriptDirectoryDaoImpl;
    }

//    public static void main(String[] args) throws Exception {
//        CrawlingEachUrlData objCrawlingEachUrlData = new CrawlingEachUrlData();
//        String url = "http://www.clonescriptdirectory.com/freelancer-clone-script/";
//        objCrawlingEachUrlData.crawlingUrlData(url);
//    }
    public void crawlingUrlData(Categories objCategories, String url) throws Exception {
        
        String title = "";
        String name = "";
        String clicks = "";
        String addedon = "";
        String pagerank = "";
        String descriptions = "";
        String demoUrl = "";
//        String url = "http://www.clonescriptdirectory.com/freelancer-clone-script/";
        String response = "";
        response = new GetRequestHandler().doGetRequest(new URL(url));
        
        Document doc = Jsoup.parse(response);

//        System.out.println("" + doc);
        Elements ele = doc.select("div[class=listings] tbody tr td div[class=title] a");//.first();
        for (Element ele1 : ele) {
            
            objCategoriesData = new CategoriesData();
            
            String href = ele1.attr("href");
            System.out.println("Href : " + href);
            
            String responseDetails = "";
            responseDetails = new GetRequestHandler().doGetRequest(new URL(href));
            Document detailsDoc = Jsoup.parse(responseDetails);
            
            try {
                Element eleTitle = detailsDoc.select("td[valign=top] table tbody a[class*=countable listings]").first();
                title = eleTitle.text();
                System.out.println("Title : " + title);
            } catch (Exception e) {
            }
            
            try {
                Element eleName = detailsDoc.select("td[valign=top] table tbody tr td a").get(2);
                name = eleName.text();
                System.out.println("Name : " + name);
            } catch (Exception e) {
            }
            
            try {
                Element eleClicks = detailsDoc.select("td[valign=top] table tbody tr td td").get(5);
                clicks = eleClicks.text();
                System.out.println("Clciks : " + clicks);
            } catch (Exception e) {
            }
            
            try {
                Element eleAdded = detailsDoc.select("td[valign=top] table tbody tr td td").get(7);
                addedon = eleAdded.text();
                System.out.println("Added on : " + addedon);
            } catch (Exception e) {
            }
            
            try {
                Element elePageRank = detailsDoc.select("td[valign=top] table tbody tr td td").get(9);
                pagerank = elePageRank.text();
                System.out.println("Page Rank : " + pagerank);
            } catch (Exception e) {
            }
            
            try {
                Element eleDescription = detailsDoc.select("td[class=center-column] div[class=box]").get(1);
                String des = eleDescription.text();
                String[] splitDescp = des.split("/10");
                String description = splitDescp[1];
                String[] splitDescp1 = description.split("Fields");
                descriptions = splitDescp1[0];
                System.out.println("Description : " + descriptions);
            } catch (Exception e) {
            }
            
            try {
                Element eleDemo = detailsDoc.select("div[class=box-content-fixed] table tbody tr").first();
                demoUrl = eleDemo.text().replace("Demo URL:", "").replace("Download Url:", "");
                System.out.println("Demo Url : " + demoUrl);
            } catch (Exception e) {
            }
            
            objCategoriesData.setCategoryId(objCategories);
            objCategoriesData.setTitle(title);
            objCategoriesData.setName(name);
            objCategoriesData.setClicks(clicks);
            objCategoriesData.setAddedOn(addedon);
            objCategoriesData.setPageRank(pagerank);
            objCategoriesData.setDescription(descriptions);
            objCategoriesData.setDemoUrl(demoUrl);
            
            objCloneScriptDirectoryDaoImpl.insertCategoriesCrawledData(objCategoriesData);
            
            System.out.println("============================================================");
            
        }
        
        try {
            System.out.println("=====Pagination=====");
            Element e = doc.select("div[class=navigation]").first();
            Elements ee = e.select("a");
            if (ee.attr("title").contains("Next")) {
                String nexturl1 = ee.attr("href");
                System.out.println("-----Next Pagination Url------" + nexturl1);
                crawlingUrlData(objCategories, nexturl1);
            }
        } catch (Exception e) {
            System.out.println("Page Not Avaliable");
        }
        
    }
    
    @Override
    public String call() throws Exception {
        try {
            crawlingUrlData(objCategories, objCategories.getCategoryUrl());
        } catch (Exception ex) {
            Logger.getLogger(CrawlingEachUrlData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "done";
    }
}
