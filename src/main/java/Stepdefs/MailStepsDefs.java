package Stepdefs;

import BusinessObjects.Users;
import DriverManager.ChromeWebDriverSingleton;
import PageObjects.DraftMailsPage;
import PageObjects.HomePage;
import PageObjects.IncomingMailsPage;
import PageObjects.NewLetterPage;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

import java.util.Map;

import static PageObjects.BaseAreasPage.MAIL_SUBJECT;
import static org.testng.Assert.assertTrue;

public class MailStepsDefs {
    public WebDriver driver;

    @Given("^I opened Mail.ru site$")
    public void iOpenedMailRuSite() {
        driver = ChromeWebDriverSingleton.getWebDriverInstance();
        new HomePage(driver).open();
    }

    @When("^I log in$")
    public void iLogIn() {
        new HomePage(driver).logIn(new Users());
    }

    @And("^I create new letter with parameters:$")
    public void iCreateNewLetter(DataTable table) {
        for (Map<String, String> map : table.asMaps(String.class, String.class)) {
            String email = map.get("email");
            String mailBodyText = map.get("mailBodyText");
            NewLetterPage newLetterPage = new IncomingMailsPage(driver).createNewLetter();
            newLetterPage.fillAllFieldsOfNewLetter(email, MAIL_SUBJECT, mailBodyText);
        }
    }

    @And("^I save the letter as draft$")
    public void iSaveTheLetterAsDraft() {
        new NewLetterPage(driver).saveAsDraft();
    }

    @And("^I open Draft folder$")
    public void iOpenDraftFolder() {
        new NewLetterPage(driver).openDraftFolder();
    }

    @Then("^the letter is in Draft folder$")
    public void theLetterIsInDraftFolder() throws InterruptedException {
        DraftMailsPage draftMailsPage = new NewLetterPage(driver).openDraftFolder();
        assertTrue(draftMailsPage.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The draft of test email is absent in the folder");
    }
}
