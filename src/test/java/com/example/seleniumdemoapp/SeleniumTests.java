package com.example.seleniumdemoapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class SeleniumTests {

    private static WebDriver driver;

    @BeforeAll
    static void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("incognito");
        options.addArguments("headless");
        driver = new ChromeDriver(options);
    }

    @BeforeEach
    void navigate() {
        driver.get("https://iths.se");
    }

    @Test
    void checkWebsiteTitle() {
        String websiteTitle = driver.getTitle();
        assertEquals("ITT-Högskolan – Här startar din IT-karriär!", websiteTitle, "Titeln verkar inte stämma...");
    }

    @Test
    void checkWebsiteHeading() {
        WebElement websiteHeading = driver.findElement(By.xpath("//*[@id=\"frontpage\"]/div/div[1]/div/div/div[1]/h1"));
        String websiteHeadingText = websiteHeading.getText();
        assertEquals("Här startar din IT-karriär!", websiteHeadingText, "Huvudrubriken verkar inte stämma...");
    }

    @Test
    void checkImage() {
        WebElement image = driver.findElement(By.xpath("//div[@class=\"banner__image\" and @style=\"background-image:url('https://www.iths.se/wp-content/uploads/2019/02/thumbnails/ithsred11nyptwebb-1-2466-627x320.jpg')\"]"));
        boolean result = image.isDisplayed();
        assertTrue(result, "Bilden verkar inte synas...");
    }

    @Test
    void checkEmail() {
        // Hämta ut H5-elementet ovanför den div där e-posten finns
        WebElement preHeading = driver.findElement(By.xpath("//h5[text()='Göteborg' and @class='preheading']"));

        // Hämta den div (nedanför H5) där eposten finns
        WebElement contactInfoDiv = driver.findElement(with(By.className("contact-site--info")).below(preHeading));

        // Hämta ut e-postelementet
        WebElement emailElement = contactInfoDiv.findElement(By.xpath("//a[@href='mailto:info@iths.se']"));

        String emailAddress = emailElement.getAttribute("href");

        assertEquals("mailto:info@iths.se", emailAddress, "E-posten verkar inte stämma...");
    }


    @AfterAll
    static void teardown() {
        driver.quit();
    }
}
