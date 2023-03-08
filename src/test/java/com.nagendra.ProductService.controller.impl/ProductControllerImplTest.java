package com.nagendra.ProductService.controller.impl;

import com.nagendra.ProductService.entity.Product;
import com.nagendra.ProductService.mapper.ProductMapper;
import com.nagendra.ProductService.service.ProductService;
import com.nagendra.ProductService.utils.ProductBuilder;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ProductControllerImplTest {
    //TODO: create the data Test generator class ProductBuilder
    private static final String ENDPOINT_URL = "/api/v1/product";
    @InjectMocks
    private ProductControllerImpl productController;
    @Mock
    private ProductService productService;
    @Mock
    private ProductMapper productMapper;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.productController).build();
    }

    @Test
    public void getAll() throws Exception {
        Mockito.when(productMapper.asDTOList(ArgumentMatchers.any())).thenReturn(ProductBuilder.getListDTO());

        Mockito.when(productService.findAll()).thenReturn(ProductBuilder.getListEntities());
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(productMapper.asDTO(ArgumentMatchers.any())).thenReturn(ProductBuilder.getDTO());

        Mockito.when(productService.findById(ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.of(ProductBuilder.getEntity()));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(productService, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(productService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(productMapper.asEntity(ArgumentMatchers.any())).thenReturn(ProductBuilder.getEntity());
        Mockito.when(productService.save(ArgumentMatchers.any(Product.class))).thenReturn(ProductBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(ProductBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(productService, Mockito.times(1)).save(ArgumentMatchers.any(Product.class));
        Mockito.verifyNoMoreInteractions(productService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(productMapper.asEntity(ArgumentMatchers.any())).thenReturn(ProductBuilder.getEntity());
        Mockito.when(productService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(ProductBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(CustomUtils.asJsonString(ProductBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(productService, Mockito.times(1)).update(ArgumentMatchers.any(Product.class), ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(productService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(productService).deleteById(ArgumentMatchers.anyLong());
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(productService, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(productService);
    }
}