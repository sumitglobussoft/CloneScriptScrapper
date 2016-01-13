package com.clonephpscrapper.entity;

import com.clonephpscrapper.entity.Categories;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-13T15:37:46")
@StaticMetamodel(CategoriesData.class)
public class CategoriesData_ { 

    public static volatile SingularAttribute<CategoriesData, Integer> categoryDataId;
    public static volatile SingularAttribute<CategoriesData, String> title;
    public static volatile SingularAttribute<CategoriesData, String> postHits;
    public static volatile SingularAttribute<CategoriesData, String> imageUrl;
    public static volatile SingularAttribute<CategoriesData, String> description;
    public static volatile SingularAttribute<CategoriesData, String> postDate;
    public static volatile SingularAttribute<CategoriesData, Categories> categoryId;
    public static volatile SingularAttribute<CategoriesData, String> rating;
    public static volatile SingularAttribute<CategoriesData, String> postId;

}