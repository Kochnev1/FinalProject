package ru.netology.pageObject;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;


public class HomePage {
    private SelenideElement heading = $$("[class='heading heading_size_l heading_theme_alfa-on-white']").find(exactText("Путешествие дня"));

    public HomePage(){
        heading.shouldBe(visible);
    }

    public CreditPage credit() {
        $$("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").find(exactText("Купить в кредит")).click();
        return new CreditPage();
    }

    public PaymentPage payment() {
        $$("[class='button button_size_m button_theme_alfa-on-white']").find(exactText("Купить")).click();
        return new PaymentPage();
    }
}
