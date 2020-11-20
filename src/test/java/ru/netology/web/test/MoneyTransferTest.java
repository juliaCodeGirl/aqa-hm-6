package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    private String amount = "2000";
    private String amountZero = "0";
    private String amountOne = "1";
    private String amountOverLimit = "35000";


    @Nested
    public class ValidLogin {
        @BeforeEach
        public void login() {
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val codeVerify = DataHelper.getVerificationCode();
            verificationPage.validVerify(codeVerify);
        }

        @Nested
        public class PositiveScenario {
            @Test
            void shouldTransferAmountFromCard1ToCard2() {
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.isDashboardPage();
                int expected1 = dashboardPage.getBalanceCard1() + Integer.parseInt(amount);
                int expected2 = dashboardPage.getBalanceCard2() - Integer.parseInt(amount);
                dashboardPage.refillCard1();
                TransferPage transferPage = new TransferPage();
                transferPage.paymentVisible();
                transferPage.setAmount(amount);
                transferPage.setFromCardField(DataHelper.getCard2());
                transferPage.getTransfer();
                assertEquals(expected1, dashboardPage.getBalanceCard1());
                assertEquals(expected2, dashboardPage.getBalanceCard2());
            }

            @Test
            void shouldTransferWhenAmountOne() {
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.isDashboardPage();
                int expected1 = dashboardPage.getBalanceCard1() + Integer.parseInt(amountOne);
                int expected2 = dashboardPage.getBalanceCard2() - Integer.parseInt(amountOne);
                dashboardPage.refillCard1();
                TransferPage transferPage = new TransferPage();
                transferPage.paymentVisible();
                transferPage.setAmount(amountOne);
                transferPage.setFromCardField(DataHelper.getCard2());
                transferPage.getTransfer();
                assertEquals(expected1, dashboardPage.getBalanceCard1());
                assertEquals(expected2, dashboardPage.getBalanceCard2());
            }

            @Test
            void shouldTransferWhenCancel() {
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.isDashboardPage();
                int expected1 = dashboardPage.getBalanceCard1();
                int expected2 = dashboardPage.getBalanceCard2();
                dashboardPage.refillCard1();
                TransferPage transferPage = new TransferPage();
                transferPage.paymentVisible();
                transferPage.setAmount(amount);
                transferPage.setFromCardField(DataHelper.getCard2());
                transferPage.getCancelTransfer();
                assertEquals(expected1, dashboardPage.getBalanceCard1());
                assertEquals(expected2, dashboardPage.getBalanceCard2());
            }

            @Test
            void shouldTransferAllTheMoney() {
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.isDashboardPage();
                int expected1 = dashboardPage.getBalanceCard1() + dashboardPage.getBalanceCard2();
                int expected2 = 0;
                String mount = dashboardPage.getBalanceCard2().toString();
                dashboardPage.refillCard1();
                TransferPage transferPage = new TransferPage();
                transferPage.paymentVisible();
                transferPage.setAmount(mount);
                transferPage.setFromCardField(DataHelper.getCard2());
                transferPage.getTransfer();
                assertEquals(expected1, dashboardPage.getBalanceCard1());
                assertEquals(expected2, dashboardPage.getBalanceCard2());
            }
        }

        @Nested
        public class NegativeScenario {

            //проверка на ошибку, если номер карты для перевода неверный
            @Test
            void shouldErrorWhenTransferFromInvalidCard() {
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.isDashboardPage();
                dashboardPage.refillCard1();
                TransferPage transferPage = new TransferPage();
                transferPage.paymentVisible();
                transferPage.setAmount(amount);
                transferPage.setFromCardField(DataHelper.getWrongCard());
                transferPage.getTransfer();
                transferPage.invalidTransfer();
            }

            //проверка на появление ошибки, если перевод происходит на карту с тем же номером
            @Test
            void shouldErrorTransferFromCard1toCard1() {
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.isDashboardPage();
                dashboardPage.refillCard1();
                TransferPage transferPage = new TransferPage();
                transferPage.paymentVisible();
                transferPage.setAmount(amount);
                transferPage.setFromCardField(DataHelper.getCard1());
                transferPage.getTransfer();
                transferPage.invalidTransfer();
            }

            //проверка на появление ошибки, что необходимо ввести сумму не равную нулю
            @Test
            void shouldErrorTransferWhenAmountZero() {
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.isDashboardPage();
                int expected1 = dashboardPage.getBalanceCard1();
                int expected2 = dashboardPage.getBalanceCard2();
                dashboardPage.refillCard1();
                TransferPage transferPage = new TransferPage();
                transferPage.paymentVisible();
                transferPage.setAmount(amountZero);
                transferPage.setFromCardField(DataHelper.getCard2());
                transferPage.getTransfer();
                assertEquals(expected1, dashboardPage.getBalanceCard1());
                assertEquals(expected2, dashboardPage.getBalanceCard2());
                transferPage.invalidTransfer();
            }

            //проверка на появление ошибки, если на счету недостаточно средств
            @Test
            void shouldErrorTransferWhenNotEnoughBalance() {
                DashboardPage dashboardPage = new DashboardPage();
                dashboardPage.isDashboardPage();
                dashboardPage.refillCard2();
                TransferPage transferPage = new TransferPage();
                transferPage.paymentVisible();
                transferPage.setAmount(amountOverLimit);
                transferPage.setFromCardField(DataHelper.getCard1());
                transferPage.getTransfer();
                transferPage.invalidTransfer();
            }
        }
    }

    @Nested
    public class InvalidLogin {
        @Test
        void shouldInvalidLogin() {
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getWrongAuthInfo();
            loginPage.invalidLogin(authInfo);
        }

        @Test
        void shouldInvalidVerify() {
            val loginPage = new LoginPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = loginPage.validLogin(authInfo);
            val codeVerify = DataHelper.getInvalidVerificationCode();
            verificationPage.invalidVerify(codeVerify);
        }
    }
}
