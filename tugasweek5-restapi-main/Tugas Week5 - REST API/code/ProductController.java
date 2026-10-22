package com.budiluhur.product_catalog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.budiluhur.product_catalog.exception.ProductNotFoundException;
import com.budiluhur.product_catalog.model.Product;
import com.budiluhur.product_catalog.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    // Constructor Injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. GET ALL: GET /api/products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Mini Challenge: GET /api/products/search?keyword=keyboard
    // PENTING: diletakkan di atas @GetMapping("/{id}") agar "search"
    // tidak tertangkap sebagai path variable {id}.
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("keyword") String keyword) {
        return productService.searchProducts(keyword);
    }

    // 2. GET BY ID: GET /api/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") String id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // 3. CREATE: POST /api/products
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        productService.createProduct(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Produk '" + product.getName() + "' berhasil ditambahkan!");
    }

    // 4. UPDATE: PUT /api/products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("id") String id,
            @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (ProductNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // 5. DELETE: DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(
                    "Produk dengan ID '" + id + "' berhasil dihapus!");
        } catch (ProductNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
