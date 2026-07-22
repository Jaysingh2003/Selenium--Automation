package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

// This is a PAGE OBJECT — it represents the Amazon search page.
// It knows HOW to interact with the page (locators + actions).
// It does NOT contain any assertions — that's the test's job.
public class AmazonSearchPage {

    // The browser session, passed in from the test (via BaseTest)
    private final WebDriver driver;

    // Used to wait for elements to appear before interacting with them
    private final WebDriverWait wait;

    // ---------- LOCATORS ----------
    // These define WHERE elements are on the page.
    // If Amazon changes their HTML, you only update these lines —
    // nothing in the test files needs to change.

    private final By searchBox = By.id("twotabsearchtextbox");     // the search input box
    private final By searchButton = By.id("nav-search-submit-button"); // the search (magnifying glass) button
    private final By resultTitles = By.cssSelector("h2 span");     // product titles in search results

    // Constructor — receives the driver and wait object from the test class.
    // This is how the test "hands over" its browser session to this page object.
    public AmazonSearchPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // ---------- ACTIONS ----------
    // These are the things you can DO on this page.
    // Tests call these methods instead of touching locators directly.

    // Opens Amazon's homepage
    public void open() {
        driver.get("https://www.amazon.in");
    }

    // Types a product name into the search box and clicks search
    public void searchProduct(String product) {
        // wait until the search box is actually visible before typing into it
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));

        box.clear();            // clear any pre-filled text first
        box.sendKeys(product);  // type the product name (e.g. "laptop")

        driver.findElement(searchButton).click(); // click the search button
    }

    // Returns a list of all product titles shown in the search results
    public List<String> getResultTitles() {
        // wait until at least one result title is present on the page
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultTitles));

        // grab every matching element (findElements = plural, returns a list)
        List<WebElement> elements = driver.findElements(resultTitles);

        // convert each WebElement into just its visible text,
        // and skip any blank/empty ones (Amazon sometimes has empty <h2><span> tags)
        return elements.stream()
                .map(WebElement::getText)
                .filter(text -> !text.isBlank())
                .collect(Collectors.toList());
    }
}