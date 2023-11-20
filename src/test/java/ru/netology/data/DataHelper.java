package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.Year;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    public static Faker faker = new Faker(new Locale("en"));

    @Value
    @RequiredArgsConstructor
    public static class CardInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String owner;
        private String cardCVC;


        //    Заполнение поля Номер карты
        public static String getApprovedCardNumber() {
            return ("4444 4444 4444 4441");
        }

        public static String getDeclinedCardNumber() {
            return ("4444 4444 4444 4442");
        }

        public static String getUnknownCardNumber() {
            return ("7821 9314 9823 2311");
        }

        public static String getShortCardNumber() {
            return ("4444 4444 4444");
        }

        public static String getCardNumberIsSymbols() {
            return ("@!!& !?!? **** )(");
        }

        public static String getCardNumberIsLetters() {
            return ("qazw sxed rfvt gbyh");
        }

        //    Заполнение поля Месяц
        public static String getValidMonth() {
            LocalDate localDate = LocalDate.now();
            return String.format("%02d", localDate.getMonthValue());
        }

        public static String getMonthAbove12() {
            return ("13");
        }

        public static String getMonthIsLetters() {
            return ("HAfwf");
        }

        public static String getMonthIsSymbols() {
            return ("%^");
        }

        public static String getMonthWithZero() {
            return ("00");
        }

        //    Заполнение поля Год
        public static String getValidYear() {
            return String.format("%ty", Year.now());
        }

        public static String getPastYear() {
            LocalDate localDate = LocalDate.now();
            return String.format("20");
        }

        public static String getMoreThan5Years() {
            LocalDate localDate = LocalDate.now();
            return String.format("29");
        }

        public static String getYearIsLetters() {
            return ("af");
        }

        public static String getYearIsSymbols() {
            return ("!&");
        }

        public static String getYearIsOneValue() {
            return ("9");
        }

        //    Заполнение поля Владелец
        public static String getOwnerName() {
            return faker.name().username();
        }
//
//        public static String getOwnerFirstName() {
//            return faker.name().firstName();
//        }


        public static String getOwnerNameInRussia() {
            Faker faker = new Faker(new Locale("ru"));
            return faker.name().username();
        }

        public static String getOwnerNameIsValue() {
            return "434567";
        }

        public static String getOwnerNameIsSymbols() {
            return "*****";
        }


        //    Заполнение поля CVC
        public static String getCVC() {
            return "655";
        }

        public static String getCVCIsLetters() {
            return "zle";
        }

        public static String getCVCIsSymbols() {
            return "***";
        }

        public static String getCVCShort() {
            return "55";
        }
    }
}
