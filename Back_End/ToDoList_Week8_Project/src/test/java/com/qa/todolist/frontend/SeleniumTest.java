package com.qa.todolist.frontend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.qa.todolist.frontend.pom.SeleniumLinks;
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

	SeleniumLinks website = PageFactory.initElements(driver, SeleniumLinks.class);

	@BeforeAll
	public static void setUp() {

		report = new ExtentReports("./target/reports/extentreports/frontendReport.html", true);

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
		driver.manage().window().maximize();
	}

	@AfterEach
	public void after() {

		report.endTest(test);
		report.flush();
		report.close();

		System.out.println("\nTest has finished!");
	}

	@Test
	public void createTest() {

		test = report.startTest("Selenium Test Create Bag");

		test.log(LogStatus.INFO, "Given - I can access http://localhost:9090/index.html");
		driver.get("http://localhost:9090/index.html");

		test.log(LogStatus.INFO, "When - I navigate to the Bag Modification table");
		website.navigateBagModif();

		test.log(LogStatus.INFO, "When - I enter values for a bag and crate a bag");
		targ = driver.findElement(By.xpath("//*[@id=\"listName\"]"));
		targ.sendKeys("ShoppingList");
		targ = driver.findElement(By.xpath("//*[@id=\"addListBtn\"]"));
		targ.click();

		test.log(LogStatus.INFO, "Then - I should see Successfully Created a (createTest) List");
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("addListPara")));
		targ = driver.findElement(By.id("addListPara"));
		String stringResult = targ.getText();

		boolean result = stringResult.contains("Successfully Created a (ShoppingList) List");

		if (result) {
			test.log(LogStatus.PASS, "Successfully created a list!");
		} else {
			test.log(LogStatus.FAIL, "Failed to create" + stringResult);
		}

		assertEquals("Successfully Created a (ShoppingList) List", stringResult);

		report.endTest(test);
		report.flush();

		test = report.startTest("Selenium Test Create Item");

		test.log(LogStatus.INFO, "Given - I can access http://localhost:9090/index.html");

		website.navigateHome();

		test.log(LogStatus.INFO, "When - I navigate to the Item Modification table");
		website.navigateItemModif();

		test.log(LogStatus.INFO, "When - I enter values for an Item and crate an Item");
		targ = driver.findElement(By.xpath("//*[@id=\"itemName\"]"));
		targ.sendKeys("Cheese");
		targ = driver.findElement(By.xpath("//*[@id=\"itemPrice\"]"));
		targ.sendKeys("9.99");
		targ = driver.findElement(By.xpath("//*[@id=\"bagId\"]"));
		targ.sendKeys("1");

		targ = driver.findElement(By.xpath("//*[@id=\"addListBtn\"]"));
		targ.click();

		test.log(LogStatus.INFO, "Then - I should see Successfully Created a (Cheese) Item");
		new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(By.id("addListPara")));
		targ = driver.findElement(By.id("addListPara"));
		String stringResult2 = targ.getText();

		boolean result2 = stringResult2.contains("Successfully Created a (Cheese) Item");

		if (result2) {
			test.log(LogStatus.PASS, "Successfully created an item!");
		} else {
			test.log(LogStatus.FAIL, "Failed to create" + stringResult2);
		}

		assertEquals("Successfully Created a (Cheese) Item", stringResult2);
	}

}
