import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        String test = "^^ㅣ발";
        test = test.replace("^","ㅅ");
        String[] list = test.split(" ");
        System.out.println(list.length);
        for(String p : list) {
            //System.out.println("");
            System.out.println(p);
            Starting a = new Starting();
        }
    }
}
