package com.GUI;

import com.GUI.Entity.Relevance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StartComment {
    public static ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 2, TimeUnit.MINUTES, new LinkedBlockingDeque<>());

    public static void run() {
        //try { Runtime.getRuntime().exec("java -jar " + System.getProperty("user.dir") + "\\selenium-server-standalone-3.141.59.jar"); } catch (IOException e){  }

        for(Map.Entry entry:Chart.relevance.entrySet()) {
            Relevance relValue = (Relevance) entry.getValue();
            if(!relValue.isOn_off()){ continue; }
            pool.execute(() -> {
                try {
                    String fileURL = System.getProperty("user.dir") + "\\browser\\" + entry.getKey();
                    int x = new File(fileURL).isDirectory()?1:0;

                    ChromeOptions chrome = new ChromeOptions();
                    chrome.addArguments("user-data-dir=" + fileURL);

                    WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:9515"), chrome);
                    switch (x) {
                        case 0:
                            driver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
                            driver.findElement(By.xpath("//input[@aria-label='手机号、帐号或邮箱']")).sendKeys(relValue.getUser());
                            driver.findElement(By.xpath("//input[@aria-label='密码']")).sendKeys(relValue.getPassword());
                            driver.findElement(By.xpath("//button[@class='_0mzm- sqdOP  L3NKy       ']")).click();
                        case 1:
                            driver.get(relValue.getUrl());
                            driver.findElement(By.xpath("//textarea")).sendKeys(relValue.getData());
                            driver.findElement(By.xpath("//button[@class='_0mzm- sqdOP yWX7d        ']")).click();
                        default:
                            driver.quit();
                    }
                }
                catch (IOException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            });
        }
    }
}
