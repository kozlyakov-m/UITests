import com.codeborne.selenide.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;


import static com.codeborne.selenide.Selenide.*;


public class UITests
{
    static MessagesPage page;
    @BeforeAll
    public static void configureSelenide(){//тесты зависят друг от друга и идут по порядку, залогиниваться каждый раз не нужно
        Configuration.startMaximized = true;
        page = new MessagesPage();
    }

    /*@AfterEach
    public void clear(){
        //может быть реализовать метод logout и вызыать или вообще убрать это в деструктор?
        clearBrowserCookies();
    }*/

    @Test
    @DisplayName("Отобразилось модальное окно сообщений")
    public void Test1() {
        //MessagesPage page = new MessagesPage();
        $x("//*[@id='direct-message-box']/div") //стоит ли вынести в метод класса MessagePage?
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Your Messages"));
    }

    @Test
    @DisplayName("Отображаеся список сообщений")
    public void Test2() {

        //Assert.assertFalse(page.getMessages().isEmpty());
        page.selectChat(1);
        $("#message-box").shouldNotHave(Condition.text("Click on the profile pic on left to see interaction with that user"));

    }

    @Test
    @DisplayName("Отправить сообщение")
    public void Test3(){
        //sleep(5000); //ждем загрузку сообщений ("умное ожидание" selenide здесь не работает)
        page.getMessages().shouldHave(CollectionCondition.sizeGreaterThan(0));
        int numberOfMessages = page.getMessages().size();
        String msg = "test";
        page.sendMessage(msg);
        sleep(5000); //без sleep не работает!
        int newNumberOfMessages = page.getMessages().size();

        //System.out.println(numberOfMessages);
        //System.out.println(newNumberOfMessages);

        if(numberOfMessages != 1) { //если сообшений нет, то в коллекции будет сообщение об отсутствие сообщений, которое заменится на msg
            Assert.assertTrue(numberOfMessages - newNumberOfMessages <= -1); // == не подходит из-за возможного сообщения с датой от системы
        }
        Assert.assertEquals(msg, page.getLastMessage().text());
    }

    @Test
    @DisplayName("Удалить сообщение")
    public void Test4(){
        int numberOfMessages = page.getMessages().size();
        page.deleteMessage();


        SelenideElement deleted = page.getDeletedSuccessMsg();
        deleted
            .should(Condition.exist)
            .shouldBe(Condition.visible)
            //.shouldHave(Condition.text("The direct message has been deleted")); //текст может поменяться, не стоит это тестировать
            .should(Condition.disappear);

        //sleep(5000);
        int newNumberOfMessages = page.getMessages().size();

        if(numberOfMessages != 1) {
            Assert.assertTrue(numberOfMessages - newNumberOfMessages >= 1); // == не подходит из-за возможного сообщения с датой от системы
        }
    }


}
