package onMessage.coin;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Objects;

public class CallCoinPrice {
    static NumberFormat f = NumberFormat.getInstance();
    static {
        f.setGroupingUsed(false);
    }

    static JSONParser jsonParser = new JSONParser();
    static String[] coins = {"BTC","XRP","ETH","SAND","DOGE","XLM","ADA","NEAR"};

    public int CallCoin(String CoinName) throws IOException, ParseException {
        String url = String.format("https://api.upbit.com/v1/ticker?markets=%s",CoinName);
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        String content = doc.text();
        content = content.substring(1,content.length()-1);

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(content);
        JSONObject jsonObject = (JSONObject) obj;
        Double data = (Double) jsonObject.get("trade_price");
        return Integer.parseInt(f.format(data));
    }

    public static HashMap<String, Integer> CallCoinsList() throws IOException, ParseException {
        HashMap<String,Integer> result = new HashMap<>();
        String url = CoinAPI(coins);
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        String content = doc.text();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(content);
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            String name = (String) jsonObject.get("market");
            Double price_double = (Double) jsonObject.get("trade_price");
            String price = f.format(price_double).replaceAll(",","");
            //System.out.println(price);
            result.put(name, Integer.parseInt(price));
        }
        return result;
    }

    public Boolean confirm(String[] list, String name) {
        for (String s : list) {
            if (Objects.equals(s, name)) {
                return true;
            }
        }
        return false;
    }

    public static String CoinAPI(String[] coins) {
        StringBuilder url = new StringBuilder("https://api.upbit.com/v1/ticker?");
        String add = "markets=%s&";
        for (String coin : coins) {
            url.append(String.format(add, "KRW-" + coin));
        }
        return url.toString();
    }
}