package Controller;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DiscordHelper {


    private static String prefix = "\\.";

    public String parseInput(MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return null;
        String input[] = event.getMessage().getContentDisplay().split(prefix);
        if (input.length != 2)
            return null;

        return input[1].toLowerCase();
    }

    public String bold(String name)
    {
        return Markup.BOLD.value() + name + Markup.BOLD.value();
    }

    public String strike(String name)
    {
        return Markup.STRIKE.value() + name + Markup.STRIKE.value();
    }

    public enum Markup
    {
        BOLD("**"),
        STRIKE("~~");

        private String mark;

        Markup(String mark) {
            this.mark = mark;
        }

        public String value()
        {
            return mark;
        }
    }
}


