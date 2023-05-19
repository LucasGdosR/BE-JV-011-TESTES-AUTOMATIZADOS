package tech.ada.testesautomatizados;



import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver","/home/luiz/Documentos/chromedriver");
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void testListAllBooks() {
        driver.get("file:///home/luiz/Documentos/FrontEnd/index.html");
        WebElement table = driver.findElement(By.id("Lista de Livros"));
        // Assert the table contains the expected number of rows and columns
        assertEquals(4, table.findElements(By.tagName("tr")).size());
    }

    @Test
    public void testFindBookById() {
        driver.get("file:///home/luiz/Documentos/FrontEnd/index.html");
        WebElement bookIdInput = driver.findElement(By.id("bookId"));
        WebElement findByIdBtn = driver.findElement(By.id("findByIdBtn"));
        WebElement foundBook = driver.findElement(By.id("foundBook"));

        bookIdInput.sendKeys("123");
        findByIdBtn.click();

        // Assert the found book information is displayed correctly
        assertEquals("ID: 123, Title: Book Title", foundBook.getText());
    }

    @Test
    public void testSaveBook() {
        driver.get("file:///home/luiz/Documentos/FrontEnd/index.html");
        WebElement isbnInput = driver.findElement(By.id("isbn"));
        WebElement titleInput = driver.findElement(By.id("title"));
        WebElement saveBtn = driver.findElement(By.id("saveBtn"));
        WebElement savedBook = driver.findElement(By.id("savedBook"));

        isbnInput.sendKeys("1234567890");
        titleInput.sendKeys("New Book");
        // Set other input values if needed

        saveBtn.click();

        // Assert the book is saved successfully
        assertEquals("Book saved. Location: /api/books/1", savedBook.getText());
    }

    @Test
    public void testEditBook() {
        driver.get("file:///home/luiz/Documentos/FrontEnd/index.html");
        WebElement editBookIdInput = driver.findElement(By.id("editBookId"));
        WebElement editIsbnInput = driver.findElement(By.id("editIsbn"));
        WebElement editTitleInput = driver.findElement(By.id("editTitle"));
        WebElement editBtn = driver.findElement(By.id("editBtn"));
        WebElement editedBook = driver.findElement(By.id("editedBook"));

        editBookIdInput.sendKeys("123");
        editIsbnInput.sendKeys("9876543210");
        editTitleInput.sendKeys("Updated Book");
        // Set other input values if needed

        editBtn.click();

        // Assert the book is edited successfully
        assertEquals("Book edited: ID: 123, Title: Updated Book", editedBook.getText());
    }

    @Test
    public void testDeleteBook() {
        driver.get("file:///home/luiz/Documentos/FrontEnd/index.html");
        WebElement deleteBookIdInput = driver.findElement(By.id("Excluir um Livro"));
        WebElement deleteBtn = driver.findElement(By.id("Excluir"));
        WebElement deletedBook = driver.findElement(By.id("deletedBook"));

        deleteBookIdInput.sendKeys("123");

        deleteBtn.click();

        // Assert the book is deleted successfully
        assertEquals("Livro Excluído: ID: 123", deletedBook.getText());
    }

}
