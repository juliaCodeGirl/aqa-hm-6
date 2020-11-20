package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

private String authUrl = "http://localhost:9999";
    private SelenideElement login = $("[data-test-id=\"login\"] input");
    private SelenideElement password = $("[data-test-id=\"password\"] input");
    private SelenideElement buttonLogin = $("[data-test-id=\"action-login\"]");
    private SelenideElement errorTransfer = $("[data-test-id='error-notification']");

    public LoginPage() {
        open(authUrl);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        login.setValue(info.getLogin());
        password.setValue(info.getPassword());
        buttonLogin.click();
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo info) {
        login.setValue(info.getLogin());
        password.setValue(info.getPassword());
        buttonLogin.click();
        errorTransfer.shouldBe(visible);
    }

}
