package impl;

import com.nagendra.ProductService.controller.impl.CustomUtils;
import com.nagendra.ProductService.controller.impl.ProductControllerImpl;
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
    private static final String ENDPOINT_URL = "/Products";

    @InjectMocks
    private ProductControllerImpl ProductController;
    @Mock
    private ProductService ProductService;
    @Mock
    private ProductMapper ProductMapper;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.ProductController).build();
    }

    @Test
    public void getAll() throws Exception {
        Mockito.when(ProductMapper.asDTOList(ArgumentMatchers.any())).thenReturn(ProductBuilder.getListDTO());

        Mockito.when(ProductService.findAll()).thenReturn(ProductBuilder.getListEntities());
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

    }

    @Test
    public void getById() throws Exception {
        Mockito.when(ProductMapper.asDTO(ArgumentMatchers.any())).thenReturn(ProductBuilder.getDTO());

        Mockito.when(ProductService.findById(ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.of(ProductBuilder.getEntity()));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
        Mockito.verify(ProductService, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(ProductService);
    }

    @Test
    public void save() throws Exception {
        Mockito.when(ProductMapper.asEntity(ArgumentMatchers.any())).thenReturn(ProductBuilder.getEntity());
        Mockito.when(ProductService.save(ArgumentMatchers.any(Product.class))).thenReturn(ProductBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.post(ENDPOINT_URL)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(com.nagendra.ProductService.controller.impl.CustomUtils.asJsonString(ProductBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(ProductService, Mockito.times(1)).save(ArgumentMatchers.any(Product.class));
        Mockito.verifyNoMoreInteractions(ProductService);
    }

    @Test
    public void update() throws Exception {
        Mockito.when(ProductMapper.asEntity(ArgumentMatchers.any())).thenReturn(ProductBuilder.getEntity());
        Mockito.when(ProductService.update(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(ProductBuilder.getEntity());

        mockMvc.perform(
                        MockMvcRequestBuilders.put(ENDPOINT_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(CustomUtils.asJsonString(ProductBuilder.getDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(ProductService, Mockito.times(1)).update(ArgumentMatchers.any(Product.class), ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(ProductService);
    }

    @Test
    public void delete() throws Exception {
        Mockito.doNothing().when(ProductService).deleteById(ArgumentMatchers.anyLong());
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(ProductService, Mockito.times(1)).deleteById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(ProductService);
    }
}