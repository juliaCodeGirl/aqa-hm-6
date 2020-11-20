package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement paymentPage = $(byText("Пополнение карты"));
    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement fromCardField = $("[data-test-id='from'] input");
    private SelenideElement transfer = $("[data-test-id='action-transfer']");
    private SelenideElement cancelTransfer = $("[data-test-id='action-cancel']");
    private SelenideElement errorTransfer = $("[data-test-id='error-notification']");

    public void paymentVisible() {
        paymentPage.shouldBe(visible);
    }

    public void setAmount(String sum) {
        amount.setValue(sum);
    }

    public void setFromCardField(String numberCard) {
        fromCardField.setValue(numberCard);
    }

    public DashboardPage getTransfer() {
        transfer.click();
        return new DashboardPage();
    }

    public void invalidTransfer() {
        transfer.click();
        errorTransfer.shouldBe(visible);
    }

    public DashboardPage getCancelTransfer() {
        cancelTransfer.click();
        return new DashboardPage();
    }
}
