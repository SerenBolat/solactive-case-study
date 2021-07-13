package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serenbo.dto.ProductDto;
import com.serenbo.enums.BusinessType;
import com.serenbo.exception.DataNotFoundException;
import com.serenbo.models.ProductEntity;
import com.serenbo.repository.ProductRepository;
import com.serenbo.service.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.*;

/**
 * @author Seren Bolat
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    private ProductEntity productEntity;
    private ProductDto request;
    private String jsonString;

    @Before
    public void setup() throws JsonProcessingException {
        productService = new ProductServiceImpl(productRepository);

        ObjectMapper mapper = new ObjectMapper();
        jsonString = "{ \"attributes\" : \n" +
                "      {\n" +
                "         \"prefix.interval\": 10,\n" +
                "         \"prefix.isPre\": \"Seren Bolat\"\n" +
                "      }\n" +
                "}";
        JsonNode node = mapper.readTree(jsonString);

        request = ProductDto.builder()
                .name("Seren Bolat")
                .business(BusinessType.EQUITY)
                .currency("TRL")
                .attributes(node)
                .asOf(1)
                .build();
        productEntity = ProductEntity.builder()
                .name("Seren Bolat")
                .business(BusinessType.EQUITY)
                .currency("TRL")
                .attributes(jsonString)
                .asOf(1)
                .id("123")
                .build();
    }


    @Test
    public void createProduct() throws JsonProcessingException {
        Mockito.when(productRepository.save(any())).thenReturn(productEntity);
        productService.createProduct(request);
        Assert.assertEquals("123", productEntity.getId());
    }

    @Test
    public void getProduct() throws JsonProcessingException {
        Mockito.when(productRepository.findByAsOfAndId(anyInt(), anyString())).thenReturn(productEntity);
        productService.getProduct("123", 1);
        Assert.assertEquals(jsonString, productEntity.getAttributes());
    }

    @Test(expected = DataNotFoundException.class)
    public void getProduct_WithException() throws JsonProcessingException {
        Mockito.when(productRepository.findByAsOfAndId(anyInt(), anyString()))
                .thenThrow(new DataNotFoundException());
        productService.getProduct("123", 2);
        Assert.assertEquals(jsonString, productEntity.getAttributes());
    }

}
