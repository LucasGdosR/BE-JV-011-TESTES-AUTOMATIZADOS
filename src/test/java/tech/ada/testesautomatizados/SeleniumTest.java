package tech.ada.testesautomatizados;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTest {

    private WebDriver driver;
    String path = "C:\\Users\\luizs\\IdeaProjects\\BE-JV-011-TESTES-AUTOMATIZADOS\\src\\main\\resources\\index.html";
    @BeforeEach
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver","F:\\Luizf\\Downloads\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver(chromeOptions);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void testListAllBooks() {
        driver.get(path);
        WebElement table = driver.findElement(By.id("bookTable"));
        // Assert the table contains the expected number of rows and columns
        assertEquals(1, table.findElements(By.tagName("tr")).size());
    }

    @Test
    public void testFindBookById() {
        driver.get(path);
        WebElement bookIdInput = driver.findElement(By.id("bookId"));
        WebElement findByIdBtn = driver.findElement(By.id("findByIdBtn"));
        WebElement foundBook = driver.findElement(By.id("foundBook"));

        bookIdInput.sendKeys("123");
        findByIdBtn.click();


        assertEquals("ISBN: 123, Title: book test", foundBook.getText());
    }

    @Test
    public void testSaveBook() {
        driver.get(path);
        WebElement isbnInput = driver.findElement(By.id("isbn"));
        WebElement titleInput = driver.findElement(By.id("title"));
        WebElement resumoInput = driver.findElement(By.id("resumo"));
        WebElement priceInput = driver.findElement(By.id("price"));
        WebElement numberOfPagesInput = driver.findElement(By.id("numberOfPages"));

        WebElement saveBtn = driver.findElement(By.id("saveBtn"));
        WebElement savedBook = driver.findElement(By.id("savedBook"));

        isbnInput.sendKeys("123");
        titleInput.sendKeys("book test");
        resumoInput.sendKeys("resumo test");
        priceInput.sendKeys("30.00");
        numberOfPagesInput.sendKeys("300");

        saveBtn.click();

        assertEquals("Livro adicionado", savedBook.getText());
    }

    @Test
    public void testEditBook() {
        driver.get(path);
        WebElement editBookIdInput = driver.findElement(By.id("editBookId"));
        WebElement editIsbnInput = driver.findElement(By.id("editIsbn"));
        WebElement editTitleInput = driver.findElement(By.id("editTitle"));
        WebElement editResumoInput = driver.findElement(By.id("editResumo"));
        WebElement editPriceInput = driver.findElement(By.id("editPrice"));
        WebElement editNumberOfPagesInput = driver.findElement(By.id("editNumberOfPages"));
        WebElement editPublishingDateInput = driver.findElement(By.id("editPublishingDate"))
                ;
        WebElement editBtn = driver.findElement(By.id("editBtn"));
        WebElement editedBook = driver.findElement(By.id("editedBook"));

        editBookIdInput.sendKeys("123");
        editIsbnInput.sendKeys("9876543210");
        editTitleInput.sendKeys("Updated Book");


        editBtn.click();


        assertEquals("Book edited: ID: 123, Title: Updated Book", editedBook.getText());
    }

    @Test
    public void testDeleteBook() {
        driver.get(path);
        WebElement deleteBookIdInput = driver.findElement(By.id("deleteBookId"));
        WebElement deleteBtn = driver.findElement(By.id("deleteBtn"));
        WebElement deletedBook = driver.findElement(By.id("deletedBook"));

        deleteBookIdInput.sendKeys("123");

        deleteBtn.click();


        assertEquals("Livro Excluído ISBN: 123", deletedBook.getText());


    }

}
