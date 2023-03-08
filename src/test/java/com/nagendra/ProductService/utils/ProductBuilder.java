package com.nagendra.ProductService.utils;

import com.nagendra.ProductService.dto.ProductDTO;
import com.nagendra.ProductService.entity.Product;

import java.util.Arrays;
import java.util.List;

public class ProductBuilder {
    public static List<ProductDTO> getListDTO() {
        return Arrays.asList(new ProductDTO(1, "p", 20, 2));
    }

    public static List<Product> getListEntities() {
        return Arrays.asList(
                new Product(1, "p", 20, 2)
        );
    }

    public static ProductDTO getDTO() {
        return new ProductDTO(1, "p", 20, 2);
    }

    public static Product getEntity() {
        return new Product(1, "p", 20, 2);
    }
}
