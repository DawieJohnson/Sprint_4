package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class PageScooterOrderAboutRent {

    private WebDriver driver;

    //локатор заголовка страницы "Про аренду"
    private By titlePageAboutRent = By.xpath(".//div[text()='Про аренду']");
    //локатор поля "Когда привезти самокат" (ввести в формате хх.хх.хххх)
    private By fieldDeliveryDate = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    //локатор кнопки поля "срок аренды"
    private By buttonRentalPeriod = By.xpath(".//span[@class='Dropdown-arrow']");
    //локатор поля "Комментарий для курьера"
    private By fieldComment = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    //локатор кнопки "Заказать"
    private By buttonOrder = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    //локатор кнопки "Да" (хотите оформить заказ?)
    private By buttonYesOrder = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Да']");
    //локатор сообщения об успешном заказе (уточненный)
    private By messageOrderOk = By.xpath(".//div[@class='Order_ModalHeader__3FDaJ' and contains(., 'Заказ оформлен')]");

    //конструктор класса
    public PageScooterOrderAboutRent(WebDriver driver) {
        this.driver = driver;
    }

    //метод подтверждения перехода на страницу "Про аренду"
    public boolean pageAboutRentIsDisplayed(){
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return driver.findElement(titlePageAboutRent).isDisplayed();
    }

    //метод выбора даты
    public void setDeliveryDate(String date) {
        driver.findElement(fieldDeliveryDate).sendKeys(date);
    }

    //метод выбора срока аренды
    public void setRentalPeriod(String rentalPeriod) {
        driver.findElement(buttonRentalPeriod).click();
        driver.findElement(By.xpath(".//div[text()='" + rentalPeriod +"']")).click();
    }

    //метод выбора цвета самоката
    public void setColorScooter(String colorScooter) {
        driver.findElement(By.id(colorScooter)).click();
    }

    //метод ввода комментария
    public void setComment(String comment) {
        driver.findElement(fieldComment).sendKeys(comment);
    }

    //нажать кнопку "Заказать"
    public void clickOrderButton() {
        driver.findElement(buttonOrder).click();
    }

    //нажать кнопку "Да"
    public void clickYesButton() {
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(buttonYesOrder));
        driver.findElement(buttonYesOrder).click();
    }

    //проверяем что заказ оформлен
    public String getOrderSuccessMessage() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(messageOrderOk));
        return driver.findElement(messageOrderOk).getText();
    }
}