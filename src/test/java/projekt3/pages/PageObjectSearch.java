package projekt3.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PageObjectSearch {
    private WebDriver driver;
    private WebDriverWait wait;
    public PageObjectSearch(WebDriver driver){
        this.driver = driver;
        driver.get("http://automationpractice.com/index.php");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    public void Search(String textToSearch){
        driver.findElement(By.id("search_query_top")).sendKeys(textToSearch);
        driver.findElement(By.id("search_query_top")).submit();
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[2]/h1"));
    }
    public void FailedSearch(String textToSearch){
        driver.findElement(By.id("search_query_top")).sendKeys(textToSearch);
        driver.findElement(By.id("search_query_top")).submit();
    }
    public String GetTitle(){
        return driver.getTitle();
    }
    public int CountFoundItems(){
        String counter = driver.findElement(By.className("heading-counter")).getText();
        counter = counter.replaceAll("\\D+","");
        return Integer.parseInt(counter);
    }
    public void ClickOnResult(int number){
        int counter=0;
        WebElement webElement = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[2]/ul"));
        while (driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div[2]/ul")).findElements(By.xpath(".//img")).size()<=number){
            counter++;
            if(counter>=8000){
                break;
            }
        }
        List<WebElement> webElements = webElement.findElements(By.xpath(".//img"));
        webElements.get(number).click();
        driver.findElement(By.id("bigpic"));
    }
    public WebElement AddToCart(){
        if(driver.findElements(By.id("add_to_cart")).size()==0){
            return null;
        }
        driver.findElement(By.name("Submit")).click();
        while(!driver.findElement(By.xpath("/html/body/div/div[1]/header/div[3]/div/div/div[4]/div[1]/div[2]/div[4]/a")).isDisplayed()){

        }
        driver.findElement(By.xpath("/html/body/div/div[1]/header/div[3]/div/div/div[4]/div[1]/div[2]/div[4]/a")).click();
        return driver.findElement(By.id("cart_title"));
    }

}
