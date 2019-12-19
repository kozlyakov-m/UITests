import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.commands.PressEnter;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MessagesPage{
    private SelenideElement lastMessage = $x("//div[@id='message-box']/div/div[last()]/div/div[2]/div[2]");
    private SelenideElement lastMessageMenuBtn = $x("//div[@id='message-box']/div/div[last()]//*[@data-icon='ellipsis-h']");
    private ElementsCollection messages = $$x("//div[@id='message-box']/div/div/div");
    private ElementsCollection chats = $$(".bg-blue-200>div>div");
    private SelenideElement deletedSuccessMsg = $x("//*[@data-icon='check-circle']/../..");

    public MessagesPage(){
        open("https://goodworkfor.life/");
        $("#email").setValue("guest@example.com");
        $("#password").setValue("guestpass").pressEnter();
        $x("//*[@data-icon='envelope']").click(); // svg[@data-icon='envelope'] почему-то не находит
    }


    public void selectChat(int position){
        chats.get(position).click();
    }

    public ElementsCollection getMessages(){
        return messages;
    }

    public void sendMessage(String text){
        $("#send-message").setValue(text).pressEnter();
    }

    public SelenideElement getLastMessage(){
        return lastMessage;
    }

    public void deleteMessage(){
        lastMessageMenuBtn.click();
        $(byText("Delete")).click(); //сломается, если кто-нибудь отправит сообщение с текстом "Delete"
    }

    public SelenideElement getDeletedSuccessMsg(){
        return deletedSuccessMsg;
    }
}