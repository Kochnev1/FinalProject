package ru.netology.pageObject;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {

    private SelenideElement paymentPay = $(byText("Оплата по карте"));

    public FormCardPage formCard() {
        paymentPay.should(visible);
        return new FormCardPage();
    }
}