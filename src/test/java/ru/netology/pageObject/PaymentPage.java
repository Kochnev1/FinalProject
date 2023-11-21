package ru.netology.pageObject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

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