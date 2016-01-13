package com.clonephpscrapper.entity;

import com.clonephpscrapper.entity.CategoriesData;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-13T15:37:46")
@StaticMetamodel(Categories.class)
public class Categories_ { 

    public static volatile SingularAttribute<Categories, String> categoryName;
    public static volatile SingularAttribute<Categories, Integer> count;
    public static volatile SingularAttribute<Categories, Integer> categoryId;
    public static volatile SingularAttribute<Categories, String> categoryUrl;
    public static volatile SingularAttribute<Categories, Integer> iscrawled;
    public static volatile CollectionAttribute<Categories, CategoriesData> categoriesDataCollection;

}