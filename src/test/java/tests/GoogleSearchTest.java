package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GoogleSearchPage;

public class GoogleSearchTest extends BaseTest {

    @Test
    public void searchOnGoogle() {
        GoogleSearchPage googlePage = new GoogleSearchPage(driver, wait);

        googlePage.open();
        googlePage.search("Selenium WebDriver");
        googlePage.waitForTitleContains("Selenium");

        Assert.assertTrue(googlePage.getTitle().contains("Selenium"),
                "Title should contain 'Selenium'");
    }
}