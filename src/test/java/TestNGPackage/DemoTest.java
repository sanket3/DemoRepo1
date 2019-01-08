package TestNGPackage;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import junit.framework.Assert;


//Driver Manager
 abstract class DriverManager {

    protected WebDriver driver;
    protected abstract void startService();
    protected abstract void stopService();
    protected abstract void createDriver();

    public void quitDriver() {
        if (null != driver) {
            driver.quit();
            driver = null;
        }

    }

    public WebDriver getDriver() {
        if (null == driver) {
            startService();
            createDriver();
        }
        return driver;
    }
}


//Chrome Driver Manager
class ChromeDriverManager extends DriverManager {

    private ChromeDriverService chService;

    @Override
    public void startService() {
        if (null == chService) {
            try {
                chService = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File("C:\\Users\\Administrator.xmltest-PC\\Downloads\\chromedriver.exe"))
                    .usingAnyFreePort()
                    .build();
                chService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stopService() {
        if (null != chService && chService.isRunning())
            chService.stop();
    }

    @Override
    public void createDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(chService, capabilities);
    }

}







//Firefox Driver Manager
class GeckoDriverManager extends DriverManager {

   private GeckoDriverService ffService;

  @Override
  public void startService() {
      if (null == ffService) {
          try {
        	  ffService = new GeckoDriverService.Builder()
                  .usingDriverExecutable(new File("C:\\Users\\Administrator.xmltest-PC\\Downloads\\geckodriver.exe"))
                  .usingAnyFreePort()
                  .build();
        	  ffService.start();
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
  }

  @Override
  public void stopService() {
      if (null != ffService && ffService.isRunning())
    	  ffService.stop();
  }

  @Override
  public void createDriver() {
      DesiredCapabilities capabilities = DesiredCapabilities.firefox();
      FirefoxOptions options = new FirefoxOptions();      
      options.addArguments("test-type");
      capabilities.setCapability(ChromeOptions.CAPABILITY, options);
      driver = new FirefoxDriver(ffService, capabilities);
      
  }

}











/*
enum DriverType {
    CHROME,
    FIREFOX    
}
*/

//Driver Manager Factory
class DriverManagerFactory
{	

    public static DriverManager getManager(String type) 
    {

        DriverManager driverManager;
        
         if(type.equalsIgnoreCase("chrome"))
          {
    	    driverManager = new ChromeDriverManager();    
    	    System.out.println("Chrome was selected");
          }
           
         else
          {
            driverManager = new GeckoDriverManager();
            System.out.println("Firefox was selected");
            
          }
        
         return driverManager;
     }
}






public class DemoTest 
    
{
	 
	DriverManager driverManager;
	
    WebDriver driver;
    //String browser;    
    String Browser="chrome";

    @BeforeTest
    public void beforeTest() {
    	
    	//driverManager = DriverManagerFactory.getManager(System.getProperty("Browser"));
    	//System.out.println("Jenkin has passed following browser:" + driverManager);
    	
       //driverManager = DriverManagerFactory.getManager(browser);   	
    	// driverManager = DriverManagerFactory.getManager("Browser");   	
    	
    	driverManager = DriverManagerFactory.getManager(Browser);   
       
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = driverManager.getDriver();
    }

    @AfterMethod
    public void afterMethod() {
        driverManager.quitDriver();
    }

    @Test
    public void launchTestAutomationGuruTest() {
        driver.get("http://testautomationguru.com");
        Assert.assertEquals("TestAutomationGuru – A technical blog on test automation", driver.getTitle());
    }

    @Test
    public void launchGoogleTest() {
        driver.get("https://www.google.com");
        Assert.assertEquals("Google", driver.getTitle());
    }

    @Test
    public void launchYahooTest() {
        driver.get("https://www.yahoo.com");
        Assert.assertEquals("Yahoo", driver.getTitle());
    }

}

	


