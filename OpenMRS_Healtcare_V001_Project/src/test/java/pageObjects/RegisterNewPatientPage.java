/**
 * 
 */
package pageObjects;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 */
public class RegisterNewPatientPage extends BasePage{
	
	private static final Logger logger = LogManager.getLogger(RegisterNewPatientPage.class);
	
	public RegisterNewPatientPage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(xpath = "//a[@id='referenceapplication-registrationapp-registerPatient-homepageLink-referenceapplication-registrationapp-registerPatient-homepageLink-extension']")
	WebElement btnRegisterPatient;
	
	@FindBy(xpath = "//input[@name='givenName']")
	WebElement txtFirstName;
	
	@FindBy(xpath = "//input[@name='familyName']")
	WebElement txtLastName;
	
	@FindBy(xpath = "//span[@id='fr4331']")
	WebElement errorRequiredFirstName;
	
	@FindBy(xpath = "//span[@id='fr9507']")
	WebElement errorReuiredLastName;
	
	@FindBy(xpath = "//select[@id='gender-field']")
	WebElement drpdownSelectGender;
	
	@FindBy(xpath = "//span[@id='fr64']")
	WebElement errorReuiredGender;
	
	@FindBy(xpath = "//input[@id='birthdateDay-field']")
	WebElement txtBirthDate;
	
	@FindBy(xpath = "//select[@id='birthdateMonth-field']")
	WebElement drpDownSelectBirthMonth;
	
	@FindBy(xpath = "//input[@id='birthdateYear-field']")
	WebElement txtBirthYear;
	
	@FindBy(xpath = "//input[@name='address1']")
	WebElement txtAddress;
	
	@FindBy(xpath = "//input[@name='phoneNumber']")
	WebElement txtPhone;
	
	@FindBy(xpath = "//input[@id='cancelSubmission']")
	WebElement btnCancelSubmission;
	
	@FindBy(xpath = "//button[@id='next-button']")
	WebElement btnNext;
	
	@FindBy(xpath = "//input[@id='submit']")
	WebElement btnConfirm;
	
	@FindBy(xpath = "//h3[normalize-space()='DIAGNOSES']")
	WebElement msgRegsiterSucess;	
	
	
	public void clickRegisterPatient()
	{
		btnRegisterPatient.click();
	}
	
	public void enterFirstLastName(String FName, String LName)
	{
		txtFirstName.sendKeys(FName);
		txtLastName.sendKeys(LName);
	}
	
	public void clickNext()
	{
		btnNext.click();
	}
	
	public void selectGender(String gender)
	{
		Select genderSelect = new Select(drpdownSelectGender);
		genderSelect.selectByVisibleText(gender);
	}
	
	//clickNext()

	public void enterPatinetBirthDate(String date, String month, String birthYear)
	{
		txtBirthDate.sendKeys(date);
		
		Select monthSelect = new Select(drpDownSelectBirthMonth);
		monthSelect.selectByValue(month);
		
		txtBirthYear.sendKeys(birthYear);
	}
	
	//clickNext()
	
	public void engterAddress(String address)
	{
		txtAddress.sendKeys(address);
	}
	//clickNext()
	
	public void enterPhone(String phoneNumber)
	{
		txtPhone.sendKeys(phoneNumber);
	}
	//clickNext()
	//relative clickNext()
	
	public void clickSubmit()
	{
		btnConfirm.click();
	}
	
	public boolean isPatientRegInfoPageDisplayed()
	{
		WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(msgRegsiterSucess));
		
		if(msgRegsiterSucess.isDisplayed() && !msgRegsiterSucess.getText().isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	

	
	
	
	

}
