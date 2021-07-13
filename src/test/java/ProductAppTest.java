import com.serenbo.ProductApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.SpringApplication;

/**
 * @author Sereb Bolat
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SpringApplication.class)
@PowerMockIgnore({"javax.management.", "javax.script."})
public class ProductAppTest {

    @Before
    public void setup() {
        PowerMockito.mockStatic(SpringApplication.class);
    }

    @Test
    public void main() {
        ProductApp.main(new String[]{});
        PowerMockito.verifyStatic(SpringApplication.class, VerificationModeFactory.atLeastOnce());
        SpringApplication.run(ProductApp.class);
    }

}
