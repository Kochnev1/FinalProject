package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.pageObject.CreditPage;
import ru.netology.pageObject.HomePage;

import static com.codeborne.selenide.Selenide.open;

public class HappyPathTest {
    HomePage homePage;

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
        var HomePage = new HomePage();
    }

    @Test


}
