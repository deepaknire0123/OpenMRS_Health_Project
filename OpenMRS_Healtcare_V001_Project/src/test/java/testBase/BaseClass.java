/**
 * 
 */
package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Common functions used for test case scripts are written here
 */
public class BaseClass {
	
	public WebDriver driver;
	public static Logger logger;
	public Properties p;
	
	@BeforeClass(groups = {"smoke", "sanity", "regression"})
	@Parameters({"browser"})
	public void setup(String br) throws IOException
	{		
		logger = LogManager.getLogger(this.getClass());
		
		FileReader file = new FileReader("./src/test/resources/config.properties");
		p = new Properties();
		p.load(file);
		
		logger.info("================================TEST CASE STARTED====================================");
		
		switch(br.toLowerCase())
		{
		case "chrome" :
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(); 
			break;
		case "edge" : 
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver(); 
			break;
		case "firefox" : 
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(); 
			break;
			
		default : System.out.println("Invalid browser..."); return; // It will exit form the execution
		}
		
		driver.manage().window().maximize();
		driver.get(p.getProperty("url"));
		driver.manage().deleteAllCookies();
		
		logger.info("******* Browser Launched Successfully *********");
		
	}
	
	@AfterClass(groups = {"smoke", "sanity", "regression"})
	public void teaeDown()
	{
		if(driver != null)
		{
			driver.quit();
			logger.info("********* Browser Closed Successfully *********");
		}
		
		logger.info("================================TEST CASE ENDED=======================================");
	}
	
	public String captureScreen (WebDriver driver, String tname) throws IOException 
	{
		logger.info("Attempting to capture screenshot...");
		// Generate timestamp for the file name
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd-hh_mm_ss").format(new Date());
		
		// Capture screenshot
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver; 
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		// Define the target file path and name
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);
		
		// Copy the screenshot to the target location using Files.copy()
		//Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

		sourceFile.renameTo(targetFile); //copy the source file into target file
		
		logger.info("Screenshot captured at: " + targetFilePath);
		
		return targetFilePath; //returning where exactly screenshot is located
	}
	
	
	

}
