package projekt3.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObjectLogin {
    private WebDriver driver;
    private final Wait<WebDriver> wait;
    public PageObjectLogin(WebDriver driver){
        this.driver = driver;
        driver.get("https://www.saucedemo.com/index.html");
        wait = new WebDriverWait(driver,10);
    }
    public void enterLogin(String username, String password){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("password")).submit();
    }
    public boolean loggedIn(){
        boolean result = driver.findElements(By.className("product_label")).size()!=0;
        System.out.println(driver.findElements(By.className("product_label")).size());
        return result;
    }
    public String errorMessage(){
        String result="";
        if(driver.findElements(By.xpath("/html/body/div[2]/div[1]/div[1]/div/form/h3")).size()!=0) {
            result = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div/form/h3")).getText();
        }
        System.out.println(result);
        return result;
    }
    public WebElement completeOrder(){
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/inventory.html"));
        driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[1]/div[3]/button")).click();
        driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[2]/a")).click();
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/cart.html"));
        driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/div/div[2]/a[2]")).click();
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/checkout-step-one.html"));
        driver.findElement(By.id("first-name")).sendKeys("Jan");
        driver.findElement(By.id("last-name")).sendKeys("Kowalski");
        driver.findElement(By.id("postal-code")).sendKeys("123-12");
        driver.findElement(By.id("postal-code")).submit();
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/checkout-step-two.html"));
        driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/div/div[2]/div[8]/a[2]")).click();
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/checkout-complete.html"));
        return driver.findElement(By.className("pony_express"));
    }
    public String postImageUrl(int id){
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/inventory.html"));
        WebElement webElement = driver.findElement(By.id("item_"+id+"_img_link"));
        System.out.println(webElement.findElement(By.xpath(".//img")).getAttribute("src"));
        return webElement.findElement(By.xpath(".//img")).getAttribute("src");
}
}
