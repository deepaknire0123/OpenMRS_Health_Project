/**
 * 
 */
package testCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.DashboardPage;
import pageObjects.FindPatientRecordPage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;

/**
 * Check if the correct patient appears, and add tests for non-existent patients.
 * Matching patients appear in the list, and selecting one opens their record.
 */
public class TC002_PatientSearch extends BaseClass{
	
	LoginPage lp;
	DashboardPage dashboard;
	FindPatientRecordPage findPatientPage;
	
	@Test(dataProvider = "FindPatientRecord", dataProviderClass = DataProviders.class, groups = {"smoke"})
	public void searchPatient(String userName, String password, String location, String patientName, String expectedResult) throws InterruptedException
	{
		logger.info("**** Starting TC002_PatientSearch ***** ");
		
		//Log into website
		lp = new LoginPage(driver);
		lp.enterUserName(userName);
		lp.enterPassword(password);
		lp.selectLocation(location);
		logger.info("***** Entered Login Information ******");
		lp.clickLogin();
		logger.info("***** Clicked on Login *****");
		
		//Methods for searching patient
		dashboard = new DashboardPage(driver);
		dashboard.clickFindPatientRecord();
		logger.info("***** Clicked on Find Patient Record ******");
		
		//Find Patient Record
		findPatientPage = new FindPatientRecordPage(driver);
		
		findPatientPage.enterSearchPatientName(patientName);
		logger.info(" Entered Patient Name for Search ");
		
		boolean isPatientFound = findPatientPage.searchPatientInPaginatedResults(patientName); // Donald Martinaz
		if(isPatientFound)
		{
			logger.info("Patient found in search results.");
			dashboard.clickLogout();
		    Assert.assertTrue(isPatientFound, "Expected to find patient, but did not.");
		}
		else
		{
			logger.warn("Patient not found in search results.");
			dashboard.clickLogout();
		    Assert.fail("Patient was not found in any page.");
		}
		
//		//Total Number of Matching Rows 
//		findPatientPage.enterSearchPatientName("Helen Young");
//		int matchingPatientCount = findPatientPage.getTotalMatchingPatientCount("Helen Young");
//		logger.info("Total Number of rows matching 'Helen Young': "+matchingPatientCount);
//		
//		//Total Number of Matching Rows -> Using pagination message and string
//		findPatientPage.enterSearchPatientName("Martin");
//		int totalCount = findPatientPage.getTotalPatientCountFromPaginationText();
//		logger.info("Total number of rows matching 'Martin' from pagination text: " + totalCount);
		
	}
	
	

}
