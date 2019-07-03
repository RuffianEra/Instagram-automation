import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {
    static int x = 0;
    public static void main(String[] args)throws Exception {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        //Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\chromedriver_75.0.3770.90.exe");

        while(true){
            pool.execute(() -> {
                try {
                    System.out.println(1);Thread.sleep(2000);
                    /*ChromeOptions chrome = new ChromeOptions();
                    chrome.addArguments("user-data-dir=D:\\test\\" + x++);

                    WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:9515"), chrome);
                    driver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
                    Thread.sleep(2000);
                    driver.findElement(By.xpath("//input[@aria-label='手机号、帐号或邮箱']")).sendKeys("belyy_bugakova");
                    Thread.sleep(2000);
                    driver.findElement(By.xpath("//input[@aria-label='密码']")).sendKeys("H2f04atcO");
                    Thread.sleep(2000);
                    JavascriptExecutor js = (JavascriptExecutor)driver;
                    js.executeScript("alert('我现在在服务器，这是第" + x +"个任务')");
                    System.out.println(x);
                    Thread.sleep(2000);
                    driver.quit();*/
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}