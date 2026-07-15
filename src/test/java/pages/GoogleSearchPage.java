package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleSearchPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By searchBox = By.name("q");

    public GoogleSearchPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void open() {
        driver.get("https://www.google.com");
    }

    public void search(String term) {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        box.sendKeys(term);
        box.submit();
    }

    public void waitForTitleContains(String text) {
        wait.until(ExpectedConditions.titleContains(text));
    }

    public String getTitle() {
        return driver.getTitle();
    }
}