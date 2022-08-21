package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TestRunner {
	private static String projectPath = System.getProperty("user.dir");
	WebDriver driver;
	String loginPageUrl, userIdInfor, passwordInfor, email;

	public static void main(String[] args) {
		TestRunner test = new TestRunner();

		test.beforeClass();
		test.TC_01_RegisterToSystem();
		test.TC_02_LoginToSystem();
		test.afterClass();
	}

	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://demo.guru99.com/v4/");
		System.out.println("----------- Open browser success ------------");

		email = "selenium09" + randomNumber() + "@gmail.com";
	}

	public void TC_01_RegisterToSystem() {
		System.out.println("Step 01 - Check Login Page displayed");
		Assert.assertTrue(driver.findElement(By.xpath("//form[@name='frmLogin']")).isDisplayed());

		System.out.println("Step 02 - Click to 'here' link -> Open Register Page");
		loginPageUrl = driver.getCurrentUrl();
		System.out.println("Login Page Url: " + loginPageUrl);
		driver.findElement(By.xpath("//a[text()='here']")).click();

		System.out.println("Step 03 - Check Register Page displayed");
		Assert.assertTrue(driver.findElement(By.xpath("//input[@name='emailid']")).isDisplayed());

		System.out.println("Step 04 - Input random email to Email textbox");
		driver.findElement(By.xpath("//input[@name='emailid']")).sendKeys(email);

		System.out.println("Step 05 - Click to Submit button");
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();

		System.out.println("Step 06 - Get User/Password information");
		userIdInfor = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
		passwordInfor = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();
		System.out.println("User: " + userIdInfor + " - Password: " + passwordInfor);
	}

	public void TC_02_LoginToSystem() {
		System.out.println("Step 01 - Open Login Page");
		driver.get(loginPageUrl);
		Assert.assertTrue(driver.findElement(By.xpath("//form[@name='frmLogin']")).isDisplayed());

		System.out.println("Step 02 - Input valid data to User and Password textbox");
		driver.findElement(By.xpath("//input[@name='uid']")).sendKeys(userIdInfor);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(passwordInfor);

		System.out.println("Step 03 - Click to Login button");
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();

		System.out.println("Step 04 - Check Home Page displayed");
		Assert.assertTrue(driver.findElement(By.xpath("//marquee[text()=\"Welcome To Manager's Page of Guru99 Bank\"]")).isDisplayed());

		System.out.println("Step 05 - Check UserID infor displayed in Home Page");
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='Manger Id : " + userIdInfor + "']")).isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
		System.out.println("----------- Quit browser success ------------");
	}

	public int randomNumber() {
		Random random = new Random();
		return random.nextInt(999999);
	}

}
