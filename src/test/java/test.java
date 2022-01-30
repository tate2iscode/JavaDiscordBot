import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        DiscordMessage discordMessage = new DiscordMessage();
        boolean a = discordMessage.DetectBadWorld("하이 씨발");
        System.out.println(a);
    }
}
