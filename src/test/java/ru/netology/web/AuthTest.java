package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationDto;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldCorrectRegister() {
        RegistrationDto validActiveUser = DataGenerator.validActiveUser();
        $("[name='login']").setValue(validActiveUser.getLogin());
        $("[name='password']").setValue(validActiveUser.getPassword());
        $("[data-test-id='action-login']").click();
        $(".heading").shouldBe(exist, Duration.ofDays(5000));
        $(".heading").shouldHave(text("Личный кабинет"));
    }

    @Test
    public void shouldCorrectRegisterBlocked() {
        RegistrationDto validBlockedUser = DataGenerator.validBlockedUser();
        $("[name='login']").setValue(validBlockedUser.getLogin());
        $("[name='password']").setValue(validBlockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible, Duration.ofDays(15000));
    }

    @Test
    public void shouldIncorrectLogin() {
        RegistrationDto inCorrectLogin = DataGenerator.inCorrectLogin();
        $("[name='login']").setValue(inCorrectLogin.getLogin());
        $("[name='password']").setValue(inCorrectLogin.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldBe(Condition.visible, Duration.ofDays(15000));
    }

    @Test
    public void shouldIncorrectPassword() {
        RegistrationDto inCorrectPassword = DataGenerator.inCorrectPassword();
        $("[name='login']").setValue(inCorrectPassword.getLogin());
        $("[name='password']").setValue(inCorrectPassword.getPassword());
        $("[data-test-id='action-login']").click();
        $(".notification_status_error").shouldBe(visible, Duration.ofDays(5000));
        $("[data-test-id=error-notification]").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}
