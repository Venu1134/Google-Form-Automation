package demo;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestCases {
    WebDriver driver;
    WebDriverWait wait;

    public TestCases()
    {
        System.out.println("Constructor: TestCases");
        //WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    
    public  void testCase01() throws InterruptedException{
        System.out.println("Start Test case: testCase01");
        //Step 1 :- Navigate to google form.
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform");

        //Step 2 :- Fill Name field.
        WebElement NameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='i1']")));
        NameField.sendKeys("Venu Gopal A");

        //Step 3 :- Fill Practing automation field.
        long epochTime = Instant.now().getEpochSecond();
        WebElement PractingAutomationField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@aria-labelledby='i5']")));
        PractingAutomationField.sendKeys("I want to be the best QA Engineer! "+epochTime);

        //Step 4 :- Enter your Automation Testing experience in the next radio button
        List<WebElement> radioButtons = driver.findElements(By.xpath("//div[@role='radiogroup']//label"));
        String optionToSelect = getOptionBasedOnExperience(3);
        for (WebElement radioButton : radioButtons) {
            if(radioButton.getText().contains(optionToSelect)){
                radioButton.click();
                break;
            }
        }

        //Step 5 :- Select Java, Selenium and TestNG from the next check-box
        Set<String> optionsToSelect = new HashSet<>(Arrays.asList("Java", "Selenium", "TestNG"));
        List<WebElement> CheckBoxes = driver.findElements(By.xpath("//div[@aria-labelledby='i25']//label"));
        for (WebElement CheckBox : CheckBoxes) {
            if(optionsToSelect.contains(CheckBox.getText())){
                CheckBox.click();
            }
        }

        //Step 6 :- Provide how you would like to be addressed in the next dropdown
        driver.findElement(By.xpath("(//div[@aria-labelledby='i42']//span)[1]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='mG61Hd']/div[2]/div/div[2]/div[5]/div/div/div[2]/div/div[2]")));
        List<WebElement> SalutationDropdown = driver.findElements(By.xpath("//div[@role='option']"));
        for (WebElement option : SalutationDropdown) {
            if (option.getText().equalsIgnoreCase("Mr")) {
                option.click();
                Thread.sleep(1000);
                break;
            }
        }

        //Step 7 :- Provided the current date minus 7 days in the next date field, it should be dynamically calculated and not hardcoded.
        LocalDate dateMinus7Days = LocalDate.now().minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = dateMinus7Days.format(formatter);
        WebElement DateField = driver.findElement(By.xpath("//input[@type='date']"));
        DateField.sendKeys(formattedDate);

        //Step 8 :- Provide the current time (Keeping in mind AM/PM) in the next field.
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("hh");
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");
        DateTimeFormatter amPmFormatter = DateTimeFormatter.ofPattern("a");

        String hour = currentTime.format(hourFormatter);
        String minute = currentTime.format(minuteFormatter);
        String amPm = currentTime.format(amPmFormatter);
        System.out.println("Current time : "+hour + minute + amPm);

        driver.findElement(By.xpath("//input[@aria-label='Hour']")).sendKeys(hour);
        driver.findElement(By.xpath("//input[@aria-label='Minute']")).sendKeys(minute);
        List<WebElement> AmPmdropDown = driver.findElements(By.xpath("//input[@aria-label='AM or PM']"));
        for (WebElement webElement : AmPmdropDown) {
            if(webElement.getText().equals(amPm)){
                webElement.click();
                break;
            }
        }

        //Step 9 :- Change the URL of the tab (amazon.in) and you will find the pop up as follows. Click on cancel.
        driver.navigate().to("https://www.amazon.in");
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();

        //Step 10 :- Submit the form
        driver.findElement(By.xpath("//span[text()='Submit']")).click();
        
        //Step 11 :- You will see a success message on the website. Print the same message on the console upon successful completion
        String ConfirmationMessage = driver.findElement(By.xpath("//div[contains(text(),'Thanks for your response')]")).getText();
        System.out.println(ConfirmationMessage);
        
        System.out.println("end Test case: testCase01");
    }

    private static String getOptionBasedOnExperience(int experience) {
        if (experience >= 0 && experience <= 2) {
            return "0 - 2";
        } else if (experience >= 3 && experience <= 5) {
            return "3 - 5";
        } else if (experience >= 6 && experience <= 10) {
            return "6 - 10";
        } else {
            return "> 10";
        }
    }
}
