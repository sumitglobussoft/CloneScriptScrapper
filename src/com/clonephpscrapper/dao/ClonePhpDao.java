package com.clonephpscrapper.dao;

import com.clonephpscrapper.entity.Categories;
import com.clonephpscrapper.entity.CategoriesData;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GLB-214
 */
public interface ClonePhpDao {
    
     public void insertCategoriesData(Categories objCategories);
    
     public void insertCategoriesCrawledData(CategoriesData objCategoriesData);
     
     public List<Categories> getCategoriesDataList();
     
     public void updateCategoriesData(Categories objCategories);
    
}
