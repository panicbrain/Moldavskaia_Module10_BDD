package Stepdefs;

import BusinessObjects.Users;
import DriverManager.ChromeWebDriverSingleton;
import PageObjects.*;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

import java.util.Map;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class MailStepsDefs {
    public WebDriver driver;
    UUID SUBJECT_TO_MAIL;
   public static String MAIL_SUBJECT;

    @Given("^I opened Mail.ru site$")
    public void iOpenedMailRuSite() {
        driver = ChromeWebDriverSingleton.getWebDriverInstance();
        new HomePage(driver).open();
    }

    @And("^I create the subject of the letter$")
    public String iCreateTheSubjectOfTheLetter(){
        SUBJECT_TO_MAIL = UUID.randomUUID();
        MAIL_SUBJECT = SUBJECT_TO_MAIL.toString();
        return MAIL_SUBJECT;
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

    @And("^I open the last saved letter$")
    public void iOpenTheLastSavedLetter(){
        NewLetterPage newLetterPage = new DraftMailsPage(driver).openLastSavedDraft();
    }

    @Then("^all fields contain same parameters$")
    public void checkDraftFieldsContent(){
        assertEquals(new NewLetterPage(driver).getMailAddress(), "ekaterinamoldavskaia18@gmail.com");
        assertEquals(new NewLetterPage(driver).getMailSubject(), MAIL_SUBJECT);
        assertTrue(new NewLetterPage(driver).getBodyText().contains("Test"), "Mail text is absent");
    }

    @And("^I send the letter$")
    public void iSendTheLetter(){
        new NewLetterPage(driver).sendMail();
    }

    @Then("^the letter should not be in Draft folder$")
    public void checkThatLetterIsNotInDraftFolder() throws InterruptedException {
        DraftMailsPage draftMailsPage = new NewLetterPage(driver).openDraftFolder();
        assertFalse(draftMailsPage.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The draft stays in the folder");
    }

    @And("^the letter should be in Sent folder$")
    public void checkThatLetterIsInSentFolder() throws InterruptedException {
        SentMailsPage sentMailsPage = new DraftMailsPage(driver).openSentFolder();
        assertTrue(sentMailsPage.getSubjectTextsOfMails().contains(MAIL_SUBJECT), "The sent email is absent in the folder");
    }

    @And("^I log out$")
    public void iLogOut(){
        HomePage homepage = new SentMailsPage(driver).logOff();
    }

    @Then("^the main page should be opened$")
    public void checkThatMainPageIsOpenAfterLogOut(){
        assertEquals(new HomePage(driver).driver.getTitle(), "Mail.Ru: почта, поиск в интернете, новости, игры");
    }
}
