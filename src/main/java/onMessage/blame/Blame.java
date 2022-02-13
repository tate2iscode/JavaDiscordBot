package onMessage.blame;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Blame {
    public static void blame(User user, TextChannel textChannel, Message message) throws IOException {
        if(DetectBadWorld(StringReplace(message.getContentRaw()))) {
            message.delete().queue();
            textChannel.sendMessage(user.getName()+" 욕설 감지").queue();
        }
    }

    public static boolean  DetectBadWorld(String text) throws IOException { // 띄어쓰기 다 한단어 한단어 인식해서 가장 높은거 기준
        String[] part = text.split(" ");
        List<Double> list = new ArrayList<Double>();

        for(String t : part) {
            double add = Percent(t);
            //System.out.println(add);
            list.add(add);
        }

        double p = Collections.max(list);
        return p > 0.8; // true == 욕 & false == 정상
    }

    public static double Percent(String text) throws IOException {
        String url = "http://101.101.218.78:5555/DiscordApi?text=";
        url = url+text;
        Connection conn = Jsoup.connect(url);
        Document html = conn.get();
        //System.out.println(html.toString());
        String html_s = html.toString();
        Document doc = Jsoup.parse(html_s);
        String onlyText = doc.text();
        //System.out.println(onlyText);

        String num = onlyText.substring(1,onlyText.length()-1);
        //System.out.println(num);
        return Double.parseDouble(num);
    }

    public static String StringReplace(String str){
        String match = "[^\uAC00-\uD7A30-9a-zA-Z]";
        str = str.replace("^","ㅅ");
        str = str.replaceAll(match, "");
        //System.out.println(str);
        return str;
    }
}
