package Game;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class TestListener extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        /*
        if (event.getAuthor().isBot())
            return;

        String formatted_response = String.format("user: \"%s\"\nmessage: \"%s\"\n", event.getAuthor().getName(), event.getMessage());
        TextChannel chat = event.getTextChannel();

        String name = event.getAuthor().getName();


            chat.sendMessage(formatted_response).queue();

        System.out.println(formatted_response);
        Message respone = new MessageBuilder().append(formatted_response).build();

        super.onMessageReceived(event);*/
    }
}