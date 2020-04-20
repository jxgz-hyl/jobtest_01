import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.assertEquals;

public class BingTest {
    private WebDriver driver = null;

    @AfterClass
    public static void init() {
        System.setProperty("webdriver.chrome.driver", "D:Tools/chromedriver.exe");
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://cn.bing.com/");
    }

    @After
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void test1() {
        System.out.println(driver.getTitle());
        assertEquals("微软 Bing 搜索 - 国内版",driver.getTitle());
    }

    @Test
    public void test2() throws InterruptedException {
        driver.findElement(By.cssSelector("#est_en")).click();
        Thread.sleep(1000);
        WebElement webElement = driver.findElement(By.cssSelector("#scpl0"));
        System.out.println(webElement.getText());
        assertEquals("Images",webElement.getText());
    }

    @Test
    public void test3()  throws InterruptedException {
        driver.findElement(By.id("id_a")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("i0116")).sendKeys("179681503@qq.com");
        driver.findElement(By.id("idSIButton9")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("i0118")).sendKeys("123456");
        driver.findElement(By.id("idSIButton9")).click();
        Thread.sleep(1000);
        WebElement webElement = driver.findElement(By.id("passwordError"));
        assertEquals("你的帐户或密码不正确。如果你不记得你的密码， 请立即进行重置。",webElement.getText());
    }


    // 失败的测试
    @Test
    public void test4() throws InterruptedException {
        String str1="语文";
        String str2="数学";
        search(driver,str1);
        search(driver,str2);
        driver.findElement(By.id("sb_form_q")).click();
        // error：试了多种选择器都找不到元素，但在浏览器中可以找到
        driver.findElement(By.xpath("//*[@id=\"sa_5003\"]/div/div/a")).click();
        driver.navigate().refresh();
        Thread.sleep(1000);
        // error：选择器找不到元素，浏览器中可以找到
        WebElement webElement = driver.findElement(By.cssSelector("#sa_5003 > div > span"));
        String topRecord = webElement.getText();
        assertEquals(str1,topRecord);
    }

    public void search(WebDriver webDriver,String arg) throws InterruptedException {
        webDriver.findElement(By.id("sb_form_q")).sendKeys(arg);
        Thread.sleep(1000);
        webDriver.findElement(By.id("sb_form_go")).click();
        Thread.sleep(1000);
        webDriver.navigate().back();
        Thread.sleep(1000);
    }
}
