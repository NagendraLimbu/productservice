package com.nagendra.ProductService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO{
    private long productId;
    private String productName;
    private long price;
    private long quantity;
}