package extentions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static project.DriverManager.closeDriver;
import static project.DriverManager.getDriver;

public class DriverExtension implements BeforeEachCallback, AfterEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        getDriver();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        closeDriver();
    }
}
