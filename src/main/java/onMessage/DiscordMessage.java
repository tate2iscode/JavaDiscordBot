package onMessage;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

import static onMessage.blame.Blame.blame;
import static onMessage.coin.Coin.MAIN;

// 0 == 도박
public class DiscordMessage extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent messageReceivedEvent) {
        User user = messageReceivedEvent.getAuthor();
        TextChannel textChannel = messageReceivedEvent.getTextChannel();
        Message message = messageReceivedEvent.getMessage();
        if(user.isBot()) return;
        //String text = StringReplace(message.getContentRaw());
        String text = message.getContentRaw();
        //if(detect(text)) {
        //    System.out.println(detectCommand(text));
        //    textChannel.sendMessage(text).queue();
        //    return;
        //}
        int state = 0;
        try {
            state = ActCommand(text, user, textChannel);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        if(state == 0) {
            return ;
        }


        //욕설 감지
        detectBlame(user, textChannel, message);
    }

    public void detectBlame(User user, TextChannel textChannel, Message message) {
        try {
            blame(user, textChannel, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String detectCommand(String text) {
        if(detect(text)) {
            StringBuffer sb = new StringBuffer();
            sb.append(text);
            String context = String.valueOf(sb.deleteCharAt(0));
            String[] ArrayString = context.split(" ");
            return ArrayString[0];
        } else {
            return "";
        }
    }

    public static String detectCommandofCommand(String text) {
        if(detect(text)) {
            StringBuffer sb = new StringBuffer();
            sb.append(text);
            String context = String.valueOf(sb.deleteCharAt(0));
            String[] ArrayString = context.split(" ");
            return ArrayString[1];
        } else {
            return "";
        }
    }

    public static String detectCommandofCommandCoin(String text, Integer num) {
        if(detect(text)) {
            StringBuffer sb = new StringBuffer();
            sb.append(text);
            String context = String.valueOf(sb.deleteCharAt(0));
            String[] ArrayString = context.split(" ");
            return ArrayString[num];
        } else {
            return "";
        }
    }


    public static boolean detect(String text) {
        String command = String.valueOf(text.charAt(0));
        return command.equals("$");
    }

    public int ActCommand(String text, User user, TextChannel textChannel) throws IOException, ParseException {
        if(Objects.equals(detectCommand(text), "코인")) {
            MAIN(text ,user ,textChannel);
            return 0;
        }
        return 1;
    }
}
