package org.bryt.tests;

import com.codeborne.selenide.WebDriverProvider;
import org.brit.driver.PlaywrightiumDriver;
import org.brit.options.PlaywrightiumOptions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;
import java.nio.file.Path;

/**
 * Created by Serhii Bryt
 * 19.03.2024 14:51
 **/
public class PlaywrightDriverRemoteProvider implements WebDriverProvider {
    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        PlaywrightiumOptions playwrightiumOptions = new PlaywrightiumOptions();
        playwrightiumOptions.setHeadless(true);
        playwrightiumOptions.setBrowserName("chromium");
        playwrightiumOptions.setConnectionByWS(true);
        playwrightiumOptions.setRecordVideo(true);
        playwrightiumOptions.setRecordsFolder(Path.of("video"));
//        playwrightiumOptions.setConnectionByWS(false);
//        return new PlaywrightiumDriver("http://localhost:4444/wd/hub", playwrightiumOptions);
        return new PlaywrightiumDriver("http://moon.aerokube.local", playwrightiumOptions);

    }
}
