/**
 * 
 */
package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

/**
 * Data providers used to get the data from excel
 */
public class DataProviders {
	
	/**
	 * Data Provider 1 - Finding the Patients on Record
	 * @return 
	 * @throws IOException 
	 */
	@DataProvider(name = "FindPatientRecord")
	public Object[][] getData() throws IOException
	{
		String path = ".\\testData\\OpenMRS_Data.xlsx";
		
		ExcelUtility xlUtils = new ExcelUtility(path);
		
		int totalRows = xlUtils.getRowCount("SearchPatient");
		int totalCols = xlUtils.getCellCount("SearchPatient", 1);
		
		Object [][] Data = new Object[totalRows][totalCols];
		
		for(int r=1;r<=totalRows;r++)
		{
			for(int c=0;c<totalCols;c++)
			{
				Data[r-1][c] = xlUtils.getCellData("SearchPatient", r, c);
			}
		}
		return Data;
	}
	
	/**
	 * Data Provider 2
	 * @return 
	 * @throws IOException 
	 */
	@DataProvider(name = "LoginCredentials")
	public Object[][] getLoginData() throws IOException
	{
		String path = ".\\testData\\OpenMRS_Data.xlsx";
		
		ExcelUtility xlUtils = new ExcelUtility(path);
		
		int totalRow = xlUtils.getRowCount("Login");
		int totalCols = xlUtils.getCellCount("Login", 1);
		
		Object[][] Data = new Object[totalRow][totalCols];
		
		for(int r=1;r<=totalRow;r++)
		{
			for(int c=0;c<totalCols;c++)
			{
				Data[r-1][c] = xlUtils.getCellData("Login", r, c);
			}
		}
		return Data;
	}
	
	
	/**
	 * Data Providers - 3
	 * @return 
	 * @throws IOException 
	 */
	@DataProvider(name="RegisterPatientData")
	public Object[][] getRegisterData() throws IOException
	{
		String path = ".\\testData\\OpenMRS_Data.xlsx";
		
		ExcelUtility xlUtils = new ExcelUtility(path);
		
		int totalRows = xlUtils.getRowCount("RegisterPatient");
		int totalCols = xlUtils.getCellCount("RegisterPatient", 1);
		
		Object[][] Data = new Object[totalRows][totalCols];
		
		for(int r=1;r<=totalRows;r++) //Excel r=0 ,c = 1 //java r=0, c=0
		{
			for(int c=0;c<totalCols;c++)
			{
				Data[r-1][c] = xlUtils.getCellData("RegisterPatient", r, c);
			}
		}
		return Data;
	}
	
	

}
