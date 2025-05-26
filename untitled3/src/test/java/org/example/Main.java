package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.ArrayList;
import java.util.List;

public class Main {
    
    private static WebDriver driver;
    @BeforeAll
    public static void main(String[] args) throws IllegalArgumentException{
        String browser = System.getProperty("browser", "chrome").toLowerCase(); // Получаем имя браузера из системных свойств

        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "C:\\Users\\Student\\Downloads\\chromedriver-win64\\chromedriver.exe");
                driver = new ChromeDriver();
                break;

            case "firefox":
                System.setProperty("webdriver.gecko.driver", "C:\\Users\\Student\\Downloads\\geckodriver\\geckodriver.exe");
                driver = new FirefoxDriver();
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
    @Step("Запуск тестов на главной странице")
    public void runTests() {
        TestTheInternetHomePage theInternetHomePage = new TestTheInternetHomePage(driver);
        theInternetHomePage.open();
        theInternetHomePage.test_PageTitle();
        theInternetHomePage.test_checkHeading();
        theInternetHomePage.test_linktoabtesting();
        theInternetHomePage.test_linktoaddremoveelements();
        theInternetHomePage.test_linktoBasicAuth();
        theInternetHomePage.test_linktoBrokenImages();
        theInternetHomePage.test_linktocheckboxes();

        TestFramesPage framesPage = new TestFramesPage(driver);
        framesPage.test_checklist();
        framesPage.testLinkToNestedFrames();
        framesPage.testLinkToIFrame();
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

// Page Object для домашней страницы The Internet
class TestTheInternetHomePage {
    private WebDriver driver;
    public TestTheInternetHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://the-internet.herokuapp.com/");
    }
    @Test
    public void test_PageTitle() {
        String actualTitle = driver.getTitle();
        String expectedTitle="The Internet";
        Assertions.assertEquals(expectedTitle,actualTitle);
    }
    @Test
    public void test_checkHeading(){
        WebElement heading = driver.findElement(new By.ByClassName("heading"));
        String actualHeading = heading.getText();
        String expectedHeading="Welcome to the-internet";
        Assertions.assertEquals(expectedHeading,actualHeading);
    }
    @Test
    public void test_linktoabtesting(){
        WebElement linktoabtesting = driver.findElement(By.linkText("A/B Testing"));
        linktoabtesting.click();
        String exepectedURL="https://the-internet.herokuapp.com/abtest";
        String actualURL= driver.getCurrentUrl();
        Assertions.assertEquals(exepectedURL,actualURL);
    }
    @Test
    public void test_linktoaddremoveelements(){
        WebElement linktoaddremoveelements = driver.findElement(By.linkText("Add/Remove Elements"));
        linktoaddremoveelements.click();
        String exepectedURL="https://the-internet.herokuapp.com/add_remove_elements/";
        String actualURL= driver.getCurrentUrl();
        Assertions.assertEquals(exepectedURL,actualURL);
    }
    @Test
    public void test_linktoBasicAuth(){
        WebElement linktobasicauth = driver.findElement(By.linkText("Basic Auth"));
        linktobasicauth.click();
        String exepectedURL="https://the-internet.herokuapp.com/basic_auth";
        String actualURL= driver.getCurrentUrl();
        Assertions.assertEquals(exepectedURL,actualURL);
    }
    @Test
    public void test_linktoBrokenImages(){
        WebElement linktobrokenimages = driver.findElement(By.linkText("Broken Images"));
        linktobrokenimages.click();
        String exepectedURL="https://the-internet.herokuapp.com/broken_images";
        String actualURL= driver.getCurrentUrl();
        Assertions.assertEquals(exepectedURL,actualURL);
    }
    @Test
    public void test_linktocheckboxes(){
        WebElement linktocheckboxes = driver.findElement(By.linkText("Ckeckboxes"));
        linktocheckboxes.click();
        String exepectedURL="https://the-internet.herokuapp.com/checkboxes";
        String actualURL= driver.getCurrentUrl();
        Assertions.assertEquals(exepectedURL,actualURL);
    }
}
// Page Object для  страницы FramesPage
class TestFramesPage{
    private WebDriver driver;
    public TestFramesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://the-internet.herokuapp.com/frames");
    }
    @Test
    public void test_checklist(){
        open();
        List<WebElement> allElements = driver.findElements(By.xpath("//div[@class='example']/ul/li"));
        List<String>actualList=new ArrayList<>();
        List<String>expectedList=new ArrayList<>();
        expectedList.add("Nested Frames");
        expectedList.add("IFrame");
        for (WebElement element: allElements) {
            actualList.add(element.getText());
        }
        Assertions.assertEquals(expectedList, actualList);
    }
    @Test
    public void testLinkToNestedFrames(){
        open();
        WebElement linktonestedframes = driver.findElement(By.linkText("Nested Frames"));
        linktonestedframes.click();
        String exepectedURL="https://the-internet.herokuapp.com/nested_frames";
        String actualURL= driver.getCurrentUrl();
        Assertions.assertEquals(exepectedURL,actualURL);
    }
    @Test
    public void testLinkToIFrame(){
        open();
        WebElement linktonestedframes = driver.findElement(By.linkText("Iframe"));
        linktonestedframes.click();
        String exepectedURL="https://the-internet.herokuapp.com/iframe";
        String actualURL= driver.getCurrentUrl();
        Assertions.assertEquals(exepectedURL,actualURL);
    }
}
