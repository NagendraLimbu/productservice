package com.nagendra.ProductService.service.impl;

import com.nagendra.ProductService.constants.ProductServiceConstants;
import com.nagendra.ProductService.dao.ProductRepository;
import com.nagendra.ProductService.entity.Product;
import com.nagendra.ProductService.exception.ProductServiceCustomException;
import com.nagendra.ProductService.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product entity) {
        log.info("Adding Product.");
        Product p = repository.save(entity);
        log.info("Product is added: " + p.toString());
        return p;
    }

    @Override
    public List<Product> save(List<Product> entities) {
        return (List<Product>) repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        log.info("Finding the product by id: " + id);
        Optional<Product> p = Optional.ofNullable(repository.findById(id).orElseThrow(() -> new ProductServiceCustomException("Product couldn't found", ProductServiceConstants.PRODUCT_ERROR_CODE)));
        return p;
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        Page<Product> entityPage = repository.findAll(pageable);
        List<Product> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Product update(Product entity, long id) {
        Optional<Product> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public Product reduceQuantity(long id, long qty) {
        log.info("Reduce Qty: {}", qty);
        Product p = repository.findById(id).orElseThrow(()->new ProductServiceCustomException("Product couldn't found", ProductServiceConstants.PRODUCT_ERROR_CODE));
        if(p.getQuantity()<qty){
            throw new ProductServiceCustomException("Product doesn't have sufficient quantity.", ProductServiceConstants.NOT_SUFFICIENT_QUANTITY);
        }
        p.setQuantity(p.getQuantity()-qty);
        repository.save(p);
        log.info("Product quantity updated successfully");
        return p;
    }
}