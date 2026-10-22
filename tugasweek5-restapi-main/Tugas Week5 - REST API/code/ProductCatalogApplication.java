package com.budiluhur.product_catalog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.budiluhur.product_catalog.exception.ProductNotFoundException;
import com.budiluhur.product_catalog.model.Product;
import com.budiluhur.product_catalog.service.ProductService;

@SpringBootApplication
public class ProductCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductCatalogApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ProductService productService) {
        return args -> {
            System.out.println("=== SPRING BOOT PRODUCT CATALOG IS RUNNING ===\n");

            // Tambah produk PRD-03
            productService.createProduct(new Product("PRD-03", "Monitor Gaming 24 Inch", 2100000.0));

            // Cetak Daftar Produk di Spring Container
            System.out.println("Daftar Produk di Spring Container:");
            productService.getAllProducts().forEach(System.out::println);

            System.out.println("\n--- HARGA & DISKON ---");

            // Cetak Informasi Produk 1 (PRD-01)
            printProductDiscountInfo(productService, "PRD-01", 10.0);

            System.out.println(); // Baris baru

            // Cetak Informasi Produk 2 (PRD-02)
            printProductDiscountInfo(productService, "PRD-02", 0.0);
        };
    }

    private static void printProductDiscountInfo(ProductService productService, String id, double discountPercent) {
        try {
            Product product = productService.getProductById(id);
            double hargaAkhir = productService.getDiscountedProductPrice(id, discountPercent);
            String statusDiskon = (discountPercent > 0) ? "(Ada Diskon)" : "(Tidak Ada Diskon)";

            System.out.println("[" + product.getId() + "] " + product.getName());
            System.out.printf(" - Harga Normal : Rp%,.2f%n", product.getPrice());
            System.out.printf(" - Diskon       : %.1f%% %s%n", discountPercent, statusDiskon);
            System.out.printf(" - Harga Akhir  : Rp%,.2f%n", hargaAkhir);
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}