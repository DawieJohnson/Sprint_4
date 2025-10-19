import model.PageScooterOrderAboutRent;
import model.PageScooterOrderForWho;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class PageOrderScooterTest {
    private static final String ORDER_PAGE_URL = "https://qa-scooter.praktikum-services.ru/order";
    private static final String BROWSER_NAME = "Firefox"; //задаем браузер Chrome или Firefox

    private WebDriver driver;

    //создаем браузер
    @Before
    public void before() {
        driver = BrowserSelect.selectDriverBrowser(BROWSER_NAME);
    }

    //поля класса
    private final String name;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String colorScooter;
    private final String comment;

    //конструктор класса
    public PageOrderScooterTest(String name, String lastName, String address, String metroStation, String phone,
                                String date, String rentalPeriod, String colorScooter, String comment) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.colorScooter = colorScooter;
        this.comment = comment;
    }

    //тестовые данные
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Александр", "Ляховский", "Москва", "Аэропорт", "89258420051", "07.10.2025", "сутки", "black", "Спасибо"},
                {"Иван", "Иванов", "Тверская", "Тверская", "+79992221111", "01.12.2024", "двое суток", "grey", "Без комментариев"},
        };
    }

    @Test
    public void testMakingOrder() {
        driver.get(ORDER_PAGE_URL);
        PageScooterOrderForWho pageScooterOrderForWho = new PageScooterOrderForWho(driver);
        pageScooterOrderForWho.closeCokie(); // закрываем куки
        pageScooterOrderForWho.testFieldName(name); //заполняем поле "имя" значением из тестового набора
        pageScooterOrderForWho.testFieldLastName(lastName); //заполняем поле "фамилия" значением из тестового набора
        pageScooterOrderForWho.testFieldAddress(address); //заполняем поле "адрес"
        pageScooterOrderForWho.testFieldMetroStation(metroStation); //заполняем поле "станция метро"
        pageScooterOrderForWho.testFieldPhone(phone); //заполняем поле "телефон"
        pageScooterOrderForWho.pushButtonNext();// нажимаем кнопку "Далее"
        PageScooterOrderAboutRent pageScooterOrderAboutRent = new PageScooterOrderAboutRent(driver); //создаем объект страницы "про аренду"
        pageScooterOrderAboutRent.pageAboutRentIsDisplayed(); //проверяем переход на страницу "про аренду"
        pageScooterOrderAboutRent.setDeliveryDate(date); //заполняем поле "дата доставки"
        pageScooterOrderAboutRent.setRentalPeriod(rentalPeriod); //заполняем поле "срок аренды"
        pageScooterOrderAboutRent.setColorScooter(colorScooter); //выбираем цвет самоката
        pageScooterOrderAboutRent.setComment(comment); //заполняем поле "комментарий
        pageScooterOrderAboutRent.clickOrderButton(); //нажимаем кнопку "Заказать"
        pageScooterOrderAboutRent.clickYesButton(); //нажимаем кнопку "Да"

        //проверяем что заказ оформлен
        assertTrue(pageScooterOrderAboutRent.getOrderSuccessMessage().contains("Заказ оформлен"));
        assertTrue(pageScooterOrderAboutRent.getOrderSuccessMessage().contains("Номер заказа:"));
        assertTrue(pageScooterOrderAboutRent.getOrderSuccessMessage().contains("Запишите его:"));
        assertTrue(pageScooterOrderAboutRent.getOrderSuccessMessage().contains("пригодится, чтобы отслеживать статус"));

        driver.quit();
    }

    @After
    public void after() {
        driver.quit();
    }
}