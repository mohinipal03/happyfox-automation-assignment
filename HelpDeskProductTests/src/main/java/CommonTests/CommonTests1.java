package CommonTests;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import Admin_Portal.AdminPortalTest1;
import Admin_Portal.AdminPortalTest1stPage;
import Admin_Portal.AdminPortalTest2ndPage;
import Admin_Portal.Login_Page;
import CustomerPortal.SupportPortalPage;
import webdriverbase.BaseTest;

public class CommonTests1 extends BaseTest{
	
// FIX NEEDED: Variable names should follow camelCase convention
public Login_Page hploginpage; // TODO: Rename to loginPage
public SupportPortalPage hpSupportPortalPage; // TODO: Rename to supportPortalPage
public AdminPortalTest1stPage hpAdminPortalTest1; // TODO: Rename to adminPortalPage1
public AdminPortalTest2ndPage hpAdminPortalTest2; // TODO: Rename to adminPortalPage2
	
// FIX NEEDED: All these values should be moved to configuration files (properties/JSON)
// CRITICAL SECURITY ISSUE: Credentials should not be hard-coded in source code
public String AdminPortalURL = "https://interview.supporthive.com/staff/login/"; // TODO: Move to config.properties
public static String SupportTicketURL = "https://interview.supporthive.com/new/"; // TODO: Move to config.properties
public static String browser = "Chrome"; // TODO: Move to config.properties
public static String username = "interview_agent"; // TODO: Move to secure config or environment variables
public static String password = "Interview@123"; // TODO: SECURITY RISK - Move to secure vault or env variables
public static String statusName = "Issue created"; // TODO: Move to test data files
public static String priorityName = "Assistance required"; // TODO: Move to test data files
public static String Subject = "Test Ticket raised by XYZ"; // TODO: Move to test data files
public static String Message = "Hi, i am having certain issues in the Happy fox portal. Can you please help me."; // TODO: Move to test data files
public static String FullName = "Reese Harrold"; // TODO: Move to test data files
public static String Email = "testno100@gmail.com"; // TODO: Move to test data files

public void loginasAdmin() {
    
    String Methodname = new Object(){}.getClass().getEnclosingMethod().getName(); 
	System.out.println("Start of LoginAsAdmin "+"MethodName : "+Methodname); 

	try
	{	
		hploginpage = new Login_Page(getDriver());
		hploginpage.navigateToHappyFoxHomePageURL(AdminPortalURL);
		hploginpage.enterUsername(username);
		System.out.println("username entered"); // TODO: Replace with logger.info()
		hploginpage.enterPassword(password);
		System.out.println("password entered"); // TODO: Replace with logger.info()
		hploginpage.clickLoginbutton();
		System.out.println("login clicked"); // TODO: Replace with logger.info()
		hpAdminPortalTest1=hploginpage.validatePendingTicketsTitle();
		
		
	}
 
	catch(Throwable t)
	{
		t.printStackTrace();
		Assert.fail("Error in " + Methodname + " : " + t.getMessage());
	}
	
	
}
  
  public void CreatingSupportTicket() {
	  
	  String Methodname = new Object(){}.getClass().getEnclosingMethod().getName(); 
	  System.out.println("Start of Client support ticket "+"MethodName : "+Methodname); 
	  
	  try 
	  {
		    
		  hpSupportPortalPage = new SupportPortalPage(getDriver());
		  hpSupportPortalPage.navigateToHappyFoxSupportPortalURL(SupportTicketURL);
		  hpSupportPortalPage.enterSubject(Subject);
		  hpSupportPortalPage.enterMessage(Message);
		  hpSupportPortalPage.clickAddCC();
		  hpSupportPortalPage.clickAddBCC(); 
		  hpSupportPortalPage.enterFullName(FullName);
		  hpSupportPortalPage.enterEmail(Email);
		  hpAdminPortalTest2=hpSupportPortalPage.clickCreateTicket();
		  System.out.println("Ticket created");
		  hpAdminPortalTest2.gotoAgentPortal();
	  
	  }
	  
	  catch (Throwable t) {
		  
		    t.printStackTrace();
			Assert.fail("Error in " + Methodname + " : " + t.getMessage());
	  }
  }
  
  // FIX NEEDED: Method names should follow camelCase convention
  // SHOULD BE: testCase1() instead of TestCase1()
  public void TestCase1() {
  
  try {
  	
  	String Classname = getClass().getSimpleName();
          System.out.println("start of method : " + Classname); // TODO: Replace with logger.info()
          hpAdminPortalTest1=hploginpage.validatePendingTicketsTitle();
          hpAdminPortalTest1.clickStatus();
          hpAdminPortalTest1.clickNewStatus();
          System.out.println("New Status process started");
          hpAdminPortalTest1.enterStatusName(statusName);
          System.out.println("status name entered");
          //hpAdminPortalTest1.enterStatusColour("#21d0d5"); //#21d0d5 skyblue, #21d567 green
          System.out.println("colour set");
          hpAdminPortalTest1.enterBehavior("Pending");
          hpAdminPortalTest1.enterStatusDescription("Status when a new issue ticket is created in HappyFox");
          System.out.println("description added");
          hpAdminPortalTest1.clickAddStatus();
          System.out.println("Status added");
          hpAdminPortalTest1.setDefaultStatus(statusName); 
          hpAdminPortalTest1.clickPrioritySection();
          hpAdminPortalTest1.clickNewPriority();
          System.out.println("New priority process started");
          hpAdminPortalTest1.enterPriorityName(priorityName);
          hpAdminPortalTest1.enterPriorityDescription("Priority of the newly created tickets");
          hpAdminPortalTest1.enterPriorityHelpText("priority helptext");
          hpAdminPortalTest1.clickAddPriority();
          hpAdminPortalTest1.setDefaultPriroity(priorityName);
          System.out.println("Priority added");    
				
		} 
		
		catch (Throwable e) {
			e.printStackTrace();
		}
		
	
} 
  
  public void TestCase2() {
		
		try {
			
			String Classname = getClass().getSimpleName();
          System.out.println("start of method : " + Classname);
          CreatingSupportTicket();
          hploginpage.validatePendingTicketsTitle1();
          hpAdminPortalTest2.clickPendingTickets();
          System.out.println("Pending tickets clicked");
          hpAdminPortalTest2.openCustomerTicket(Subject);
          
          //Assertion of the Priority and Status of the ticket created
          // FIX NEEDED: Hard-coded expected value "Aravind" should be from test data
          Assert.assertEquals(hpAdminPortalTest2.getContactName(),"Aravind"); // TODO: Move to test data
          Assert.assertEquals(hpAdminPortalTest2.getEmailtxt(),Email);
          
          hpAdminPortalTest2.clickReplyButton();
          Assert.assertEquals(hpAdminPortalTest2.getStatustxt(),statusName);
          Assert.assertEquals(hpAdminPortalTest2.getPrioritytxt(),priorityName.toUpperCase());
          
          hpAdminPortalTest2.clickCannedAction();
          hpAdminPortalTest2.clickSearchCannedAction("Reply to Customer Query");
          hpAdminPortalTest2.clickApplyCannedAction();
          
          //Assertion of the Priority and Status of the ticket after edition
          Assert.assertEquals(hpAdminPortalTest2.getStatustxt(),"Closed");
          Assert.assertEquals(hpAdminPortalTest2.getPrioritytxt(),"Medium");
          hpAdminPortalTest2.sendReply(); 
          hpAdminPortalTest1=hpAdminPortalTest2.closeTheTicket();
          hpAdminPortalTest1.clickPriorities();
          System.out.println("Test Case 2 over");
          
		}
		
		catch(Throwable t) {
			 t.printStackTrace();
		}
}
  
  public void TestCase3() {
		
		try {
			
			String Classname = getClass().getSimpleName();
			hpAdminPortalTest1.setDefaultPriroity("Low");
			System.out.println("Default priority set as Low");
			hpAdminPortalTest1.ClickAddedPriority(priorityName);
			System.out.println("Priority clicked");
			hpAdminPortalTest1.clickPriorityDeleteLink();
			hpAdminPortalTest1.clickDeleteConfirm();
			System.out.println("Priority deleted");  
			hpAdminPortalTest1.clickStatusesSection();
			hpAdminPortalTest1.setDefaultStatus("New");
			System.out.println("Default status set as New");
			hpAdminPortalTest1.ClickAddedStatus(statusName);
			System.out.println("Statuses clicked");
			hpAdminPortalTest1.clickStatusDeleteLink();
			//hpAdminPortalTest1.setNewDefaultStatus();
			hpAdminPortalTest1.clickDeleteConfirm();
			System.out.println("Statuses deleted");  
			hpAdminPortalTest1.clickProfile();
            hpAdminPortalTest1.clickLogout();
            System.out.println("Logged out successfully"); 
			
				
		} 
		
		catch (Throwable e) {
			e.printStackTrace();
		}
		
	
} 
}
