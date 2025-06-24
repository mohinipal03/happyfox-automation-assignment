package Admin_Portal;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CommonTests.CommonTests1;

 // FIX NEEDED: Class name should follow PascalCase convention consistently
 // Test class structure needs improvement
 public class AdminPortalTest1 extends CommonTests1  {

  // FIX NEEDED: Test method names should be descriptive and follow camelCase
  // SHOULD BE: testCreateStatusAndPriorityScenario() instead of Scenario1()
  @Test
  public void Scenario1() {
   
    String Methodname = new Object(){}.getClass().getEnclosingMethod().getName();
 
 	try {
 		
 		String Classname = getClass().getSimpleName();
            System.out.println("start of method : " + Classname); // TODO: Replace with logger.info()
            loginasAdmin();
            TestCase1();
            TestCase3();
				
		} 
		
		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Error in "+ Methodname +" : "+ e.getMessage());
		}
		
	
  } 
  
 }
 