package ru.netology.pageObject;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class HomePage {

    private SelenideElement heading = $("[class='heading heading_size_l heading_theme_alfa-on-white']");
    private SelenideElement headingPaymentForm = $("[class='heading heading_size_m heading_theme_alfa-on-white']");
    private SelenideElement buyByDebitCardButton = $(byText("Купить"));
    private SelenideElement buyByCreditCardButton = $(byText("Купить в кредит"));

    public HomePage() {
        heading.shouldBe(visible);
    }

    public FormPaymentPage payWithDebitCard() {
        buyByDebitCardButton.click();
        headingPaymentForm.shouldHave(exactText("Оплата по карте"));
        return new FormPaymentPage();
    }

    public FormCreditPage payWithCreditCard() {
        buyByCreditCardButton.click();
        headingPaymentForm.shouldHave(exactText("Кредит по данным карты"));
        return new FormCreditPage();
    }
}

