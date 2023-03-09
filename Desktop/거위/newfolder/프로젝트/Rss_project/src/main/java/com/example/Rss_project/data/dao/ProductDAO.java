package com.example.Rss_project.data.dao;

import com.example.Rss_project.data.entity.ProductEntity;
public interface ProductDAO {
    ProductEntity  saveProduct(ProductEntity productEntity);
    ProductEntity getProduct (String productId);
}
