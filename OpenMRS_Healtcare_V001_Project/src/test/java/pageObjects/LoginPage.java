/**
 * 
 */
package pageObjects;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Login to page with selecting Location/Department
 */
public class LoginPage extends BasePage{
	
	public LoginPage(WebDriver driver)
	{
		super(driver);
	}
	
	
	@FindBy(xpath = "//input[@id='username']")
	WebElement txtUserName;
	
	@FindBy(xpath = "//input[@id='password']")
	WebElement txtPassword;
	
	@FindBy(xpath = "//ul[@id='sessionLocation']/li")
	List<WebElement> locationOptions;
	
	@FindBy(xpath = "//span[@id='sessionLocationError']")
	WebElement errorSelectLocation;
	
	@FindBy(xpath = "//input[@id='loginButton']")
	WebElement btnLogin;
	
	@FindBy(xpath = "//div[@id='error-message']")
	WebElement errorLogin;
	
	
	public void enterUserName(String userName)
	{
		txtUserName.sendKeys(userName);
	}
	
	public void enterPassword(String password)
	{
		txtPassword.sendKeys(password);
	}
	
	//Method to select the location
	public void selectLocation(String locationName)
	{
		for(WebElement location:locationOptions)
		{
			if(location.getText().equals(locationName))
			{
				location.click();
				break;
			}
		}
	}
	
	//Method to verify if the specific location is selected
	public boolean isLocationSelected(String locationName)
	{
		for(WebElement location:locationOptions)
		{
			if(location.getText().equalsIgnoreCase(locationName) && location.getAttribute("class").contains("selected"))
			{
				return true;
			}
		}
		return false;
	}
	
	public String islocationErrorDisplayed()
	{
		return errorSelectLocation.getText();
	}
	
	public void clickLogin()
	{
		btnLogin.click();
	}
	
	public boolean isErrorMessageDisplayed()
	{
		try
		{System.out.println("About to wait");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(errorLogin));
			return errorLogin.isDisplayed() && !errorLogin.getText().isEmpty();
		}
		catch (NoSuchElementException e) 
		{
			return false;
		}
		catch (Exception e) 
		{
			return false;
		}
		
	}
	
	public String getErrorMessageText()
	{
		try
		{
			if(errorLogin.isDisplayed())
			{
				return errorLogin.getText();
			}
		}
		catch (Exception e) 
		{
			throw new RuntimeException("Error Occured on Login Page", e);
		}
		
		return null;
	}
}
