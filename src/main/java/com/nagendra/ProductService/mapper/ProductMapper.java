package com.nagendra.ProductService.mapper;

import com.nagendra.ProductService.dto.ProductDTO;
import com.nagendra.ProductService.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper extends GenericMapper<Product, ProductDTO> {
    @Override
    @Mapping(target = "productId", ignore = true)
    Product asEntity(ProductDTO dto);
}