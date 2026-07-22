package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AmazonSearchPage;

import java.util.List;

// This is the TEST class — it decides WHAT to test and checks the RESULT.
// It never touches locators (By.id, By.cssSelector, etc.) directly.
// It only calls methods on AmazonSearchPage, which knows HOW to do things.
public class AmazonSearchTest extends BaseTest {

    // extends BaseTest means this class automatically gets:
    // - a fresh `driver` (Chrome browser) before every @Test method
    // - `driver.quit()` after every @Test method
    // (see base/BaseTest.java for that setup/teardown logic)

    @Test
    public void searchLaptopOnAmazon() {

        // Create the page object, handing it the current driver + wait
        // (this is the "remote control" for the Amazon search page)
        AmazonSearchPage amazonPage = new AmazonSearchPage(driver, wait);

        // ---------- STEPS (the scenario) ----------
        amazonPage.open();                    // go to amazon.in
        amazonPage.searchProduct("laptop");   // type "laptop" and hit search

        // Ask the page object for the list of product titles it found
        List<String> titles = amazonPage.getResultTitles();

        // ---------- VERIFICATION (the actual test) ----------
        // This is the ONLY part that decides pass/fail — all logic above
        // was just performing actions and collecting data.

        // 1. Make sure Amazon actually returned some results
        Assert.assertFalse(titles.isEmpty(), "Search should return at least one product");

        // 2. Check that at least ONE of those titles mentions "laptop"
        boolean foundLaptop = titles.stream()
                .anyMatch(title -> title.toLowerCase().contains("laptop"));

        Assert.assertTrue(foundLaptop,
                "Expected at least one result to mention 'laptop'");
    }
}