/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clonescriptscrapper.dao;

import com.clonescriptscrapper.entity.Categories;
import com.clonescriptscrapper.entity.CategoriesData;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 *
 * @author GLB-214
 */
public class CloneScriptDirectoryDaoImpl implements CloneScriptDirectoryDao {

    private SimpleJdbcInsert launchDataInsert;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public void insertCategoriesData(Categories objCategories) {

        String SQL = "insert into categories (CATEGORY_NAME, CATEGORY_URL, ISCRAWLED) values (?, ?, ?)";
        try {
            jdbcTemplateObject.update(SQL, objCategories.getCategoryName(), objCategories.getCategoryUrl(), objCategories.getIscrawled());
            System.out.println("Categories Data inserted");
        } catch (Exception e) {
        }
    }

    @Override
    public List<Categories> getCategoriesDataList() {

        List<Categories> CategoriesDataList = null;
        String SQL = "Select * from categories where iscrawled=0";
        try {
            CategoriesDataList = jdbcTemplateObject.query(SQL,
                    new BeanPropertyRowMapper(Categories.class));
            System.out.println("Fetching data");
        } catch (Exception e) {
        }
        return CategoriesDataList;
    }

    @Override
    public void insertCategoriesCrawledData(CategoriesData objCategoriesData) {

        String SQL = "insert into categories_data (CATEGORY_ID, TITLE, NAME, CLICKS, ADDED_ON, PAGE_RANK, DESCRIPTION, DEMO_URL) values (?,?,?, ?, ?, ? ,?,?)";
        try {
            jdbcTemplateObject.update(SQL, objCategoriesData.getCategoryId().getCategoryId(), objCategoriesData.getTitle(), objCategoriesData.getName(), objCategoriesData.getClicks(), objCategoriesData.getAddedOn(), objCategoriesData.getPageRank(), objCategoriesData.getDescription(), objCategoriesData.getDemoUrl());
            System.out.println("categories_data Table Data inserted");
        } catch (Exception e) {
        }
    }

    @Override
    public void updateCategoriesData(Categories objCategories) {

        String SQL = "update categories set ISCRAWLED = 1 where CATEGORY_ID = ?";
        jdbcTemplateObject.update(SQL, objCategories.getCategoryId());

        try {
            jdbcTemplateObject.update(SQL, objCategories.getCategoryId());
            System.out.println("Categories Data updated");
        } catch (Exception e) {
        }
    }

}
