package projekt3;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import projekt3.pages.PageObjectSearch;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    private static WebDriver driver;
    private PageObjectSearch pageObjectSearch;
    @BeforeAll
    static void setUpBeforeClass() {
        BrowserHandler.setBrowser(Browser.Firefox);
        FirefoxOptions FirefoxOptions = new FirefoxOptions();
        FirefoxOptions.addArguments("--headless","window-size=1024,768", "--no-sandbox");
        driver = new FirefoxDriver(FirefoxOptions);
    }
    @Test
    void SimpleSearch(){
        pageObjectSearch = new PageObjectSearch(driver);
        pageObjectSearch.Search("Blouse");
        assertEquals("Search - My Store",pageObjectSearch.GetTitle());
    }
    @Test
    void SearchOneResult(){
        pageObjectSearch = new PageObjectSearch(driver);
        pageObjectSearch.Search("Blouse");
        assertEquals(1,pageObjectSearch.CountFoundItems());
    }
    @Test
    void CompleteOrder(){
        pageObjectSearch = new PageObjectSearch(driver);
        pageObjectSearch.Search("T-shirt");
        pageObjectSearch.ClickOnResult(0);
        assertNotNull(pageObjectSearch.AddToCart());
    }
    @Test
    void FailedOrder(){
        pageObjectSearch = new PageObjectSearch(driver);
        pageObjectSearch.Search("Dress");
        assertNull(pageObjectSearch.AddToCart());
    }
    @Test
    void SearchMultipleResults(){
        pageObjectSearch = new PageObjectSearch(driver);
        pageObjectSearch.Search("Dress");
        assertTrue(pageObjectSearch.CountFoundItems()>1);
    }
    @Test
    void SearchZeroResults(){
        pageObjectSearch = new PageObjectSearch(driver);
        pageObjectSearch.Search("ujehqwequhqekuh");
        assertFalse(pageObjectSearch.CountFoundItems()!=0);
    }
    @Test
    void FailedSearch(){
        pageObjectSearch = new PageObjectSearch(driver);
        pageObjectSearch.FailedSearch("Blouse");
        assertNotEquals("Search - My Store",pageObjectSearch.GetTitle());
    }
    @Test
    void ClickOnFirstResult(){
        pageObjectSearch = new PageObjectSearch(driver);
        pageObjectSearch.Search("T-shirt");
        pageObjectSearch.ClickOnResult(0);
        assertEquals("Faded Short Sleeve T-shirts - My Store",pageObjectSearch.GetTitle());
    }
    @Test
    void ClickOnLastResult(){
        pageObjectSearch = new PageObjectSearch(driver);
        pageObjectSearch.Search("Dress");
        pageObjectSearch.ClickOnResult(pageObjectSearch.CountFoundItems()-1);
        assertEquals("Blouse - My Store",pageObjectSearch.GetTitle());
    }

    @AfterAll
    static void tearDownAfterClass() {
            driver.quit();
    }
}
