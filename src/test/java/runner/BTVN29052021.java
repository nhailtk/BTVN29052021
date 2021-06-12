package runner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BTVN29052021 {
    WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Driver\\webdrivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
    }

    @Test
    public void login_standard_user() {
        login("standard_user","secret_sauce");
        WebElement shoppingCart = driver.findElement(By.id("shopping_cart_container"));
        Assert.assertTrue(shoppingCart.isDisplayed());
    }

    @Test
    public void login_locked_out_user() {
        login("locked_out_user","secret_sauce");
        WebElement errorLabel = driver.findElement(By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]/h3[1]"));
        Assert.assertEquals("Epic sadface: Sorry, this user has been locked out.", errorLabel.getText());
    }

    @Test
    public void login_problem_user() {
        login("abc_user","secret_sauce");
        WebElement errorLabel = driver.findElement(By.xpath("//div[@class='login-box']//h3"));
        Assert.assertEquals("Epic sadface: Username and password do not match any user in this service", errorLabel.getText());
    }
    
    @Test
    public void login_problem_password() {
        login("standard_user","wrong_sauce");
        WebElement errorLabel = driver.findElement(By.xpath("//div[@class='login-box']//h3"));
        Assert.assertEquals("Epic sadface: Username and password do not match any user in this service", errorLabel.getText());
    }
    
    @Test
    public void login_with_userName_blank(){
        login("","secret_sauce");
        WebElement errorLabel = driver.findElement(By.xpath("//div[@class='login-box']//h3"));
        Assert.assertEquals("Epic sadface: Username is required", errorLabel.getText());
    }
    @Test
    public void login_with_passWord_blank(){
        login("locked_out_user","");
        WebElement errorLabel = driver.findElement(By.xpath("//div[@class='login-box']//h3"));
        Assert.assertEquals("Epic sadface: Password is required", errorLabel.getText());
    }
    @Test
    public void login_with_userName_passWord_blank(){
        login("","");
        WebElement errorLabel = driver.findElement(By.xpath("//div[@class='login-box']//h3"));
        Assert.assertEquals("Epic sadface: Username is required", errorLabel.getText());
    }
    @After
    public void closeBroswer() {
        driver.quit();
    }
    public void login(String userName, String passWord){
        WebElement txtUserName = driver.findElement(By.id("user-name"));
        WebElement txtPassWord = driver.findElement(By.id("password"));
        WebElement btnLogin = driver.findElement(By.id("login-button"));
        txtUserName.sendKeys(userName);
        txtPassWord.sendKeys(passWord);
        btnLogin.click();
    }
}
