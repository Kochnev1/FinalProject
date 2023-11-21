package ru.netology.pageObject;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;

public class FormCardPage {
    private SelenideElement cardNumber = $$("[class='form-field form-field_size_m form-field_theme_alfa-on-white']").find(exactText("Номер карты"));
    private SelenideElement month = $$("[class='input-group__input-case']").find(exactText("Месяц"));
    private SelenideElement year = $$("[class='input-group__input-case']").find(exactText("Год"));
    private SelenideElement owner = $$("[class='input-group__input-case']").find(exactText("Владелец"));
    private SelenideElement cvc = $$("[class='input-group__input-case']").find(exactText("CVC/CVV"));
    private SelenideElement execButton = $$("button").find(exactText("Продолжить"));
}

