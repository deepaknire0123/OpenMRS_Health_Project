/**
 * 
 */
package pageObjects;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Elements of Find Patient Record
 */
public class FindPatientRecordPage extends BasePage{
	
	private static final Logger logger = LogManager.getLogger(FindPatientRecordPage.class);
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	
	public FindPatientRecordPage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(xpath = "//input[@id='patient-search']")
	WebElement txtSearchPatient;
	
	@FindBy(xpath = "//tbody/tr/td[2]")
	List<WebElement> lstPatientRecord;
	
	@FindBy(xpath = "//td[@class='dataTables_empty']")
	WebElement msgPatientRecordNotFound;
	
	@FindBy(xpath = "//a[@class='next fg-button ui-button ui-state-default']")
	WebElement lnkNext;
	
	@FindBy(xpath = "//a[@class='previous fg-button ui-button ui-state-default']")
	WebElement lnkPrevious;
	
	@FindBy(xpath = "//div[@id='patient-search-results-table_info']") 
	WebElement msgTotalEntries;
	
	
	public void enterSearchPatientName(String patientName)
	{
		txtSearchPatient.clear();
		txtSearchPatient.sendKeys(patientName);
		logger.info("Entered the Patient Name for the search: " + patientName);
	}
	
	public boolean isPatientInCurrentPage(String patientName)
	{
		try
		{
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOf(msgPatientRecordNotFound));
			if (msgPatientRecordNotFound.isDisplayed() && !msgPatientRecordNotFound.getText().isEmpty()) 
			{
				logger.info("No records found on the current page.");
	            return false; // No records found
	        }
		}
		catch (Exception e) 
		{
			// Ignore if not found
		}
		wait.until(ExpectedConditions.visibilityOfAllElements(lstPatientRecord));
		for(WebElement patient: lstPatientRecord)
		{
			if(patient.getText().equalsIgnoreCase(patientName))
			{
				logger.info("Patient " + patientName + " found on the current page.");
				return true;
			}
		}
		return false;
	}
	
	public boolean navigateToNextPage()
	{
		try
		{
			if(lnkNext.isDisplayed() && lnkNext.isEnabled())
			{
				wait.until(ExpectedConditions.elementToBeClickable(lnkNext));
				lnkNext.click();
				logger.info("Navigated to the next page.");
				return true;
			}
		}
		catch (Exception e) 
		{
			logger.warn("No 'Next' button available or failed to navigate.");
		}
		return false;
	}
	
	public boolean searchPatientInPaginatedResults(String patientName)
	{
		do {
			if(isPatientInCurrentPage(patientName))
			{
				logger.info("Patient found: " + patientName);
				return true; // Patient found on this page
			}
		} while (navigateToNextPage()); // Move to next page if available
		
		logger.info("Patient not found: " + patientName);
		return false; //// Patient not found in any pages
	}
	
	// Count matching rows on the current page============================================
	public int countMatchingPatientInCurrentPage(String patientName)
	{
		int count=0;
		try
		{
			wait.until(ExpectedConditions.visibilityOf(msgPatientRecordNotFound));
			if (msgPatientRecordNotFound.isDisplayed() && !msgPatientRecordNotFound.getText().isEmpty()) 
			{
				Thread.sleep(2000);
				logger.info("No records found on the current page.");
	            return count; // No records found
	        }
		}catch (Exception e) {
			//ignore if not
		}
		wait.until(ExpectedConditions.visibilityOfAllElements(lstPatientRecord));
		for(WebElement patient:lstPatientRecord)
		{
			if(patient.getText().equalsIgnoreCase(patientName))
			{
				count++;
			}
		}
		return count;	
	}
	
	// Total count across all pages
	public int getTotalMatchingPatientCount(String patientName)
	{
		int totalCount =0;
		do {
			// Count matching patients on the current page
			totalCount += countMatchingPatientInCurrentPage(patientName);
			
		} while (navigateToNextPage());
		return totalCount; // Total count across all pages
	}
	
	//Total Number row count using Pagination Maessage ===================================
	public int getTotalPatientCountFromPaginationText() throws InterruptedException
	{
		try
		{
			Thread.sleep(2000);
			String paginationText = wait.until(ExpectedConditions.visibilityOf(msgTotalEntries)).getText();
			System.out.println(paginationText);
			
			String[] parts = paginationText.split(" ");
			int tCount = Integer.parseInt(parts[5]);
			logger.info("Parsed total patient count: " + tCount);
			return tCount;
		}
		catch (NumberFormatException | ArrayIndexOutOfBoundsException e) 
		{
			logger.error("Failed to parse total count from pagination text: " + msgTotalEntries.getText(), e);
			return 0;
		}
	}
	

}
