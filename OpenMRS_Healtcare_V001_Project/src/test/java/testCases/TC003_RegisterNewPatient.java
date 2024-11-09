/**
 * 
 */
package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import pageObjects.RegisterNewPatientPage;
import testBase.BaseClass;
import utilities.DataProviders;

/**
 * Verify that users can register a new patient and the patient record is created successfully.
 */
public class TC003_RegisterNewPatient extends BaseClass{
	
	LoginPage lp;
	DashboardPage dashboard;
	RegisterNewPatientPage reg;
	
	@Test(groups = {"sanity"}, dataProvider = "RegisterPatientData", dataProviderClass = DataProviders.class)
	public void registerPatient(String userName, String	password, String location, String firstName, String lastName, String gender, String date, 
			String month, String year, String address, String phone)
	{
		logger.info("**** Starting TC003_RegisterNewPatient ***** ");
		
		//Log into website
		lp = new LoginPage(driver);
		lp.enterUserName(userName);
		lp.enterPassword(password);
		lp.selectLocation(location);
		logger.info("***** Entered Login Information ******");
		lp.clickLogin();
		logger.info("***** Clicked on Login *****");
		
		//Register New Patient
		reg = new RegisterNewPatientPage(driver);
		reg.clickRegisterPatient();
		logger.info("Clicked on Register Patient");
		
		reg.enterFirstLastName(firstName, lastName);
		reg.clickNext();
		logger.info("Entered Name information");
		
		reg.selectGender(gender);
		reg.clickNext();
		logger.info("Selected gender");
		
		reg.enterPatinetBirthDate(date, month, year);
		reg.clickNext();
		logger.info("Birthday information entered");
		
		reg.engterAddress(address);
		reg.clickNext();
		logger.info("Address info entered");
		
		reg.enterPhone(phone);
		reg.clickNext();
		logger.info("Phone number added");
		
		reg.clickNext();
		logger.info("Clikcked on Relative next - info not added");
		
		reg.clickSubmit();
		logger.info("Confirmed clicking on submit");
		
		boolean isRegPageDisplayed = reg.isPatientRegInfoPageDisplayed();
		if(isRegPageDisplayed)
		{
			logger.info("Registration Success - Registration Patient Page displayed");
			Assert.assertTrue(true, "Expected to Display registration Patient Page, but did not");
		}
		else
		{
			logger.warn("Registration unuccessful - Registration Patient Page did not display");
			Assert.fail("Registration unuccessful");
		}
		
		dashboard = new DashboardPage(driver);
		dashboard.clickHomePage();
		
		//Logout
		dashboard.clickLogout();
		logger.info("**** Finished TC003_RegisterNewPatient ***** ");
		
	}

}
