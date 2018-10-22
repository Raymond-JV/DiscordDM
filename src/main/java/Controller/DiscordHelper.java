package Controller;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DiscordHelper {

    public String parseInput(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot())
            return null;

        String input[] = event.getMessage().getContentDisplay().split("!");
        if (input.length != 2)
            return null;

        return input[1];
    }
}
