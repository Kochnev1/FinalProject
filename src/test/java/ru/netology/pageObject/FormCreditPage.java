package ru.netology.pageObject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FormCreditPage {
    private SelenideElement numberCardField = $$("[class='form-field form-field_size_m form-field_theme_alfa-on-white']").find(exactText("Номер карты"));
    private SelenideElement monthField = $$("[class='input input_type_text input_view_default input_size_m input_width_available input_has-label input_theme_alfa-on-white]").find(exactText("Месяц"));
    private SelenideElement yearField = $$("[class='input-group__input-case']").find(exactText("Год"));
    private SelenideElement ownerField = $$("[class='input-group__input-case']").find(exactText("Владелец"));
    private SelenideElement cvcField = $$("[class='input-group__input-case']").find(exactText("CVC/CVV"));
    private SelenideElement execButton = $$("button").find(exactText("Продолжить"));

    private SelenideElement failedNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement successNotification = $(byText("Операция одобрена Банком."));
    private SelenideElement mandatoryFieldMessage = $(byText("Поле обязательно для заполнения"));
    private SelenideElement wrongFormatMessage = $(byText("Неверный формат"));
    private SelenideElement invalidCharactersMessage = $(byText("Поле содержит недопустимые символы"));
    private SelenideElement wrongCardExpirationMessage = $(byText("Неверно указан срок действия карты"));
    private SelenideElement cardExpiredMessage = $(byText("Истёк срок действия карты"));

    public void fillForm(String cardNumber, String month, String year, String cardOwner, String code) {
        numberCardField.sendKeys(cardNumber);
        monthField.sendKeys(month);
        yearField.sendKeys(year);
        ownerField.sendKeys(cardOwner);
        cvcField.sendKeys(code);
        execButton.click();
    }

    public FormCreditPage clear() {
        clearFields();
        return new FormCreditPage();
    }

    public void clearFields() {
        numberCardField.doubleClick().sendKeys(Keys.BACK_SPACE);
        monthField.doubleClick().sendKeys(Keys.BACK_SPACE);
        yearField.doubleClick().sendKeys(Keys.BACK_SPACE);
        ownerField.doubleClick().sendKeys(Keys.BACK_SPACE);
        cvcField.doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    public void waitForFailedNotification() {
        failedNotification.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }

    public void waitForSuccessNotification() {
        successNotification.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }

    public void waitForMandatoryFieldMessage() {
        mandatoryFieldMessage.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }

    public void waitForWrongFormatMessage() {
        wrongFormatMessage.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }

    public void waitForInvalidCharactersMessage() {
        invalidCharactersMessage.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }

    public void waitForWrongCardExpirationMessage() {
        wrongCardExpirationMessage.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }

    public void waitForCardExpiredMessage() {
        cardExpiredMessage.shouldBe(Condition.visible, Duration.ofSeconds(11));
    }
}

