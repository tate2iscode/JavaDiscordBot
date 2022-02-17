package onMessage.coin;

import net.dv8tion.jda.api.entities.User;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import static onMessage.coin.CallCoinPrice.CallCoinsList;
import static onMessage.coin.CallCoinPrice.coins;
import static onMessage.coin.CoinMemberDatas.*;

public class CoinMemberTrade {
    public static int buyCoin(User user, Integer BuyNum, String CoinName) throws IOException, ParseException {
        SetMember(user);
        HashMap<String, Integer> data = RequestDatas(user.getDiscriminator());
        HashMap<String, Integer> coinsPrice = CallCoinsList();
        if(!data.containsKey(CoinName)) {
            //System.out.println(1);
            return 2;
        }

        //System.out.println(coinsPrice);
        //System.out.println(CoinName);
        if(coinsPrice.get("KRW-"+CoinName)*BuyNum > data.get("asset")) {
            //System.out.println(2);
            return 1;
        }
        int asset = data.get("asset") - coinsPrice.get("KRW-"+CoinName)*BuyNum;
        int coinNum = data.get(CoinName) + BuyNum;
        data.put("asset", asset);
        data.put(CoinName, coinNum);
        SaveDatas(data, user.getDiscriminator());
        return 0;
    }

    public static int sellCoin(User user, Integer SellNum, String CoinName) throws IOException, ParseException {
        SetMember(user);
        HashMap<String, Integer> data = RequestDatas(user.getDiscriminator());
        HashMap<String, Integer> coinsPrice = CallCoinsList();
        if(!data.containsKey(CoinName)) {
            //System.out.println(3);
            return 2;
        }
        if(SellNum > data.get(CoinName)) {
            //System.out.println(4);
            return 1;
        }
        int asset = data.get("asset") + coinsPrice.get("KRW-"+CoinName)*SellNum;
        data.put("asset", asset);
        data.put(CoinName, data.get(CoinName)-SellNum);
        SaveDatas(data, user.getDiscriminator());
        return 0;
    }

    public String transName(int name) {
        return coins[name];
    }

    public static String wallet(User user) throws IOException, ParseException {
        int asset = 0;
        int money = 0;
        HashMap<String, Integer> coinsPrice = CallCoinsList();
        StringBuilder result = new StringBuilder("");
        HashMap<String, Integer> data = RequestDatas(user.getDiscriminator());

        result.append(user.getName()).append("의 지갑 \n");

        for(String name : data.keySet()) {
            if(Objects.equals(name, "asset")) {
                money = data.get(name);
            } else {
                result.append(name).append(" : ").append(data.get(name)).append("\n");
                asset = asset + data.get(name) * coinsPrice.get("KRW-"+name);
            }
        }
        result.append("현금 : ").append(money).append("\n");
        result.append("총 자산 : ").append(money + asset);
        return result.toString();
    }
}
