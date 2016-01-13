/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clonephpscrapper.crawler;

import com.clonephpscrapper.dao.ClonePhpDaoImpl;
import com.clonephpscrapper.entity.Categories;
import static com.clonephpscrapper.utility.FetchPageWithProxy.fetchPage;
import com.clonephpscrapper.utility.GetRequestHandler;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author GLB-214
 */
public class ClonePhpScrapper {

    Categories objCategories;

    ApplicationContext context
            = new ClassPathXmlApplicationContext("Beans.xml");
    // TODO code application logic here

    ClonePhpDaoImpl objClonePhpDaoImpl
            = (ClonePhpDaoImpl) context.getBean("ClonePhpDaoImpl");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, Exception {
        // TODO code application logic here

        ClonePhpScrapper objClonePhpScrapper = new ClonePhpScrapper();
        objClonePhpScrapper.crawledCategories();
    }

    public void crawledCategories() throws URISyntaxException, IOException, InterruptedException, Exception {

        String url = "http://clonephp.com/";

//       Document doc = Jsoup.parse(fetchPage(new URI(url)));
        String response = "";
        response = new GetRequestHandler().doGetRequest(new URL(url));

        Document doc = Jsoup.parse(response);

        Elements ele = doc.select("table[class=dir] tbody tr td table[class=dir_cat] tbody tr th a");//.first();

        for (Element ele1 : ele) {
            objCategories= new Categories();

            String categoryName = ele1.text();
            String categoryUrl = "http://clonephp.com/" + ele1.attr("href");

            System.out.println("CATEGORY_NAME : " + categoryName);
            System.out.println("CATEGORY_URL  : " + categoryUrl);

            objCategories.setCategoryName(categoryName);
            objCategories.setCategoryUrl(categoryUrl);

            objClonePhpDaoImpl.insertCategoriesData(objCategories);

//            objCrawlingEachUrlData.crawlingUrlData(categoryUrl);
        }

        List<Future<String>> list = new ArrayList<Future<String>>();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Categories> listCatogories = objClonePhpDaoImpl.getCategoriesDataList();

        for (Categories listCatogory : listCatogories) {

            try {
                Callable worker = new CrawlingEachUrlData(listCatogory,objClonePhpDaoImpl);
                Future<String> future = executor.submit(worker);
                list.add(future);
            } catch (Exception exx) {
                System.out.println(exx);
            }

        }

        for (Future<String> fut : list) {
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                System.out.println(new Date() + "::" + fut.get());
            } catch (InterruptedException | ExecutionException ep) {
                ep.printStackTrace();
            }
        }
        //shut down the executor service now
        executor.shutdown();

    }

}
