/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clonephpscrapper.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GLB-214
 */
@Entity
@Table(name = "categories")
public class Categories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CATEGORY_ID")
    private Integer categoryId;
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @Column(name = "CATEGORY_URL")
    private String categoryUrl;
    @Basic(optional = false)
    @Column(name = "ISCRAWLED")
    private int iscrawled;
    @Column(name = "COUNT")
    private Integer count;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryId")
    private Collection<CategoriesData> categoriesDataCollection;

    public Categories() {
    }

    public Categories(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Categories(Integer categoryId, int iscrawled) {
        this.categoryId = categoryId;
        this.iscrawled = iscrawled;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public int getIscrawled() {
        return iscrawled;
    }

    public void setIscrawled(int iscrawled) {
        this.iscrawled = iscrawled;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @XmlTransient
    public Collection<CategoriesData> getCategoriesDataCollection() {
        return categoriesDataCollection;
    }

    public void setCategoriesDataCollection(Collection<CategoriesData> categoriesDataCollection) {
        this.categoriesDataCollection = categoriesDataCollection;
    }

}
