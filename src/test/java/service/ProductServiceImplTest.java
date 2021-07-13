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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * @author Seren Bolat
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    private final ObjectMapper mapper = new ObjectMapper();
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    private ProductEntity productEntity;
    private ProductDto request;

    @Captor
    private ArgumentCaptor<ProductEntity> argumentCaptor;

    @Before
    public void setup() throws JsonProcessingException {
        productService = new ProductServiceImpl(productRepository);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{ \"attributes\" : \n" +
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
        Mockito.when(productRepository.save(argumentCaptor.capture())).thenReturn(productEntity);

        ProductDto product = productService.createProduct(request);

        assertThat(argumentCaptor.getValue())
                .isNotNull()
                .isExactlyInstanceOf(ProductEntity.class);
        Mockito.verify(productRepository, Mockito.times(1)).save(argumentCaptor.getValue());
        assertHasSamePropertyValues(product, productEntity);
    }

    @Test
    public void getProduct() throws JsonProcessingException {
        Mockito.when(productRepository.findByAsOfAndId(1, "123")).thenReturn(productEntity);
        ProductDto product = productService.getProduct("123", 1);

        assertThat(product)
                .isNotNull()
                .isExactlyInstanceOf(ProductDto.class);
        assertHasSamePropertyValues(product, productEntity);
    }

    @Test
    public void getProduct_WithException() {
        Mockito.when(productRepository.findByAsOfAndId(2, "123"))
                .thenThrow(new DataNotFoundException());

        Throwable throwable = catchThrowable(() -> productService.getProduct("123", 2));

        assertThat(throwable)
                .isNotNull()
                .isExactlyInstanceOf(DataNotFoundException.class);
    }

    private void assertHasSamePropertyValues(ProductDto product, ProductEntity productEntity) throws JsonProcessingException {
        assertThat(product.getId()).isEqualTo(productEntity.getId());
        assertThat(product.getName()).isEqualTo(productEntity.getName());
        assertThat(product.getBusiness()).isEqualTo(productEntity.getBusiness());
        assertThat(product.getCurrency()).isEqualTo(productEntity.getCurrency());
        assertThat(product.getAsOf()).isEqualTo(productEntity.getAsOf());
        assertThat(product.getAttributes()).isEqualTo(mapper.readTree(productEntity.getAttributes()));
    }

}
