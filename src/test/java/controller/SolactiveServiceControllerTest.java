package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serenbo.controller.SolactiveServiceController;
import com.serenbo.dto.ProductDto;
import com.serenbo.enums.BusinessType;
import com.serenbo.service.ProductService;
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
 * @author Umut Ismet Erdolu
 */
@RunWith(MockitoJUnitRunner.class)
public class SolactiveServiceControllerTest {

    @InjectMocks
    private SolactiveServiceController serviceController;
    @Mock
    private ProductService productService;

    private ProductDto response;
    private ProductDto request;
    private JsonNode node;

    @Before
    public void setup() throws JsonProcessingException {
        serviceController = new SolactiveServiceController(productService);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{ \"attributes\" : \n" +
                "      {\n" +
                "         \"prefix.interval\": 10,\n" +
                "         \"prefix.isPre\": \"Seren Bolat\"\n" +
                "      }\n" +
                "}";
        node = mapper.readTree(jsonString);

        request = ProductDto.builder()
                .name("Seren")
                .business(BusinessType.EQUITY)
                .currency("TRL")
                .attributes(node)
                .asOf(1)
                .build();
        response = ProductDto.builder()
                .name("Seren")
                .business(BusinessType.EQUITY)
                .currency("TRL")
                .attributes(node)
                .asOf(1)
                .id("123")
                .build();
    }

    @Test
    public void productCreate() throws JsonProcessingException {
        Mockito.when(productService.createProduct(any())).thenReturn(response);
        Assert.assertEquals("123", serviceController.productCreate(request));
    }

    @Test
    public void productRead() throws JsonProcessingException {
        Mockito.when(productService.getProduct(anyString(), anyInt())).thenReturn(response);
        Assert.assertEquals(node, serviceController.productRead("123", 1));
    }
}
