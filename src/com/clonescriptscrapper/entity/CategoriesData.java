/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clonescriptscrapper.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author GLB-214
 */
@Entity
@Table(name = "categories_data")
public class CategoriesData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CATEGORY_DATA_ID")
    private Integer categoryDataId;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CLICKS")
    private String clicks;
    @Column(name = "ADDED_ON")
    private String addedOn;
    @Column(name = "PAGE_RANK")
    private String pageRank;
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID")
    @ManyToOne(optional = false)
    private Categories categoryId;

    public CategoriesData() {
    }

    public CategoriesData(Integer categoryDataId) {
        this.categoryDataId = categoryDataId;
    }

    public Integer getCategoryDataId() {
        return categoryDataId;
    }

    public void setCategoryDataId(Integer categoryDataId) {
        this.categoryDataId = categoryDataId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClicks() {
        return clicks;
    }

    public void setClicks(String clicks) {
        this.clicks = clicks;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public String getPageRank() {
        return pageRank;
    }

    public void setPageRank(String pageRank) {
        this.pageRank = pageRank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categories getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Categories categoryId) {
        this.categoryId = categoryId;
    }

}
