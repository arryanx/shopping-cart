package com.aryanproject.dreamshop.service.product;

import com.aryanproject.dreamshop.model.Product;
import com.aryanproject.dreamshop.request.AddProductRequest;
import com.aryanproject.dreamshop.request.ProductUpdateRequest;

import java.util.List;

public interface ProductServiceInterface {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product>getAllProducts();
    List<Product>getProductsByCategory(String category);
    List<Product>getProductsByBrand(String brand);
    List<Product>getProductsByCategoryAndBrand(String category, String brand);
    List<Product>getProductsByName(String name);
    List<Product>getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
