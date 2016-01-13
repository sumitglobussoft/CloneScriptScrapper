/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clonephpscrapper.crawler;

import com.clonephpscrapper.dao.ClonePhpDaoImpl;
import com.clonephpscrapper.entity.Categories;
import com.clonephpscrapper.entity.CategoriesData;
import com.clonephpscrapper.utility.GetRequestHandler;
import java.net.MalformedURLException;
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
    ClonePhpDaoImpl objClonePhpDaoImpl;

    public CrawlingEachUrlData(Categories objCategories, ClonePhpDaoImpl objClonePhpDaoImpl) {
        this.objCategories = objCategories;
        this.objClonePhpDaoImpl = objClonePhpDaoImpl;
    }

//    public static void main(String[] args) throws MalformedURLException, Exception {
//
//        CrawlingEachUrlData objCrawlingEachUrlData = new CrawlingEachUrlData();
//        String url = "http://clonephp.com/index.php?view=ads&catid=119&subcatid=&cityid=0";
//        objCrawlingEachUrlData.crawlingUrlData(url);
//    }
    public void crawlingUrlData(Categories objCategories, String Url) throws MalformedURLException, Exception {

//        String url = "http://clonephp.com/index.php?view=ads&catid=119&subcatid=&cityid=0";
//       Document doc = Jsoup.parse(fetchPage(new URI(url)));
        String title = "";
        String ImageUrl = "";
        String rating = "";
        String description = "";
        String PostID = "";
        String PostDate = "";
        String PostHits = "";

        String response = "";
        response = new GetRequestHandler().doGetRequest(new URL(Url));

        Document doc = Jsoup.parse(response);

        Elements ele = doc.select("table[class=postlisting] tbody tr td a");
        for (Element ele1 : ele) {
            objCategoriesData = new CategoriesData();

            String href = "http://clonephp.com/" + ele1.attr("href");
            System.out.println("Href : " + href);

            String responseDetails = "";
            responseDetails = new GetRequestHandler().doGetRequest(new URL(href));
            Document detailsDoc = Jsoup.parse(responseDetails);

            try {
                Element eleTitle = detailsDoc.select("table[class=postheader] tr td h1").first();
                title = eleTitle.text();
                System.out.println("Title  : " + title);
            } catch (Exception e) {
            }

            try {
                Element eleImage = detailsDoc.select("table[class=postpics] tbody span img").first();
                ImageUrl = "http://clonephp.com/" + eleImage.attr("src");
                System.out.println("Image Url : " + ImageUrl);
            } catch (Exception e) {
            }

            try {
                Element eleRating = detailsDoc.select("div[class=ratingblock] p").first();
                rating = eleRating.text().replace("", "");
                System.out.println("Rating : " + rating);
            } catch (Exception e) {
            }

            try {
                Element eleDescription = detailsDoc.select("div[id=wrap]").first();
                description = eleDescription.text();
                System.out.println("Description : " + description);
            } catch (Exception e) {
            }

            try {
                Element eleID = detailsDoc.select("table[class=postheader] tr td").first();
                String eleIDS = eleID.text();
                eleIDS = eleIDS.replace("|", "globus");
                String[] splitdata = eleIDS.split("globus");
                PostID = splitdata[0];
                PostDate = splitdata[1];
                PostHits = splitdata[2];
                System.out.println("Post ID   : " + PostID);
                System.out.println("Post Date : " + PostDate);
                System.out.println("Post Hits : " + PostHits);
            } catch (Exception e) {
            }

            objCategoriesData.setCategoryId(objCategories);
            objCategoriesData.setTitle(title);
            objCategoriesData.setImageUrl(ImageUrl);
            objCategoriesData.setRating(rating);
            objCategoriesData.setDescription(description);
            objCategoriesData.setPostId(PostID);
            objCategoriesData.setPostDate(PostDate);
            objCategoriesData.setPostHits(PostHits);

            objClonePhpDaoImpl.insertCategoriesCrawledData(objCategoriesData);

            System.out.println("============================================================");

        }
        try {
            System.out.println("pagination");
            Element e = doc.select("table[class=pagetable] tbody tr a[class=pagelink_next]").first();
            if (e.text().contains("Next")) {
                String nexturl1 = e.attr("href");
                nexturl1 = nexturl1.replace("amp;", "").replace("http://clonephp.com", "http://clonephp.com/");
                System.out.println("-----Next Pagination Url------" + nexturl1);
                crawlingUrlData(objCategories, nexturl1);
            }
        } catch (Exception e) {
            System.out.println("Page Not Avaliable");
        }
    }

    @Override
    public String call() {
        try {
            crawlingUrlData(objCategories, objCategories.getCategoryUrl());
        } catch (Exception ex) {
            Logger.getLogger(CrawlingEachUrlData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "done";
    }

}
