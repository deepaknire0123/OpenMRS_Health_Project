/**
 * 
 */
package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;

/**
 *  This test case verify the login to the website
 */
public class TC001_LoginPageTest extends BaseClass{
	
	LoginPage lp;
	DashboardPage dashboard;
	
	@Test(dataProvider = "LoginCredentials", dataProviderClass = DataProviders.class, groups = {"smoke","sanity"})
	public void verifyLOgin(String userName, String password, String location, String expectedResult)
	{
		logger.info("**** Starting TC001_LoginPageTest ***** ");
		lp = new LoginPage(driver);
		
		lp.enterUserName(userName);
		lp.enterPassword(password);
		lp.selectLocation(location);
		logger.info("***** Entered Login Information ******");
		
		// Verify location selected and click login
		if(lp.isLocationSelected(location))
		{
			lp.clickLogin();
			logger.info("***** Clicked on Login ******");
		}
		else
		{
			lp.islocationErrorDisplayed();
			logger.error("***** Location Not Selected ******");
			Assert.fail("Error During Login: Location Not Selected");
		}
		
		if(lp.isErrorMessageDisplayed())
		{
			String errorMsg = lp.getErrorMessageText();
			if (errorMsg == null || errorMsg.isEmpty()) 
			{
		        logger.error("Error Message on Login Page is empty.");
		        Assert.fail("Error During Login: No message provided");
		    } 
			else 
			{
		        logger.error("Error Message on Login Page: " + errorMsg);
		        Assert.fail("Error During Login: " + errorMsg);
		    }
//			logger.error("Error Message on Login Page: " +errorMsg );
//			Assert.fail("Error During Login");
		}
		
		// Validate dashboard elements for successful login
		dashboard = new DashboardPage(driver);
		if(dashboard.validateLogin())
		{
			logger.info("***** Validated Dashboard --> Login Successful ******");
			Assert.assertTrue(true, "Login Failed");
			dashboard.clickLogout();
		}
		else 
		{
			logger.info("***** Login Failed ******");
			Assert.fail("Login Failed");
		}
				
	}

}
