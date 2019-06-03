package projekt3;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import projekt3.pages.PageObjectLogin;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {
    private static WebDriver driver;
    private PageObjectLogin pageObjectLogin;
    @BeforeAll
    static void setUpBeforeClass() {
        BrowserHandler.setBrowser(Browser.Chrome);
        ChromeOptions ChromeOptions = new ChromeOptions();
        ChromeOptions.addArguments("--headless","window-size=1024,768", "--no-sandbox");
        driver = new ChromeDriver(ChromeOptions);
    }
    @Test
    void StandardUserLogin(){
        pageObjectLogin = new PageObjectLogin(driver);
        pageObjectLogin.enterLogin("standard_user","secret_sauce");
        assertTrue(pageObjectLogin.loggedIn());
    }
    @Test
    void StandardUserLoginNoError(){
        pageObjectLogin = new PageObjectLogin(driver);
        pageObjectLogin.enterLogin("standard_user","secret_sauce");
        assertEquals("",pageObjectLogin.errorMessage());
    }
    @Test
    void LockedOutUserLogin(){
        pageObjectLogin = new PageObjectLogin(driver);
        pageObjectLogin.enterLogin("locked_out_user","secret_sauce");
        assertFalse(pageObjectLogin.loggedIn());
    }
    @Test
    void LockedOutUserError(){
        pageObjectLogin = new PageObjectLogin(driver);
        pageObjectLogin.enterLogin("locked_out_user","secret_sauce");
        assertEquals("Epic sadface: Sorry, this user has been locked out.",pageObjectLogin.errorMessage());
    }
    @Test
    void GetImgOfIdZeroStandardUser(){
        pageObjectLogin = new PageObjectLogin(driver);
        pageObjectLogin.enterLogin("standard_user","secret_sauce");
        assertEquals("https://www.saucedemo.com/img/bike-light-1200x1500.jpg",pageObjectLogin.postImageUrl(0));
    }
    @Test
    void GetImgOfIdZeroProblemUser(){
        pageObjectLogin = new PageObjectLogin(driver);
        pageObjectLogin.enterLogin("problem_user","secret_sauce");
        assertNotEquals("https://www.saucedemo.com/img/bike-light-1200x1500.jpg",pageObjectLogin.postImageUrl(0));
    }
    @Test
    void WrongCredentialsErrorWhenLogin(){
        pageObjectLogin = new PageObjectLogin(driver);
        pageObjectLogin.enterLogin("sahbsakhjassa","eqiqwiuyeyeiwu");
        assertEquals("Epic sadface: Username and password do not match any user in this service",pageObjectLogin.errorMessage());
    }
    @Test
    void LoginWhenNoPassword(){
        pageObjectLogin = new PageObjectLogin(driver);
        pageObjectLogin.enterLogin("problem_user","");
        assertEquals("Epic sadface: Password is required",pageObjectLogin.errorMessage());
    }
    @Test
    void LoginWhenNoUsername(){
        pageObjectLogin = new PageObjectLogin(driver);
        pageObjectLogin.enterLogin("","secret_sauce");
        assertEquals("Epic sadface: Username is required",pageObjectLogin.errorMessage());
    }
    @Test
    void LoginWhenWrongPassword(){
        pageObjectLogin = new PageObjectLogin(driver);
        pageObjectLogin.enterLogin("standard_user","wygqytqwtquywquyt");
        assertFalse(pageObjectLogin.loggedIn());
    }
    @Test
    void CompelteOrderStandardUSer(){
        pageObjectLogin = new PageObjectLogin(driver);
        pageObjectLogin.enterLogin("standard_user","secret_sauce");
        assertNotNull(pageObjectLogin.completeOrder());
    }
    @AfterAll
    static void tearDownAfterClass() {
        driver.quit();
    }

}
