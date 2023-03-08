package com.nagendra.ProductService.service;

import com.nagendra.ProductService.entity.Product;

public interface ProductService extends GenericService<Product, Long> {
    Product reduceQuantity(long id, long qty);
}