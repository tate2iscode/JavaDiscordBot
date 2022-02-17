package onMessage.coin;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static onMessage.DiscordMessage.detectCommandofCommand;
import static onMessage.DiscordMessage.detectCommandofCommandCoin;
import static onMessage.coin.CallCoinPrice.CallCoinsList;
import static onMessage.coin.CallCoinPrice.coins;
import static onMessage.coin.CoinMemberTrade.*;

public class Coin {
    public static void MAIN(String text, User user, TextChannel textChannel) throws IOException, ParseException {
        if(Objects.equals(detectCommandofCommand(text), "가격")) {
            //textChannel.sendMessage("가격").queue();
            HashMap<String, Integer> hashMap = CallCoinsList();
            //System.out.println(hashMap);
            StringBuilder result = new StringBuilder();
            result.append("현재 실제 가격").append("\n");
            int i = 0;
            for(String name : coins) {
                result.append(i).append(" ").append(name).append(" : ").append(hashMap.get("KRW-"+name)).append("\n");
                i++;
            }
            textChannel.sendMessage(result).queue();
        } else if(Objects.equals(detectCommandofCommand(text), "돈")) {
            textChannel.sendMessage(user.getDiscriminator()).queue();
        } else if(Objects.equals(detectCommandofCommand(text), "매수")) {
            String coinName = detectCommandofCommandCoin(text, 2);
            String num = detectCommandofCommandCoin(text, 3);
            int state = buyCoin(user, Integer.parseInt(num), coinName);
            if(state == 1) {
                textChannel.sendMessage(user.getName()+" 다시 적어주세요").queue();
            } else {
                textChannel.sendMessage(user.getName()+" 매수성공").queue();
            }
        } else if(Objects.equals(detectCommandofCommand(text), "매도")) {
            String coinName = detectCommandofCommandCoin(text, 2);
            String num = detectCommandofCommandCoin(text, 3);
            int state = sellCoin(user, Integer.parseInt(num), coinName);
            if (state == 1) {
                textChannel.sendMessage(user.getName() + " 돈 또는 코인이 부족합니다").queue();
            } else if(state == 2) {
                textChannel.sendMessage(user.getName() + " 코인 이름이 틀립니.").queue();
            } else {
                textChannel.sendMessage(user.getName() + " 매도성공").queue();
            }
        } else if(Objects.equals(detectCommandofCommand(text), "지갑")) {
            textChannel.sendMessage(wallet(user)).queue();
        }
    }
}
