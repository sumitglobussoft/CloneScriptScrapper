/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clonescriptscrapper.dao;

import com.clonescriptscrapper.entity.Categories;
import com.clonescriptscrapper.entity.CategoriesData;
import java.util.List;

/**
 *
 * @author GLB-214
 */
public interface CloneScriptDirectoryDao {

    public void insertCategoriesData(Categories objCategories);

    public void insertCategoriesCrawledData(CategoriesData objCategoriesData);

    public List<Categories> getCategoriesDataList();

    public void updateCategoriesData(Categories objCategories);

}
