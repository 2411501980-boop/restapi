package com.budiluhur.product_catalog.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.budiluhur.product_catalog.exception.ProductNotFoundException;
import com.budiluhur.product_catalog.model.Product;

@Repository
public class ProductRepository {
    private final List<Product> productList = new ArrayList<>();

    public ProductRepository() {
        productList.add(new Product("PRD-01", "Keyboard Mechanical", 450000.0));
        productList.add(new Product("PRD-02", "Mouse Wireless Silent", 175000.0));
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public List<Product> findAll() {
        return productList;
    }

    public Product findById(String id) throws ProductNotFoundException {
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        throw new ProductNotFoundException("Produk dengan ID '" + id + "' tidak ditemukan!");
    }

    // Step 1: Update & Delete
    public Product updateProduct(String id, Product updatedProduct) throws ProductNotFoundException {
        Product existingProduct = findById(id);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        return existingProduct;
    }

    public boolean deleteById(String id) throws ProductNotFoundException {
        Product existingProduct = findById(id);
        return productList.remove(existingProduct);
    }

    // Mini Challenge: pencarian produk berdasarkan nama (case-insensitive)
    public List<Product> findByNameContains(String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product p : productList) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }
}