package projekt3;


import org.junit.Before;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class FormTest {
    private static WebDriver driver;
    private static List<String> fullForm = Arrays.asList("Jan","Kowalski","Czekoladowa","Jablkowa","Gdynia","ID","123","us","23","https://www.google.pl","12-312-3123","Yes","No","Yes","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce varius urna eget dui placerat, ut tempor risus vehicula. Aliquam ornare urna eu est sollicitudin sodales. Maecenas fringilla magna dictum, molestie lectus sed, fermentum mauris. Morbi cursus cursus lectus non auctor. Etiam eu nisl eget lorem eleifend vulputate. Sed ultricies lectus dui, id rhoncus tellus porta in. Donec faucibus quam auctor, porttitor velit porta, eleifend nibh. Proin eleifend pulvinar lobortis. Nulla dictum commodo dignissim. Interdum et malesuada fames ac ante ipsum primis in faucibus. Maecenas aliquet iaculis augue nec laoreet. Nullam tincidunt nisi vel augue ornare gravida. Praesent lectus tellus, ornare at lectus eget, posuere gravida turpis. Suspendisse enim neque, volutpat eget tortor sit amet, facilisis aliquet nisi.");
    private void FillForm(){
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.xpath("/html/body/div/a")).click();
        driver.findElement(By.id("address_first_name")).sendKeys("Jan");
        driver.findElement(By.id("address_last_name")).sendKeys("Kowalski");
        driver.findElement(By.id("address_street_address")).sendKeys("Czekoladowa");
        driver.findElement(By.id("address_city")).sendKeys("Gdynia");
        driver.findElement(By.id("address_zip_code")).sendKeys("123");
        driver.findElement(By.name("commit")).click();
    }
    private ArrayList<String> GetAllFormInfoToTheList(){
        List<String > listOfFields = Arrays.asList("first_name","last_name","street_address","secondary_address","city","state","zip_code","country","age","website","phone","interest_climb","interest_dance","interest_read","note");
        ArrayList<String> result = new ArrayList<>();
        for (String listOfField : listOfFields) {
            if (driver.findElements(By.cssSelector("span[data-test='" + listOfField + "']")).size() != 0) {
                result.add(driver.findElement(By.cssSelector("span[data-test='" + listOfField + "']")).getText());
            }
        }
        return result;
    }
    @BeforeAll
    static void setUpBeforeClass() {
        BrowserHandler.setBrowser(Browser.Firefox);
        FirefoxOptions FirefoxOptions = new FirefoxOptions();
        FirefoxOptions.addArguments("--headless","window-size=1024,768", "--no-sandbox");
        driver = new FirefoxDriver(FirefoxOptions);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @BeforeEach
     void logInBeforeEach(){
        driver.get("http://a.testaddressbook.com/");
        driver.findElement(By.id("sign-in")).click();
        driver.findElement(By.id("session_email")).sendKeys("b@b.com");
        driver.findElement(By.id("session_password")).sendKeys("b");
        driver.findElement(By.id("session_password")).submit();
    }
    @Test
    void SignInTest(){
        assertEquals("Address Book - Sign In",driver.getTitle());
    }
    @Test
    void FillMinimumForm(){
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        FillForm();
        assertEquals("Address was successfully created.",driver.findElement(By.xpath("/html/body/div/div[1]")).getText());
    }
    @Test
    void ShowFilledForm(){
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        FillForm();
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.xpath("/html/body/div/table/tbody/tr[1]/td[5]/a")).click();
        assertEquals("Gdynia",driver.findElement(By.xpath("/html/body/div/p[5]/span[2]")).getText());
    }
    @Test
    void DeleteFilledForm(){
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        FillForm();
        FillForm();
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.xpath("/html/body/div/table/tbody/tr[1]/td[7]/a")).click();
        driver.switchTo().alert().accept();

        assertEquals("Address was successfully destroyed.",driver.findElement(By.xpath("/html/body/div/div")).getText());
    }
    @Test
    void EditFilledForm(){
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        FillForm();
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.linkText("Edit")).click();
        driver.findElement(By.id("address_secondary_address")).sendKeys("Jablkowa");
        driver.findElement(By.id("address_secondary_address")).submit();
        assertTrue(driver.findElement(By.cssSelector("div[data-test='notice']")).getText().contains("Address was successfully updated."));
    }
    @Test
    void NoFirstName(){
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.xpath("/html/body/div/a")).click();
        driver.findElement(By.id("address_first_name")).sendKeys("");
        driver.findElement(By.id("address_last_name")).sendKeys("Kowalski");
        driver.findElement(By.id("address_street_address")).sendKeys("Czekoladowa");
        driver.findElement(By.id("address_city")).sendKeys("Gdynia");
        driver.findElement(By.id("address_zip_code")).sendKeys("123");
        driver.findElement(By.name("commit")).click();
        assertEquals("First name can't be blank",driver.findElement(By.xpath("/html/body/div/div/div/form/div[1]/ul/li")).getText());
    }
    @Test
    void NoLastName(){
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.xpath("/html/body/div/a")).click();
        driver.findElement(By.id("address_first_name")).sendKeys("Jan");
        driver.findElement(By.id("address_last_name")).sendKeys("");
        driver.findElement(By.id("address_street_address")).sendKeys("Czekoladowa");
        driver.findElement(By.id("address_city")).sendKeys("Gdynia");
        driver.findElement(By.id("address_zip_code")).sendKeys("123");
        driver.findElement(By.name("commit")).click();
        assertEquals("Last name can't be blank",driver.findElement(By.xpath("/html/body/div/div/div/form/div[1]/ul/li")).getText());
    }
    @Test
    void EmptyFormSubmit(){
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.xpath("/html/body/div/a")).click();
        driver.findElement(By.name("commit")).click();
        String result = driver.findElement(By.xpath("/html/body/div/div/div/form/div[1]/h4")).getText();
        assertEquals(5,Integer.parseInt(result.replaceAll("\\D+","")));
    }
    @Test
    void NoZipCode(){
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.xpath("/html/body/div/a")).click();
        driver.findElement(By.id("address_first_name")).sendKeys("Jan");
        driver.findElement(By.id("address_last_name")).sendKeys("Kowalski");
        driver.findElement(By.id("address_street_address")).sendKeys("Czekoladowa");
        driver.findElement(By.id("address_city")).sendKeys("Gdynia");
        driver.findElement(By.id("address_zip_code")).sendKeys("");
        driver.findElement(By.name("commit")).click();
        assertEquals("Zip code can't be blank",driver.findElement(By.xpath("/html/body/div/div/div/form/div[1]/ul/li")).getText());
    }
    @Test
    void FillFullForm(){
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        driver.findElement(By.xpath("/html/body/div/a")).click();
        driver.findElement(By.id("address_first_name")).sendKeys(fullForm.get(0));
        driver.findElement(By.id("address_last_name")).sendKeys(fullForm.get(1));
        driver.findElement(By.id("address_street_address")).sendKeys(fullForm.get(2));
        driver.findElement(By.id("address_secondary_address")).sendKeys(fullForm.get(3));
        driver.findElement(By.id("address_city")).sendKeys(fullForm.get(4));
        Select dropdown = new Select(driver.findElement(By.id("address_state")));
        dropdown.selectByValue(fullForm.get(5));
        driver.findElement(By.id("address_zip_code")).sendKeys(fullForm.get(6));
        driver.findElement(By.id("address_country_us")).click();
        driver.findElement(By.id("address_age")).sendKeys(fullForm.get(8));
        driver.findElement(By.id("address_website")).sendKeys(fullForm.get(9));
        driver.findElement(By.id("address_phone")).sendKeys(fullForm.get(10));
        driver.findElement(By.id("address_interest_climb")).click();
        driver.findElement(By.id("address_interest_read")).click();
        driver.findElement(By.id("address_note")).sendKeys(fullForm.get(14));
        driver.findElement(By.name("commit")).click();
        assertArrayEquals(GetAllFormInfoToTheList().toArray(),fullForm.toArray());
    }
    @AfterEach
     void logOutAfterEach(){
        driver.findElement(By.cssSelector("a[data-test='addresses']")).click();
        if(driver.findElements(By.xpath("/html/body/div/table/tbody/tr/td[7]/a")).size()!=0) {
            driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[7]/a")).click();
            driver.switchTo().alert().accept();
        }
        driver.findElement(By.xpath("/html/body/nav/div/div[1]/a[3]")).click();
    }
    @AfterAll
    static void tearDownAfterClass() {
        driver.quit();
    }
}
