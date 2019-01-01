package Test_GroupID_01.TestNG_project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	 System.out.println( "Hello World!" );
    	 System.setProperty("webdriver.gecko.driver", "C:\\Users\\Administrator.xmltest-PC\\Downloads\\geckodriver.exe");
    	 WebDriver driver = new FirefoxDriver();
    	 driver.get("https://www.google.com/");  
    }
}
