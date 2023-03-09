package com.example.Rss_project.data.service.Impl;

import com.example.Rss_project.data.dto.ProductDTO;
import com.example.Rss_project.data.entity.ProductEntity;
import com.example.Rss_project.data.handler.ProductDataHandler;
import com.example.Rss_project.data.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    ProductDataHandler productDataHandeler;

    @Autowired
    public ProductServiceImpl(ProductDataHandler productDataHandler){
        this.productDataHandeler = productDataHandler;
    }

    // Service(Client) <-> Controller : DTO
    // Service <-> DAO(DB) : Entity
    @Override
    public ProductDTO saveProduct(String productId, String productName, int productPrice, int productStock){
        ProductEntity productEntity = productDataHandeler.saveProductEntity(productId, productName, productPrice, productStock);

        ProductDTO productDTO = new ProductDTO(productEntity.getProductId(), productEntity.getProductName(),
                productEntity.getProductPrice(), productEntity.getProductStocks());
        return productDTO;
    }

    @Override
    public ProductDTO getProduct(String productId){
        ProductEntity productEntity = productDataHandeler.getProductEntity(productId);

        ProductDTO productDTO = new ProductDTO(productEntity.getProductId(), productEntity.getProductName(),
                productEntity.getProductPrice(), productEntity.getProductStocks());
        return productDTO;
    }

}
