package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private SelenideElement head = $("[data-test-id='dashboard']");

    private SelenideElement card1Button = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']>[data-test-id='action-deposit']");
    private SelenideElement card2Button = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']>[data-test-id='action-deposit']");
    private SelenideElement balanceCard1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private SelenideElement balanceCard2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");



    public void isDashboardPage() {
        head.shouldBe(visible);
    }

    public TransferPage refillCard1() {
        card1Button.click();
        return new TransferPage();
    }

    public TransferPage refillCard2() {
        card2Button.click();
        return new TransferPage();
    }

    public Integer getBalanceCard1() {
        String[] text = balanceCard1.getText().split(",");
        String bal = text[text.length-1].replaceAll("[^\\d-]+", "");
        Integer balance = Integer.parseInt(bal);
        return balance;
    }

    public Integer getBalanceCard2() {
        String[] text = balanceCard2.getText().split(",");
        String bal = text[text.length-1].replaceAll("[^\\d-]+", "");
        Integer balance = Integer.parseInt(bal);
        return balance;
    }


}
