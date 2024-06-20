package org.bryt.tests;

import com.codeborne.selenide.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Iterator;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.DragAndDropOptions.to;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Serhii Bryt
 * 18.03.2024 13:40
 **/
public class SelenideTests {
    @BeforeClass
    public void beforeClass() {
        Configuration.baseUrl = "https://the-internet.herokuapp.com";
    }


    @Test
    public void addRemoveElements() {
        open("/add_remove_elements/");
        $$("button")
                .find(exactText("Add Element"))
                .click();
        $(byTagAndText("button", "Delete")).shouldBe(visible).click();
        $(byTagAndText("button", "Delete")).shouldBe(not(visible));
    }

    @Test
    public void basicAuth() {
        open("/basic_auth", AuthenticationType.BASIC, new BasicAuthCredentials("admin", "admin"));
        $(byText("Congratulations! You must have the proper credentials.")).shouldBe(visible);
    }

    @Test
    public void checkBoxes() {
        open("/checkboxes");
        $$("input[type=checkbox]").get(0).shouldBe(not(checked));
        $$("input[type=checkbox]").get(1).shouldBe(checked);
        $$("input[type=checkbox]").get(0).click();
        $$("input[type=checkbox]").get(1).click();
        $$("input[type=checkbox]").get(1).shouldBe(not(checked));
        $$("input[type=checkbox]").get(0).shouldBe(checked);
    }

    @Test
    public void DnD() {
        open("/drag_and_drop");
        $(byId("column-a")).dragAndDrop(to($("#column-b")));
        $(byId("column-a")).shouldHave(text("B"));
        $(byId("column-a")).dragAndDrop(to($("#column-b")));
        $(byId("column-a")).shouldHave(text("A"));
    }

    @Test
    public void dropdown() {
        open("/dropdown");
        SelenideElement $ = $("#dropdown");
        $.selectOption("Option 1");
        $.getSelectedOption().shouldHave(value("1"));
        $.selectOptionByValue("2");
        $.getSelectedOption().shouldHave(text("Option 2"));
    }

    @Test
    public void dynamic_controls() {
        open("/dynamic_controls");
        $("#checkbox-example")
                .$("#checkbox")
                .shouldBe(visible, not(checked));
        $("#checkbox-example")
                .$("button")
                .shouldHave(text("Remove"))
                .click();
        $("#loading").shouldBe(visible).shouldBe(hidden, Duration.ofSeconds(10));
        $("#checkbox-example")
                .$("#checkbox")
                .shouldNotBe(visible);
    }

    @Test(priority = 1)
    public void fileUploadDownload() throws IOException {
        File fileToUpload = File.createTempFile("upload", ".txt");
        String s = RandomStringUtils.randomAlphanumeric(300);
        Files.write(fileToUpload.toPath(), s.getBytes());

        open("/upload");
        $("#file-upload").uploadFile(fileToUpload);
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(text(fileToUpload.getName()));

        open("/download");
        File download = $(By.linkText(fileToUpload.getName())).download();

        assertThat(download).hasSameTextualContentAs(fileToUpload);
    }

    @Test
    public void login() {
        open("/login");
        $("#username").setValue("tomsmith");
        $("#password").setValue("SuperSecretPassword!");
        $("button.radius").click();

        $(".button.secondary.radius")
                .shouldBe(visible)
                .shouldHave(text("Logout"));
    }

    @Test
    public void nested_frames() {
        open("/nested_frames");
        switchTo().innerFrame("frame-top", "frame-middle");
        $("#content").shouldHave(text("MIDDLE"));
    }

    @Test
    public void hovers() {
        open("/hovers");
        ElementsCollection $$ = $$(".figure");
        for (int i = 0; i < $$.size(); i++) {
            SelenideElement selenideElement = $$.get(i);
            selenideElement.hover();
            selenideElement.$("h5").shouldHave(text("name: user" + (i + 1)));
        }
    }

    @Test
    public void javascript_alerts(){
        open("/javascript_alerts");
        $$("button")
                .find(text("Click for JS Alert"))
                .click();
        confirm("I am a JS Alert");
        String text = executeJavaScript("return arguments[0].textContent;", $("#result"));
        assertThat(text).isEqualTo("You successfully clicked an alert");

    }

    @Test
    public void windows(){
        open("/windows");
        $(byLinkText("Click Here")).click();
        switchTo().window(1);
        $("h3").shouldHave(text("New Window"));
        closeWindow();
        switchTo().window(0);
        $("h3").shouldHave(text("Opening a new window"));
    }
}
