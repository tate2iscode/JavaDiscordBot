import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;

import static onMessage.coin.CallCoinPrice.CallCoinsList;
import static onMessage.coin.CoinMemberDatas.RequestDatas;


public class test {
    public static void main(String[] args) throws IOException, ParseException {
        HashMap<String, Integer> data = RequestDatas("4171");
        System.out.println(data.keySet());
        //System.out.println(walletTest("4171"));
    }
}
