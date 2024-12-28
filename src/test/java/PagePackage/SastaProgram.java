package PagePackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;

import UtilityPackage.ExcelUtil;

public class SastaProgram {
	
	    WebDriver driver;
	    ExcelUtil excelutil;
	    
	    @FindBy(xpath = "//a[contains(text(), 'Kerala')]")
	    WebElement keralaOption;

	  

	    @FindBy(xpath = "//*[@id=\"menuDropdownLinks\"]")
	    WebElement userMenuButton;

	    @FindBy(xpath = "/html/body/div[1]/div[2]/div/nav/ul/li[2]/div/a[1]")
	    WebElement loginOption;

	    @FindBy(name = "email")
	    WebElement emailField;

	    @FindBy(id = "password")
	    WebElement passwordField;

	    @FindBy(name = "submit")
	    WebElement loginButton;
	   

	    public SastaProgram(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	    }
	    public void SetLoginValues(String email, String password) throws InterruptedException{
	    	userMenuButton.click();
	    	Thread.sleep(1000);
	    	loginOption.click();
	    	Thread.sleep(1000);
	    	emailField.sendKeys(email);
	    	passwordField.sendKeys(password);
	    	 loginButton.click();
	    	
	    
	    	
	    	
	    	    }
	    
	    	    //(1)Scroll down
	    //in the flight scroll down to packages and click kerala 
	    public void ScrollDownClick() {
	        try {
	            // Wait for the "Packages" element to be present
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	            wait.until(ExpectedConditions.visibilityOf(keralaOption));

	            // Scroll to the "Packages" element
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", keralaOption);

	            // Debugging: Highlight the "Packages" element to ensure it's located
	            ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", keralaOption);

	            // Wait for the element to be clickable
	            wait.until(ExpectedConditions.elementToBeClickable(keralaOption));

	            // Click the "Packages" element
	            keralaOption.click();

	            // Log success message for debugging
	            System.out.println("Successfully clicked on 'Packages' and selected Kerala.");

	        } catch (Exception e) {
	            // Print the stack trace for debugging
	            e.printStackTrace();
	            System.out.println("Failed to scroll or click on 'Packages'. Please verify the XPath or page behavior.");
	        }
	    }
        //(2)Window Handle
//first in the hotel page we will search kochi and then after loading the window is handled back to parent page 
	    public void WindowHandleClick() {
	       

	    	
	    		    	String parentWindow=driver.getWindowHandle();
	    		    	System.out.println("Parent window title"+driver.getTitle());
	    		    	driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[2]/a/p")).click();
	    		    	
	    		    	Set<String> allWindows=driver.getWindowHandles();
	    		    	for(String handle:allWindows) {
	    		    		if(!handle.equalsIgnoreCase(parentWindow)) {
	    		    			driver.switchTo().window(handle);
	    		    			driver.findElement(By.xpath("//*[@id=\"hotels-destination-whitelabel_en\"]")).sendKeys("kochi"  + Keys.RETURN);
	    		    			driver.close();
	    		    		}
	    		    			
	    		    		}
	    		    		driver.switchTo().window(parentWindow);
	    		    		String expectedUrl = "https://www.sastasafar.com";
	    		    		if(!driver.getCurrentUrl().equals(expectedUrl))
	    		    		{
	    		    			driver.get(expectedUrl);
	    		    		}
	    		    	}
	    //(3)Assertion
	    //then assertion of title verification
	    public void TitleVerification()
	    {
	        

	        String actualTitle = driver.getTitle();
	        String expectedTitle = "Book Cheap Flights, Cheap Air Tickets, Lowest Airfare - sastasafar.com";
	        AssertJUnit.assertEquals(actualTitle, expectedTitle);
	        System.out.println("Title verification passed");
	    }
	    

	   //(4)copy and paste
	    //type newdelhi in from  flight then copy and paste in to flight
	    public void CopyPaste()
		{
			Actions act=new Actions(driver);
			WebElement From=driver.findElement(By.xpath("//*[@id=\"origin\"]"));
			From.sendKeys("New Delhi");
			WebElement to=driver.findElement(By.xpath("//*[@id=\"destination\"]"));
			
			act.keyDown(From, Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL);
		    act.keyDown(From, Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL);
		    act.keyDown(to, Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL);
		    act.perform();
		
			
		}
	    //(5) Data Driven test
	    //take email and password from excel sheet
	    
	    
		public void DataDrivenTest () throws IOException
		{
	    	driver.get("https://www.sastasafar.com/portal/login");
			FileInputStream fi=new FileInputStream("C:\\Users\\kjh49\\OneDrive\\Desktop\\Book1.xlsx");
			
			 
			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(fi);
		        XSSFSheet sh = wb.getSheet("Sheet1");

		        int rowCount = sh.getLastRowNum();

		        for (int i = 1; i <= rowCount; i++) {
		            String Username = sh.getRow(i).getCell(0).getStringCellValue();
		            String Password = sh.getRow(i).getCell(1).getStringCellValue();

		            System.out.println("Username: " + Username);
		            System.out.println("Password: " + Password);

		            try {
		                // Wait for elements
		                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		                // Clear and enter email
		                WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
		                emailField.clear();
		                emailField.sendKeys(Username);

		                // Clear and enter password
		                WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
		                passwordField.clear();
		                passwordField.sendKeys(Password);

		                // Submit login form
		                WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("submit")));
		                submitButton.click();

		                // Wait for login result and navigate back
		                Thread.sleep(2000); // Optional; replace with condition
		                driver.get("https://www.sastasafar.com/portal/login");
		            } catch (Exception e) {
		                System.out.println("Failed for Username: " + Username);
		                e.printStackTrace();
		            }
		        }
		}
	    //(6)Drop down
	    //in the flight page click dropdown on travellers and cabin and under class click dropdown and select first.
	    
	    public void dropDownHandling() {
	    	driver.get("https://www.sastasafar.com/flight-deal");
	    	// Step 1: Click the dropdown menu to make it visible
	    	WebElement dropdownToggle = driver.findElement(By.xpath("//*[@id=\"traveller\"]"));

	        dropdownToggle.click();

	        // Step 2: Locate the "Class" dropdown (the <select> tag)
	        WebElement classDropdown = driver.findElement(By.xpath("//*[@id=\"classi\"]")); // Use correct XPath if needed

	        // Step 3: Create a Select object for the dropdown
	        Select classDetails = new Select(classDropdown);
	        

	        // Step 4: Select the desired option (e.g., "first") by visible text
	        classDetails.selectByVisibleText("First");

	        // Step 5: Click the "Done" button to close the dropdown
	        WebElement doneButton = driver.findElement(By.xpath("//*[@id=\"Search_Flights\"]/div[3]/div[8]/div/div[5]/input"));
	        doneButton.click();
	    }
	    //(7)Drag and Drop 
	    //drag hotel to from and bus to To.
	    
	    public void DragAndDrop() {
		    // Initialize Actions class for performing drag-and-drop
		    Actions actions = new Actions(driver);

		    // Locate the "Hotel" link element using its XPath
		    WebElement hotelLink = driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[2]/a/p")); // Adjust XPath if necessary

		    // Locate the "Bus" link element using its XPath
		    WebElement busLink = driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[3]/a/span")); // Adjust XPath if necessary

		    // Locate the "From" input field using its XPath
		    WebElement fromField = driver.findElement(By.xpath("//*[@id=\"origin\"]"));

		    // Locate the "to" button using its XPath
		    WebElement toField = driver.findElement(By.xpath("//*[@id=\"destination\"]"));
		    
           // Perform drag-and-drop operation for "Hotel" -> "From"
		    actions.dragAndDrop(hotelLink, fromField).perform();

		    // Perform drag-and-drop operation for "Bus" -> "to"
		    actions.dragAndDrop(busLink, toField).perform();
		    // Optional: Add assertions to verify the success of the operation
		    System.out.println("Drag and drop operations completed succesfully.");
		}
	    
	    //(8) link count
	    public void linkCount()
		{
			List<WebElement> linkDetails=driver.findElements(By.tagName("a"));
			System.out.println("total no of links="+linkDetails.size());
			
		
		}
	  //(9)page source
	    public void PageSource()
		{
			String src = driver.getPageSource();
			if (src.contains("Enjoy exciting deals on flights, hotels, buses, car rental, tour packages and more")) 
			{
				System.out.println("Content Present");
			} 
			else
			{
				System.out.println("Content not present");
			}
		}
	    //(10)screenshot
	    public void ScreenShot () throws IOException
	    {
	    	File c=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    	FileHandler.copy(c, new File("C:\\Users\\kjh49\\OneDrive\\Desktop\\Screen\\screenshot3.png"));
	    	WebElement button=driver.findElement(By.xpath("//*[@id=\"search_btn_home\"]"));
	    	File buttonImage=button.getScreenshotAs(OutputType.FILE);
	    	FileHandler.copy(buttonImage,new File("C:\\Users\\kjh49\\OneDrive\\Desktop\\sastascreenshot\\buttonimage.png"));
	    }
	    //(11)link text
	    public void linktext() {
	    	List<WebElement> linkDetails=driver.findElements(By.tagName("a"));
			//System.out.println("total no of links="+linkDetails.size());
			for(WebElement element:linkDetails)
			{
				
				@SuppressWarnings("deprecation")
				String link=element.getAttribute("href");
				String linkText=element.getText();
				System.out.println("Link ="+link);
				System.out.println("Link Text="+linkText);
				
			}
			
		
		} 


	    }






	       
	    
	    


	    


	
	

