package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.pageObject.HomePage;
import ru.netology.pageObject.FormPaymentPage;
import ru.netology.data.SqlHelper;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyDebitTest {
    private HomePage homePage;
    private FormPaymentPage paymentFormPageDebit;

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
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var cardOwner = DataHelper.getValidOwner();
        var code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForSuccessNotification();
        var expected = DataHelper.getFirstCardStatus();
        var actual = SqlHelper.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }
    @Test
        // Попытка покупки с незаполненными полями
    void shouldPurchaseWithBlankFields() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getEmptyCardNumber();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getEmptyYear();
        val cardOwner = DataHelper.getEmptyOwner();
        val code = DataHelper.getEmptyCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test
        //Покупка с незаполненным полем "Номер карты"
    void shouldPurchaseWithaBlankCardNumber() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getEmptyCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test
        //Покупка с использованием отклоненной карты
    void shouldPurchaseFromaDeclinedCard() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getSecondCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForFailedNotification();
        val expected = DataHelper.getSecondCardStatus();
        val actual = SqlHelper.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
        //Покупка с использованием неизвестной карты
    void shouldPurchaseWithAnUnknownCard() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getRandomCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForFailedNotification();
    }

    @Test
        //Поле "Номер карты" заполенно 15 цифрами
    void shouldPurchaseWithaShortCardNumber() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getCardNumberWith15Digits();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
        //Поле "Номер карты" заполнено одной цифрой
    void shouldPurchaseWithOneDigitsCardNumber() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getCardNumberWith1Digit();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
        //поле "Номер карты" заполнено словами
    void shouldPurchaseUsingTheWordsInTheCardNumber() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getCardNumberWithTextAndChars();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
        //Поле "Месяц" оставлено пустым
    void ShouldTheMonthFieldIsLeftBlank() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test
        //Поле "Месяц" зжаполнено значением свыше 12
    void shouldPurchaseWithMonthAbove12() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getMonthOver12();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
        //Поле "Месяц" заполено значением 0
    void shouldTheMonthFieldIsFilledWithZero() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getZeroMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
        //Поле "Месяц" заполнено невалидными данными
    void ShouldTheMonthFieldIsFilledWithInvalidData() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getInvalidFormatMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
        //Поле "Месяц" заполнено буквами
    void shouldTheMonthFieldIsFilledWithLetters() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getMonthWithText();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
        //поле "Год" оставлено пустым
    void shouldFieldYearLeftEmpty() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getEmptyYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test
        //Поле "Год" указан прошедший год
    void shouldFieldYearLeftPastYear() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getPastYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
        //Поле "Год" заполнено невалидными данными
    void shouldFieldYearFilledWithInvalidData() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidFormatYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
        //Поле "Год" заполнено датой будующего года
    void shouldFieldYearFilledWithNextYearDate() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getFutureYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
        //Поле "Год" заполнено текстом
    void shouldFieldYearFilledWithText() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getYearWithText();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test
        //Поле "Владелец" оставлено пустым
    void shouldFieldOwnerLeftEmpty() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getEmptyOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }


    @Test
        //Поле "Владелец" заполнени маленькими буквами
    void shouldFieldOwnerFilledWithSmallLowerLetters() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getLowercaseLettersOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }


    @Test   //Поле "Владелец" заполнено кириллицей
    void shouldFieldOwnerFilledWithCyrillic () {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getCyrillicDataOwner();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }


    @Test   //Поле "Владелец" заполнено цифрами
    void shouldFieldOwnerFilledWithNumbers() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getOwnerWithDigits();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test   //Поле "Владелец" заполнено спец.символами
    void shouldFieldOwnerFilledSymbol() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getOwnerWithSpecialChars();
        val code = DataHelper.getValidCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test   //Поле "CVC/CVV" оставлено пустым
    void shouldFieldCVCLeftEmpty() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getEmptyCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForMandatoryFieldMessage();
    }

    @Test   //Поле "CVC/CVV" заполнено невалидными данными
    void shouldFieldCVCFilledWithInvalidData() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getInvalidFormatCode();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }

    @Test   //Поле "CVC/CVV" заполнено буквами
    void shouldFieldCVCFilledWithLetters() {
        paymentFormPageDebit = homePage.payWithDebitCard()
                .clear();
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val cardOwner = DataHelper.getValidOwner();
        val code = DataHelper.getCodeWithText();
        paymentFormPageDebit.fillForm(cardNumber, month, year, cardOwner, code);
        paymentFormPageDebit.waitForWrongFormatMessage();
    }
}
