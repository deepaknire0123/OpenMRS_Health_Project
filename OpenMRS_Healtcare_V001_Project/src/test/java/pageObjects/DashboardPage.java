/**
 * 
 */
package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * 
 */
public class DashboardPage extends BasePage{

	public DashboardPage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(xpath = "//h4[contains(normalize-space(text()), 'Logged in as')]")
	WebElement msgLoggedIn;
	
	@FindBy(xpath = "//a[normalize-space()='Logout']")
	WebElement lnkLogout;
	
	@FindBy(xpath = "//a[@id='coreapps-activeVisitsHomepageLink-coreapps-activeVisitsHomepageLink-extension']")
	WebElement btnFindPatientRecord;
	
	@FindBy(xpath = "//i[@class='icon-home small']")
	WebElement iconHomePage;
	
	
	public boolean validateLogin()
	{
		return msgLoggedIn.isDisplayed();
	}
	
	public void clickLogout()
	{
		lnkLogout.click();
	}
	
	public void clickFindPatientRecord()
	{
		btnFindPatientRecord.click();
	}
	
	public void clickHomePage()
	{
		iconHomePage.click();
	}
}
