import WebdriverIDE.TesTest;
import com.GUI.Entity.Relevance;
import com.GUI.Entity.URLData;
import com.GUI.Method.ReadFile;
import com.GUI.StartComment;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {
    static int y = 0;
    public static void main(String[] args)throws Exception {
        facebook();
    }

    public static void facebook() throws Exception{
        //List<URLData> ruldata = new ArrayList<>();
        //ruldata.add(new URLData("https://www.instagram.com/p/BzTtfS_hEyf/", "大家好"));
        //ruldata.add(new URLData("https://www.instagram.com/p/ByXBS_dD34z/", "你好"));
        //Relevance rel = new Relevance("1589627395@qq.com,0412andr,true,instagram".split(","), ruldata);
        //String fileURL = System.getProperty("user.dir") + "\\browser\\instagram\\1589627395@qq.com";
        List<URLData> ruldata = new ArrayList<>();
        ruldata.add(new URLData("https://www.facebook.com/110089804873/posts/10157553876494874", "你好"));
        ruldata.add(new URLData("https://www.facebook.com/138876992797995/posts/2606525389366464", "大家好"));
        Relevance rel = new Relevance("heshearpgkrf@hotmail.com,3EmoiB,true,facebook".split(","), ruldata);

        String fileURL = System.getProperty("user.dir") + "\\browser\\facebook\\heshearpgkrf@hotmail.com";
        int x = new File(fileURL).isDirectory()?1:0;

        ChromeOptions chrome = new ChromeOptions();
        chrome.addArguments("user-data-dir=" + fileURL, "--test-type", "--start-maximized", "disable-infobars");

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.managed_default_content_settings.images", 2);
        chrome.setExperimentalOption("prefs", prefs);

        //WebDriver driver = new RemoteWebDriver(new URL("http://127.0.0.1:9515"), chrome);

        //StartComment.instagram(driver, rel, x, fileURL);
        StartComment.loginFacebook(chrome, rel);
        StartComment.facebook(chrome, rel);
    }
}