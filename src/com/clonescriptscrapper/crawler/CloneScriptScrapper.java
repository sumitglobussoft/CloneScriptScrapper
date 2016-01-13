/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clonescriptscrapper.crawler;

import com.clonescriptscrapper.dao.CloneScriptDirectoryDaoImpl;
import com.clonescriptscrapper.entity.Categories;
import com.clonescriptscrapper.utility.GetRequestHandler;
import java.io.IOException;
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
public class CloneScriptScrapper {
    
    Categories objCategories;
    
    ApplicationContext context
            = new ClassPathXmlApplicationContext("Beans.xml");
    // TODO code application logic here

    CloneScriptDirectoryDaoImpl objCloneScriptDirectoryDaoImpl
            = (CloneScriptDirectoryDaoImpl) context.getBean("CloneScriptDirectoryDaoImpl");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, Exception {
        // TODO code application logic here
        CloneScriptScrapper objCloneScriptScrapper = new CloneScriptScrapper();
        objCloneScriptScrapper.crawledCategories();
    }
    
    public void crawledCategories() throws URISyntaxException, IOException, InterruptedException, Exception {
        
        String url = "http://www.clonescriptdirectory.com/";

//       Document doc = Jsoup.parse(fetchPage(new URI(url)));
        String response = "";
        response = new GetRequestHandler().doGetRequest(new URL(url));
        
        Document doc = Jsoup.parse(response);

//        System.out.println("---" + doc);
        Elements ele = doc.select("table[class=categories] tbody tr td a");
        for (Element ele1 : ele) {
            
            objCategories = new Categories();
            
            String title = ele1.text();
            String href = ele1.attr("href");
            System.out.println("Title : " + ele1.text());
            System.out.println("Href : " + ele1.attr("href"));
            
            objCategories.setCategoryName(title);
            objCategories.setCategoryUrl(href);
            
            objCloneScriptDirectoryDaoImpl.insertCategoriesData(objCategories);
        }
        
        List<Future<String>> list = new ArrayList<Future<String>>();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        
        List<Categories> listCatogories = objCloneScriptDirectoryDaoImpl.getCategoriesDataList();
        
        for (Categories listCatogory : listCatogories) {
            
            try {
                objCloneScriptDirectoryDaoImpl.updateCategoriesData(objCategories);
                Callable worker = new CrawlingEachUrlData(listCatogory, objCloneScriptDirectoryDaoImpl);
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

//            objcrawlingUrlData.crawlingUrlData(href);
    }
    
}
