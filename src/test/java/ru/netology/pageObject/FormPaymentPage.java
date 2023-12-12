package ru.netology.pageObject;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FormPaymentPage {
    private SelenideElement numberCardField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement ownerField = $$("[class='input__control']").get(3);
    private SelenideElement cvcField = $("[placeholder='999']");
    private SelenideElement execButton = $$("button").find(exactText("Продолжить"));

    private SelenideElement failedNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement successedNotification = $(byText("Операция одобрена Банком."));
    private SelenideElement mandatoryFieldMessage = $(byText("Поле обязательно для заполнения"));
    private SelenideElement wrongFormatMessage = $(byText("Неверный формат"));


    public void fillForm(String cardNumber, String month, String year, String cardOwner, String code) {
        numberCardField.sendKeys(cardNumber);
        monthField.sendKeys(month);
        yearField.sendKeys(year);
        ownerField.sendKeys(cardOwner);
        cvcField.sendKeys(code);
        execButton.click();
    }

    public FormPaymentPage clear() {
        clearFields();
        return new FormPaymentPage();
    }

    public void clearFields() {
        numberCardField.doubleClick().sendKeys(Keys.BACK_SPACE);
        monthField.doubleClick().sendKeys(Keys.BACK_SPACE);
        yearField.doubleClick().sendKeys(Keys.BACK_SPACE);
        ownerField.doubleClick().sendKeys(Keys.BACK_SPACE);
        cvcField.doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    public void waitForFailedNotification() {
        failedNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void waitForSuccessNotification() {
        successedNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void waitForMandatoryFieldMessage() {
        mandatoryFieldMessage.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void waitForWrongFormatMessage() {
        wrongFormatMessage.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

}


