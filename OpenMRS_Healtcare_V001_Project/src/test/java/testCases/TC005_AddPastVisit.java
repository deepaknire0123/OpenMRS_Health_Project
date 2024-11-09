/**
 * 
 */
package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.AddPastVistPage;
import pageObjects.DashboardPage;
import pageObjects.FindPatientRecordPage;
import pageObjects.LoginPage;
import pageObjects.RegisterNewPatientPage;
import pageObjects.RequestAppointmentPage;
import testBase.BaseClass;

/**
 * Adding past visit records  and File of past record upload
 * Calendar and uploading attachments
 */
public class TC005_AddPastVisit extends BaseClass{
	
	LoginPage lp;
	DashboardPage dashboard;
	RegisterNewPatientPage reg;
	FindPatientRecordPage findPatientPage;
	RequestAppointmentPage reqAppointment;
	AddPastVistPage addPastVist;
	
	@BeforeMethod
	public void initialize()
	{
		logger.info("****** Initializing Page Objects @BeforeMethod ********");
		lp = new LoginPage(driver);
		dashboard = new DashboardPage(driver);
		reg = new RegisterNewPatientPage(driver);
		findPatientPage = new FindPatientRecordPage(driver);
		reqAppointment = new RequestAppointmentPage(driver);
		addPastVist = new AddPastVistPage(driver);
	}
	
	@Test
	public void addPastVist()
	{
		logger.info("**** Starting TC005_AddPastVisit ***** ");
		
		lp.enterUserName(p.getProperty("username"));
		lp.enterPassword(p.getProperty("password"));
		lp.selectLocation(p.getProperty("location"));
		logger.info("***** Entered Login Information ******");
		lp.clickLogin();
		logger.info("***** Clicked on Login *****");
		
		//Methods for searching patient
		dashboard.clickFindPatientRecord();
		logger.info("***** Clicked on Find Patient Record ******");
		
		//Find Patient Record and select patient
		findPatientPage.enterSearchPatientName("James Kelp");
		logger.info(" Entered Patient Name for Search ");
		reqAppointment.clickFirstRecord();
		logger.info("Selected Patient");
		
		//Add past visit
		addPastVist.clickAddPastVisit();
		addPastVist.selectStartDate("August", "2024", "12");
		addPastVist.selectStopDate("August", "2024", "14");
		addPastVist.clickCOnfirmButton();
		
		//validating past record
		boolean pastVisitStatus = addPastVist.validatePastVistRecord();
		if(pastVisitStatus)
		{
			logger.info("Past Visit Added successfully");
			Assert.assertTrue(true, "Failed to validate the past visit");
		}
		else
		{
			logger.error("Past visit failed to Add to the record");
			Assert.fail("Failed to add past records");
		}
		
		//Assert.assertTrue(addPastVist.validatePastVistRecord(), "Past visit record validation failed.");
		
		//Adding an attachment
		addPastVist.clickAttachment();
		addPastVist.loadFile("C:\\Doc2.pdf");
		
		//validating Loading the file
		boolean fileStatus = addPastVist.fileUploadConfirmation();
		if(fileStatus)
		{
			logger.info("File Loaded Successfully");
			Assert.assertTrue(true, "File did not load");
		}
		else
		{
			logger.error("File did not loaded");
			Assert.fail("File did not loaded");
		}
		
		//Assert.assertTrue(addPastVist.fileUploadConfirmation(), "File upload confirmation not displayed.");
		
		//Caption or description for file upload
		addPastVist.fileUploadCaption("fever");
		addPastVist.clickUploadFileButton();
		
		//Validate File Upload
		boolean fileUploadStatus = addPastVist.validateFileUploadMessage("fever");
		if(fileUploadStatus)
		{
			logger.info("File Uploaded Successfully");
			Assert.assertTrue(true, "File did not upload");
		}
		else
		{
			logger.error("File upload caption mismatch.");
			Assert.fail("File upload caption mismatch.");
		}
		
		//Assert.assertTrue(addPastVist.validateFileUploadMessage("fever"), "File upload caption mismatch.");
		
		dashboard.clickHomePage();
		
		
		
		
		
	}
	

}
