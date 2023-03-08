package com.nagendra.ProductService.controller.impl;

import com.nagendra.ProductService.dto.ProductDTO;
import com.nagendra.ProductService.entity.Product;
import com.nagendra.ProductService.mapper.ProductMapper;
import com.nagendra.ProductService.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/product")
@RestController
public class ProductControllerImpl{
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductControllerImpl(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO save(@RequestBody ProductDTO productDTO) {
        Product product = productMapper.asEntity(productDTO);
        return productMapper.asDTO(productService.save(product));
    }

    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable("id") long id) {
        Product product = productService.findById(id).orElse(null);
        return productMapper.asDTO(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        productService.deleteById(id);
    }

    @GetMapping
    public List<ProductDTO> list() {
        return productMapper.asDTOList(productService.findAll());
    }

    @GetMapping("/page-query")
    public Page<ProductDTO> pageQuery(Pageable pageable) {
        Page<Product> productPage = productService.findAll(pageable);
        List<ProductDTO> dtoList = productPage
                .stream()
                .map(productMapper::asDTO).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, productPage.getTotalElements());
    }

    @PutMapping("/{id}")
    public ProductDTO update(@RequestBody ProductDTO productDTO, @PathVariable("id") long id) {
        Product product = productMapper.asEntity(productDTO);
        return productMapper.asDTO(productService.update(product, id));
    }

    @PutMapping("/reduceQuantity/{id}")
    public long reduceQuantity(@PathVariable long id, @RequestParam long qty){
       Product p =  productService.reduceQuantity(id, qty);
        ProductDTO pdto = productMapper.asDTO(p);
        return qty;
    }
}