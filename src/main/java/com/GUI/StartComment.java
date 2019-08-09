package com.GUI;

import com.GUI.Entity.Relevance;
import com.GUI.Entity.URLData;
import com.GUI.Method.ReadFile;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StartComment {
    public static ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 2, TimeUnit.MINUTES, new LinkedBlockingDeque<>());

    public static void run() {

        for(Map.Entry entry: ReadFile.relevanceMap.entrySet()) {
            Relevance relValue = (Relevance) entry.getValue();
            if(!relValue.isOn_off()){ continue; }
            pool.execute(() -> {
                try {
                    String fileURL = System.getProperty("user.dir") + "\\browser\\" + ReadFile.relevanceMap.get(entry.getKey()).getWebsite() + "\\" + entry.getKey();

                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    prefs.put("profile.managed_default_content_settings.images", 2);
                    ChromeOptions chrome = new ChromeOptions();
                    chrome.addArguments("--user-data-dir=" + fileURL, "--test-type", "--start-maximized", "--disable-infobars" + "--user-agent= Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 10.0; Maxthon 2.0)");
                    chrome.setExperimentalOption("prefs", prefs);

                    /** 先判断该帐号是否有缓存 */
                    if(!new File(fileURL).isDirectory()){
                        if(relValue.getWebsite().equals("facebook")){
                            loginFacebook(chrome, relValue);
                        }
                        else if(relValue.getWebsite().equals("instagram")){
                            loginInstagram(chrome, relValue);
                        }
                    }

                    /** 直接使用缓存帐号登陆评论 */
                    if(relValue.getWebsite().equals("facebook")){
                        facebook(chrome, relValue);
                    }
                    else if(relValue.getWebsite().equals("instagram")){
                        instagram(chrome, relValue);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * facebook网站评论接口
     * @param chrome    通用配置参数
     * @param relValue  评论的用户数据信息
     * @throws Exception
     */
    public static void facebook(ChromeOptions chrome, Relevance relValue) throws Exception{
        WebDriver driver;
        for(URLData urlData:relValue.getUrlData()) {
            driver = new RemoteWebDriver(new URL("http://127.0.0.1:9515"), chrome);
            /** 设置隐式等待，等待当前页面所有元素加载完成 */
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.get(urlData.url);
            driver.findElement(By.xpath("//div[@data-testid='UFI2ComposerInput/comment:proxy-input'][@contenteditable='true']")).sendKeys(urlData.data, Keys.ENTER);
            Thread.sleep(10000);
            driver.close();
            driver.quit();
        }
    }

    /**
     * instagram网站评论接口
     * @param chrome    通用配置参数
     * @param relValue  评论的用户数据信息
     * @throws Exception
     */
    public static void instagram(ChromeOptions chrome, Relevance relValue) throws Exception {
        WebDriver driver;
        for(URLData urlData:relValue.getUrlData()) {
            driver = new RemoteWebDriver(new URL("http://127.0.0.1:9515"), chrome);
            /** 设置隐式等待，等待当前页面所有元素加载完成 */
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.get(urlData.url);
            driver.findElement(By.xpath("//textarea[@aria-label='添加评论...'][@placeholder='添加评论...'][@class='Ypffh']")).sendKeys(urlData.data);
            driver.findElement(By.xpath("//button[@class='_0mzm- sqdOP yWX7d        '][@type='submit']")).click();
            Thread.sleep(10000);
            driver.close();
            driver.quit();
        }
    }

    /**
     * facebook帐号登陆
     * @param chrome    通用配置参数
     * @param relValue  评论的用户数据信息
     * @throws Exception
     */
    public static void loginFacebook(ChromeOptions chrome,  Relevance relValue) throws Exception {
        WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:9515"), chrome);
        driver.get("https://www.facebook.com/");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys(relValue.getUser());
        driver.findElement(By.xpath("//input[@id='pass']")).sendKeys(relValue.getPassword());
        driver.findElement(By.xpath("//input[@aria-label='登录'][@value='登录']")).click();
        Thread.sleep(10000);
        driver.close();
        driver.quit();
    }

    /**
     * instagram帐号登陆
     * @param chrome    通用配置参数
     * @param relValue  评论的用户数据信息
     * @throws Exception
     */
    public static void loginInstagram(ChromeOptions chrome, Relevance relValue) throws Exception{
        WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:9515"), chrome);
        driver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
        driver.findElement(By.xpath("//input[@aria-label='手机号、帐号或邮箱']")).sendKeys(relValue.getUser());
        driver.findElement(By.xpath("//input[@aria-label='密码']")).sendKeys(relValue.getPassword());
        driver.findElement(By.xpath("//button[@class='_0mzm- sqdOP  L3NKy       ']")).click();
        Thread.sleep(10000);
        driver.close();
        driver.quit();
    }
}