/**
 * 
 */
package pageObjects;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * type-ahead or autocomplete text box.
 */
public class RequestAppointmentPage extends BasePage{
	
	public static final Logger logger = LogManager.getLogger(RequestAppointmentPage.class);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	public RequestAppointmentPage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(xpath = "//tbody/tr[1]/td[2]")
	WebElement firstPatientRecordEntry;
	
	@FindBy(xpath = "//div[contains(text(),'Request Appointment')]")
	WebElement lnkRequestAppointment;
	
	@FindBy(xpath = "//input[@id='appointment-type']")
	WebElement txtAppointmentType;
	
	@FindBy(xpath = "//ul[@role='listbox']/li") 
	List<WebElement> dropdownSuggestions;
	
	@FindBy(xpath = "//input[@id='save-button']")
	WebElement btnSave;
	
	@FindBy(xpath = "//ul[@id='miniPatientAppointmentRequests']")
	WebElement confirmAppointment;
	
	public void clickFirstRecord()
	{
		firstPatientRecordEntry.click();
	}
	
	public void clickRequestAppointment()
	{
		wait.until(ExpectedConditions.visibilityOf(lnkRequestAppointment));
		lnkRequestAppointment.click();
	}
	
	public void enterAppointmentType(String appointmentType)
	{
		txtAppointmentType.clear();
		txtAppointmentType.sendKeys(appointmentType);
	}
	
	public void selectAppointmentType(String appointmentType)
	{
		wait.until(ExpectedConditions.visibilityOfAllElements(dropdownSuggestions));
		boolean typeFound = false;
		
		for(WebElement appType : dropdownSuggestions)
		{
			if(appType.getText().equalsIgnoreCase(appointmentType))
			{
				appType.click();
				typeFound = true;
				break;
			}
		}
		if(!typeFound)
		{
			throw new NoSuchElementException("Appointment type '"+ appointmentType + "'not found in suggestions.");
		}
	}
	
	public void clickConfirmAppointment()
	{
		btnSave.click();
	}
	
	public boolean validateAppointmentConfirmation(String appointmentType)throws TimeoutException
	{
		try
		{
			wait.until(ExpectedConditions.visibilityOf(confirmAppointment));
			logger.info(confirmAppointment.getText());
			return confirmAppointment.getText().contains(appointmentType);
		}
		catch (NoSuchElementException e) 
		{
			logger.warn("Confirmation message was not displayed.");
			return false;
		}
	}
	
	//JaveScriptExecutor
//	public void clickElement(WebElement element) {
//	    try 
//	    {
//	        element.click();
//	    } catch (Exception e) 
//	    {
//	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
//	    }
//	}

}
