package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.pageObject.HomePage;
import ru.netology.pageObject.FormCreditPage;
import ru.netology.data.SqlHelper;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyCreditTest {
    private HomePage homePage;
    private FormCreditPage paymentFormPageCredit;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        homePage = open("http://localhost:8080/", HomePage.class);
    }

    @AfterEach
    void cleanDB() {
        SqlHelper.clearDB();
    }


    @Test
        //Покупка с использованием валидных данных и подтвержденной карты
    void shouldPurchaseUsingValidData() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForSuccessNotification();
        val expected = DataHelper.getFirstCardStatus();
        val actual = SqlHelper.getCreditPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
        // Попытка покупки с незаполненными полями
    void shouldPurchaseWithBlankFields() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getEmptyCardNumber();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getEmptyYear();
        val cardOwner = DataHelper.getEmptyOwner();
        val code = DataHelper.getEmptyCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForMandatoryFieldMessage();
    }

    @Test
        //Покупка с незаполненным полем "Номер карты"
    void shouldPurchaseWithaBlankCardNumber() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getEmptyCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForMandatoryFieldMessage();
    }

    @Test
        //Покупка с использованием отклоненной карты
    void shouldPurchaseFromaDeclinedCard() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getSecondCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForFailedNotification();
        val expected = DataHelper.getSecondCardStatus();
        val actual = SqlHelper.getCreditPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
        //Покупка с использованием неизвестной карты
    void shouldPurchaseWithAnUnknownCard() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getRandomCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForFailedNotification();
    }

    @Test
        //Поле "Номер карты" заполенно 15 цифрами
    void shouldPurchaseWithaShortCardNumber() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getCardNumberWith15Digits();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test
        //Поле "Номер карты" заполнено одной цифрой
    void shouldPurchaseWithOneDigitsCardNumber() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getCardNumberWith1Digit();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test
        //поле "Номер карты" заполнено словами
    void shouldPurchaseUsingTheWordsInTheCardNumber() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getCardNumberWithTextAndChars();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }

    @Test
        //Поле "Месяц" оставлено пустым
    void ShouldTheMonthFieldIsLeftBlank() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForMandatoryFieldMessage();
    }

    @Test
        //Поле "Месяц" зжаполнено значением свыше 12
    void shouldPurchaseWithMonthAbove12() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getMonthOver12();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongCardExpirationMessage();
    }

    @Test
        //Поле "Месяц" заполено значением 0
    void shouldTheMonthFieldIsFilledWithZero() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getZeroMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongCardExpirationMessage();
    }

    @Test
        //Поле "Месяц" заполнено невалидными данными
    void ShouldTheMonthFieldIsFilledWithInvalidData() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getInvalidFormatMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test
        //Поле "Месяц" заполнено буквами
    void shouldTheMonthFieldIsFilledWithLetters() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getMonthWithText();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }

    @Test
        //поле "Год" оставлено пустым
    void shouldFieldYearLeftEmpty() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getEmptyYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForMandatoryFieldMessage();
    }

    @Test
        //Поле "Год" указан прошедший год
    void shouldFieldYearLeftPastYear() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getPastYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForCardExpiredMessage();
    }

    @Test
        //Поле "Год" заполнено невалидными данными
    void shouldFieldYearFilledWithInvalidData() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidFormatYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test
        //Поле "Год" заполнено датой будующего года
    void shouldFieldYearFilledWithNextYearDate() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getFutureYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongCardExpirationMessage();
    }

    @Test
        //Поле "Год" заполнено текстом
    void shouldFieldYearFilledWithText() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getYearWithText();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }

    @Test
        //Поле "Владелец" оставлено пустым
    void shouldFieldOwnerLeftEmpty() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getEmptyOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForMandatoryFieldMessage();
    }


    @Test
        //Поле "Владелец" заполнени маленькими буквами
    void shouldFieldOwnerFilledWithSmallLowerLetters() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getLowercaseLettersOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }


    @Test   //Поле "Владелец" заполнено кириллицей
    void shouldFieldOwnerFilledWithCyrillic () {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getCyrillicDataOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }


    @Test   //Поле "Владелец" заполнено цифрами
    void shouldFieldOwnerFilledWithNumbers() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getOwnerWithDigits();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }

    @Test   //Поле "Владелец" заполнено спец.символами
    void shouldFieldOwnerFilledSymbol() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getOwnerWithSpecialChars();
        val code = DataHelper.getValidCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }

    @Test   //Поле "CVC/CVV" оставлено пустым
    void shouldFieldCVCLeftEmpty() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getEmptyCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForMandatoryFieldMessage();
    }

    @Test   //Поле "CVC/CVV" заполнено невалидными данными
    void shouldFieldCVCFilledWithInvalidData() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getInvalidFormatCode();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForWrongFormatMessage();
    }

    @Test   //Поле "CVC/CVV" заполнено буквами
    void shouldFieldCVCFilledWithLetters() {
        paymentFormPageCredit = homePage.payWithCreditCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getCodeWithText();
        paymentFormPageCredit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageCredit.waitForInvalidCharactersMessage();
    }
}
