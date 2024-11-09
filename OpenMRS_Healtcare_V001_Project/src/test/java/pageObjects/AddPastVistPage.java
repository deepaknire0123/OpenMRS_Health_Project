/**
 * 
 */
package pageObjects;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Calendar and uploading attachments
 */
public class AddPastVistPage extends BasePage{
	
	WebDriverWait wait  = new WebDriverWait(driver, Duration.ofSeconds(5));
	private static final Logger logger = LogManager.getLogger(AddPastVistPage.class);
	
	public AddPastVistPage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath = "//div[contains(text(),'Add Past Visit')]")
	WebElement lnkAddPastVisit;
	
	@FindBy(xpath = "//span[@id='retrospectiveVisitStartDate-wrapper']//span[@class='add-on']")
	WebElement retrospectiveVisitStartDateCalendarIcon;
	
	@FindBy(xpath = "//body[1]/div[2]/div[3]/table[1]/thead[1]/tr[1]/th[1]/i[1]")
	WebElement previousMonthStartDateIcon;
	
	@FindBy(xpath = "//body/div[2]/div[3]/table[1]/thead[1]/tr[1]/th[2]")
	WebElement calendarStartDateMonthYear;
	
	@FindBy(xpath = "//body[1]/div[2]/div[3]/table[1]/thead[1]/tr[1]/th[3]/i[1]")
	WebElement nextMonthStartDateIcon;
	
	@FindBy(xpath = "//body/div[2]/div[3]/table[1]/tbody/tr/td[@class='day']") //body/div[2]/div[3]/table[1]/tbody/tr/td[contains(@class, 'day')]
	List<WebElement> lstStartDate;
	
	@FindBy(xpath = "//span[@id='retrospectiveVisitStopDate-wrapper']//i[@class='icon-calendar small']")
	WebElement retrospectiveVisitStopDateCalendarIcon;
	
	@FindBy(xpath = "//body[1]/div[3]/div[3]/table[1]/thead[1]/tr[1]/th[1]/i[1]")
	WebElement previousMonthStopDateIcon;
	
	@FindBy(xpath = "//body[1]/div[3]/div[3]/table[1]/thead[1]/tr[1]/th[1]/i[1]")
	WebElement calendarStopDateMonthYear;
	
	@FindBy(xpath = "//body[1]/div[3]/div[3]/table[1]/thead[1]/tr[1]/th[3]/i[1]")
	WebElement nextMonthStopDateIcon;
	
	@FindBy(xpath = "//body/div[3]/div[3]/table[1]/tbody[1]/tr/td[@class='day']")
	List<WebElement> lstStopDate;
	
	@FindBy(xpath = "//div[@class='dialog-content form']//button[@class='confirm right'][normalize-space()='Confirm']")
	WebElement btnConfirm;
	
	@FindBy(xpath = "//li[@class='ui-state-default ui-corner-top ui-tabs-active ui-state-active']") //li[@class='ui-state-default ui-corner-top ui-tabs-active ui-state-active']/a
	WebElement msgValidateConfirmedVisit;
	
	@FindBy(xpath = "//a[@id='attachments.attachments.visitActions.default']")
	WebElement lnkAttachments;
	
	@FindBy(xpath = "//div[@class='dz-default dz-message ng-binding']")
	WebElement uploadFile;
	
	@FindBy(xpath = "//span[contains(text(),'.pdf')]")
	WebElement cnfDocumentUploaded;
	
	@FindBy(xpath = "//textarea[@placeholder='Enter a caption']")
	WebElement txtCaptionUploadFile;
	
	@FindBy(xpath = "//button[normalize-space()='Upload file']")
	WebElement btnUploadFile;
	
	@FindBy(xpath = "//div[@ng-hide='editMode']")
	WebElement msgDocumentUploadCaptionConfirmation;
	
	@FindBy(xpath = "//input[@type='file']")
	WebElement fileInput;
	
	public void clickAddPastVisit()
	{
		lnkAddPastVisit.click();
		logger.info("Clicked on Add Past Record Link");
	}
	
	public void selectStartDate(String month, String year, String startDate)
	{
		// Open the calendar
		retrospectiveVisitStartDateCalendarIcon.click();
		
		while(true)
		{
			//split the month and year
			String displayedMonthYear[] = calendarStartDateMonthYear.getText().split(" ");
			String displayedMonth = displayedMonthYear[0];
			String displayedYear = displayedMonthYear[1];
			
			// Check if the displayed month and year match the target month and year
			if(displayedMonth.equalsIgnoreCase(month) && displayedYear.equalsIgnoreCase(year))
			{
				logger.info("Month and year Matched");
				break;
			}
			
			 // Click to navigate to previous month if the year is too recent
			if(Integer.parseInt(displayedYear)>Integer.parseInt(year) || 
					(displayedYear.equalsIgnoreCase(year) && getMonthIndex(displayedMonth)>getMonthIndex(month) ))
			{
				previousMonthStartDateIcon.click();	
				logger.info("Navigating to previous months");
			}
			else
			{
				// Click to navigate to next month
	            //nextMonthStartDateIcon.click();
				break;
				// Handle unexpected scenario (displayed year and month are in the past)
	            //throw new RuntimeException("Target date is in the future. Cannot navigate to previous month.");
			}
		}
		
		for(WebElement day:lstStartDate)
		{
			if(day.getText().equals(startDate))
			{
				logger.info("date matches - Selected start date: " + startDate);
				day.click();
	            break;
			}
		}
	
	}
	
	public void selectStopDate(String month, String year, String stopDate)
	{
		retrospectiveVisitStopDateCalendarIcon.click();
		
		while(true)
		{
			String [] displayedMonthYear = calendarStopDateMonthYear.getText().split(" ");
			String displayedMonth = displayedMonthYear[0];
			String displayedYear = displayedMonthYear[1];
			
			if(displayedMonth.equalsIgnoreCase(month) && displayedYear.equalsIgnoreCase(year))
			{
				logger.info("Month and year Matched in start date");
				break;
			}
			
			if(Integer.parseInt(displayedYear) >Integer.parseInt(year) ||
					(displayedYear.equalsIgnoreCase(year) && getMonthIndex(displayedMonth)>getMonthIndex(month)))
			{
				logger.info("Navigating to previous months");
				previousMonthStopDateIcon.click();
			}
			else
			{
				break;
				//nextMonthStopDateIcon.click();
			}	
		}
		
		for(WebElement day:lstStopDate)
		{
			if(day.getText().equalsIgnoreCase(stopDate))
			{
				logger.info("date matches - Selected stop date "+stopDate);
				day.click();
				break;
			}
		}
	}
	
	// Helper method to convert month name to its index for easier comparison
		private int getMonthIndex(String month)
		{
			Map<String, Integer> monthMap = new HashMap<>();
	        monthMap.put("January", 1);
	        monthMap.put("February", 2);
	        monthMap.put("March", 3);
	        monthMap.put("April", 4);
	        monthMap.put("May", 5);
	        monthMap.put("June", 6);
	        monthMap.put("July", 7);
	        monthMap.put("August", 8);
	        monthMap.put("September", 9);
	        monthMap.put("October", 10);
	        monthMap.put("November", 11);
	        monthMap.put("December", 12);
	        
	        return monthMap.get(month);
		}
	

/** One of the way to write the helper method
 * 
	 private int getMonthIndex(String month) {
    return switch (month) {
        case "January" -> 1;
        case "February" -> 2;
        case "March" -> 3;
        case "April" -> 4;
        case "May" -> 5;
        case "June" -> 6;
        case "July" -> 7;
        case "August" -> 8;
        case "September" -> 9;
        case "October" -> 10;
        case "November" -> 11;
        case "December" -> 12;
        default -> 0; // Error handling
    };
 */
/**
 *  private static int getMonthIndex(String month) {
        switch (month.toLowerCase()) {
            case "january":
                return 1;
            case "february":
                return 2;
            case "march":
                return 3;
            case "april":
                return 4;
            case "may":
                return 5;
            case "june":
                return 6;
            case "july":
                return 7;
            case "august":
                return 8;
            case "september":
                return 9;
            case "october":
                return 10;
            case "november":
                return 11;
            case "december":
                return 12;
            default:   

            return 0; // Handle invalid month
        }
    }
 */
		
		
	public void clickCOnfirmButton()
	{
		btnConfirm.click();
		logger.info("Clicked on confirm button of Add past visit");
	}
	
	public boolean validatePastVistRecord()
	{
		logger.info("verifying display of past visit");
		return msgValidateConfirmedVisit.isDisplayed();
	}
	
	public void clickAttachment()
	{
		lnkAttachments.click();
		logger.info("Navigating to Attchment");
	}
	
	public void loadFile(String filePath)
	{
		uploadFile.click(); // Clicks the upload button if needed (e.g., to make the input visible) //Trigger file input visibility if needed
		fileInput.sendKeys(filePath); // Directly uploads the file by sending the path to the file input element // Set file path directly to input
		logger.info("Loading a file");
	}
	
	public boolean fileUploadConfirmation()
	{
		logger.info("Verifying document load");
		return cnfDocumentUploaded.isDisplayed();
	}
	
	public void fileUploadCaption(String cpation)
	{
		txtCaptionUploadFile.clear();
		txtCaptionUploadFile.sendKeys(cpation);
		logger.info("File upload description - "+cpation);
	}
	
	public void clickUploadFileButton()
	{
		btnUploadFile.click();
		logger.info("clicked on upload button");
	}
	
	public boolean validateFileUploadMessage(String expectedCaption) 
	{
	    String actualCaption = msgDocumentUploadCaptionConfirmation.getText().trim(); // Retrieve confirmation message text
	    logger.info("Validating file upload confirmation message. Expected: " + expectedCaption + ", Actual: " + actualCaption);
	    return actualCaption.equals(expectedCaption); // Compare actual with expected caption
	}
	
	
}
