package org.bryt.tests;

import com.codeborne.selenide.WebDriverProvider;
import org.brit.driver.PlaywrightiumDriver;
import org.brit.emulation.Device;
import org.brit.options.PlaywrightiumOptions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;

/**
 * Created by Serhii Bryt
 * 19.03.2024 14:51
 **/
public class PlaywrightDriverProvider implements WebDriverProvider {
    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        PlaywrightiumOptions playwrightiumOptions = new PlaywrightiumOptions();
        playwrightiumOptions.setHeadless(false);
        playwrightiumOptions.setBrowserName("webkit");
       // playwrightiumOptions.setRecordVideo(true);
        return new PlaywrightiumDriver(playwrightiumOptions);
    }
}
