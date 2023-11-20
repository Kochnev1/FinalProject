package ru.netology.pageObject;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CreditPage {
    private SelenideElement creditPay = $(byText("Кредит по данным карты"));

    public FormCardPage formCard() {
        creditPay.should(visible);
        return new FormCardPage();
    }
}