package tests;

import org.testng.annotations.BeforeClass;
import utils.PropertyProvider;

public abstract class BaseTest {
    protected PropertyProvider provider;

    @BeforeClass
    final void setProvider() {
        provider = PropertyProvider.getInstance();
    }
}
