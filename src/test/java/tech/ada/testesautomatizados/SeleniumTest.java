package tech.ada.testesautomatizados;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumTest {

    private WebDriver driver;
    String path = "C:\\Users\\T-GAMER\\IdeaProjects\\testes-automatizados\\src\\main\\resources\\index.html";
    @BeforeEach
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver","D:\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(400));
    }

//    @AfterEach
//    public void tearDown() {
//        driver.quit();
//    }
    @Test
    @Order(2)
    public void testListAllBooks() {
        driver.get(path);

        WebElement table = driver.findElement(By.id("bookTable"));
        // Assert the table contains the expected number of rows and columns
        assertEquals(2, table.findElements(By.tagName("tr")).size());
    }

    @Test
    @Order(3)
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
    @Order(1)
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
        priceInput.sendKeys("3000");
        numberOfPagesInput.sendKeys("300");

        saveBtn.click();

        assertEquals("Livro adicionado", savedBook.getText());
    }

    @Test
    @Order(4)
    public void testEditBook() {
        driver.get(path);

        WebElement editIsbnInput = driver.findElement(By.id("editIsbn"));
        WebElement editTitleInput = driver.findElement(By.id("editTitle"));
        WebElement editResumoInput = driver.findElement(By.id("editResumo"));
        WebElement editPriceInput = driver.findElement(By.id("editPrice"));
        WebElement editNumberOfPagesInput = driver.findElement(By.id("editNumberOfPages"));
        WebElement editPublishingDateInput = driver.findElement(By.id("editPublishingDate"));

        WebElement editBtn = driver.findElement(By.id("editBtn"));
        WebElement editedBook = driver.findElement(By.id("editedBook"));

        editIsbnInput.sendKeys("123");
        editTitleInput.sendKeys("Updated Book");
        editResumoInput.sendKeys("Updated Resumo");
        editPriceInput.sendKeys("3000");
        editNumberOfPagesInput.sendKeys("300");

        editBtn.click();

        assertEquals("Book edited: ID: 123, Title: Updated Book", editedBook.getText());
    }

    @Test
    @Order(5)
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
