package onMessage.coin;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static onMessage.DiscordMessage.detectCommandofCommand;
import static onMessage.coin.CallCoinPrice.CallCoinsList;
import static onMessage.coin.CallCoinPrice.coins;

public class Coin {
    public static void MAIN(String text, User user, TextChannel textChannel) throws IOException, ParseException {
        if(Objects.equals(detectCommandofCommand(text), "가격")) {
            //textChannel.sendMessage("가격").queue();
            HashMap<String, Integer> hashMap = CallCoinsList();
            //System.out.println(hashMap);
            StringBuilder result = new StringBuilder();
            result.append("현재 실제 가격").append("\n");
            for(String name : coins) {
                result.append(name).append(" : ").append(hashMap.get("KRW-"+name)).append("\n");
            }
            textChannel.sendMessage(result).queue();
        } else if(Objects.equals(detectCommandofCommand(text), "돈")) {
            textChannel.sendMessage("돈").queue();
        }
    }
}
