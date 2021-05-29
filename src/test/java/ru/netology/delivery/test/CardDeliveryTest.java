package ru.netology.delivery.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }


    @Test
    void validFiled() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        val daysToAddForFirstMeeting = 4;
        $("[type='tel']").sendKeys(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(60000));
        $$(".notification__content").findBy(text("Встреча успешно запланирована на " + DataGenerator.generateDate(daysToAddForFirstMeeting))).shouldBe(visible, Duration.ofSeconds(60000));
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        val daysToAddForSecondMeeting = 7;
        $("[type='tel']").sendKeys(DataGenerator.generateDate(daysToAddForSecondMeeting));
        $$("button").find(exactText("Запланировать")).click();
        $(withText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(visible, Duration.ofSeconds(60000));
        $$(".button__text").find(text("Перепланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(60000));
        $$(".notification__content").findBy(text("Встреча успешно запланирована на " + DataGenerator.generateDate(daysToAddForSecondMeeting))).shouldBe(visible, Duration.ofSeconds(60000));
    }

    @Test
    void invalidFiledCity() {
        $("[placeholder='Город']").setValue("США");
        $(byText("Запланировать")).click();
        $("[data-test-id='city']").shouldBe(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void emptyFiledCity() {
        $(byText("Запланировать")).click();
        $("[data-test-id='city']").shouldBe(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void invalidFileDate() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        $("[type='tel']").setValue("22.01.2000");
        $(byText("Запланировать")).click();
        $(".calendar-input__custom-control ").shouldBe(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidFiledName() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        val daysToAddForFirstMeeting = 4;
        $("[type='tel']").sendKeys(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $("[data-test-id='name'] input").setValue("Petrov Ivan");
        $(byText("Запланировать")).click();
        $("[data-test-id='name']").shouldBe(text("Имя и Фамилия указаные неверно."));

    }

    @Test
    void emptyFiledName() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        val daysToAddForFirstMeeting = 4;
        $("[type='tel']").sendKeys(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $(byText("Запланировать")).click();
        $("[data-test-id='name'] .input__sub").shouldBe(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void emptyFiledNumber() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        val daysToAddForFirstMeeting = 4;
        $("[type='tel']").sendKeys(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName("ru"));
        $(byText("Запланировать")).click();
        $("[data-test-id='phone']").shouldBe(text("Поле обязательно для заполнения"));
    }

    @Test
    void invalidCheckbox() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        val daysToAddForFirstMeeting = 4;
        $("[type='tel']").sendKeys(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $(byText("Запланировать")).click();
        $(".checkbox_checked").shouldBe(hidden);
    }

    @Test
    void invalidSecondFiledPhone() {
        $("[placeholder='Город']").setValue(DataGenerator.generateCity("ru"));
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        val daysToAddForFirstMeeting = 4;
        $("[type='tel']").sendKeys(DataGenerator.generateDate(daysToAddForFirstMeeting));
        $("[data-test-id='name'] input").setValue(DataGenerator.generateName("ru"));
        $("[name='phone']").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(60000));
        $$(".notification__content").findBy(text("Встреча успешно запланирована на " + DataGenerator.generateDate(daysToAddForFirstMeeting))).shouldBe(visible, Duration.ofSeconds(60000));
        $("[type='tel']").sendKeys(Keys.CONTROL, "a");
        $("[type='tel']").sendKeys(Keys.DELETE);
        $$("button").find(exactText("Запланировать")).click();
        $(".calendar-input__custom-control ").shouldBe(exactText("Неверно введена дата"));
    }

}
