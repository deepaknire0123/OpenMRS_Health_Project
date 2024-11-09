/**
 * 
 */
package testCases;

import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.DashboardPage;
import pageObjects.FindPatientRecordPage;
import pageObjects.LoginPage;
import pageObjects.RegisterNewPatientPage;
import pageObjects.RequestAppointmentPage;
import testBase.BaseClass;

/**
 * Find a patient and request for appointment also contains type-ahead or autocomplete text box.
 */
public class TC004_RequestAppointment extends BaseClass{
	
	LoginPage lp;
	DashboardPage dashboard;
	RegisterNewPatientPage reg;
	FindPatientRecordPage findPatientPage;
	RequestAppointmentPage reqAppointment;
	
	@Test
	public void requestAppointment() throws TimeoutException, InterruptedException
	{
		logger.info("**** Starting TC003_RegisterNewPatient ***** ");
		
		//Log into website
		lp = new LoginPage(driver);
		lp.enterUserName(p.getProperty("username"));
		lp.enterPassword(p.getProperty("password"));
		lp.selectLocation(p.getProperty("location"));
		logger.info("***** Entered Login Information ******");
		lp.clickLogin();
		logger.info("***** Clicked on Login *****");
		
		//Methods for searching patient
		dashboard = new DashboardPage(driver);
		dashboard.clickFindPatientRecord();
		logger.info("***** Clicked on Find Patient Record ******");
		
		//Find Patient Record
		findPatientPage = new FindPatientRecordPage(driver);
		findPatientPage.enterSearchPatientName("James Kelp");
		logger.info(" Entered Patient Name for Search ");
		
		//Select Patient
		reqAppointment = new RequestAppointmentPage(driver);
		reqAppointment.clickFirstRecord();
		logger.info("Selected Patient");
		
		//Request Appointment
		reqAppointment.clickRequestAppointment();
		logger.info("Clicked on request appointmet");
		
		//Enter Appointmet type
		reqAppointment.enterAppointmentType("d");
		logger.info("Entered appointment type");
		
		//select the appointment type
		reqAppointment.selectAppointmentType("Pediatrics");
		logger.info("Selected Appointment type");
		
		//confirm appointment
		reqAppointment.clickConfirmAppointment();
		logger.info("Confirmed appointment by clicking SAVE button");
		
		//Validating appointment
		boolean apointmentStatus = reqAppointment.validateAppointmentConfirmation("Pediatrics");
		if(apointmentStatus)
		{
			logger.info("Validated Appointment type");
			Assert.assertTrue(apointmentStatus, "Appointment not confirmed");
		}
		else
		{
			logger.warn("Appointment was not successful");
			Assert.fail("Appointment not confirmed");
		}
		
	//	Assert.assertTrue(apointmentStatus, "Appointment not confirmed for type: " + appointmentType);
		
		dashboard.clickHomePage();
		
		
	}

}
