package com.qa.todolist.frontend;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@SpringBootTest
@ActiveProfiles("dev")
public class SeleniumTest {
	private static RemoteWebDriver driver;
	private static WebElement targ;
	private static ExtentReports report;
	private static ExtentTest test;

	@BeforeAll
	public static void setUp() {

		report = new ExtentReports("./target/reports/extentreports/waitReport.html", true);

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

		driver = new ChromeDriver();
		System.out.println("Tests have Started");

	}

	@AfterAll
	public static void cleanUp() {
		driver.quit();
		System.out.println("The driver has been closed!");
	}

	@BeforeEach
	public void before() {
		System.out.println("\nTest has started!");
	}

	@AfterEach
	public void after() {

		report.endTest(test);
		report.flush();
		report.close();

		System.out.println("\nTest has finished!");
	}

	@Test
	public void createBagTest() {

		//
		test.log(LogStatus.INFO, "Given - I can access http://localhost:9090/index.html");
		driver.get("http://localhost:9090/index.html");

		test.log(LogStatus.INFO, "When - I navigate to the Bag Modification table");
		targ = driver.findElement(By.xpath("/html/body/div/div[1]/div/div/div[1]/a/button"));
		targ.click();

		test.log(LogStatus.INFO, "When - I enter values for a bag and crate a bag");
		targ = driver.findElement(By.xpath("//*[@id=\"search_query_top\"]"));
		targ.sendKeys("Dress");

		test.log(LogStatus.INFO, "Then - I should see Successfully Created a (createTest) List");

	}

}
