package onMessage.coin;

import net.dv8tion.jda.api.entities.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static onMessage.coin.CallCoinPrice.coins;

public class CoinMemberDatas {

    public static void SetMember(User user) throws IOException {
        String userName = user.getDiscriminator();
        //HashMap<String, Integer> data = RequestDatas();
        String[] list = coins;
        String path = "datas/membersCoinDatas/"+userName+".txt";
        File f = new File(path);
        if(!f.exists()) {
            f.createNewFile();

            HashMap<String, Integer> data = RequestDatas(userName);
            data.put("asset",1000000);
            for(String name : list) {
                data.put(name, 0);
            }
            SaveDatas(data, userName);
        }
    }

    //public static HashMap<String,String> DataSaves;

    public static HashMap<String,Integer> RequestDatas(String userName) {
        HashMap<String, Integer> map_from_file = new HashMap<String, Integer>();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("datas/membersCoinDatas/"+userName+".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String key : properties.stringPropertyNames()) {
            map_from_file.put(key, Integer.parseInt(properties.get(key).toString()));
        }
        return map_from_file;
    }

    public static void SaveDatas(HashMap<String,Integer> data, String userName) {
        Properties properties = new Properties();
        HashMap<String, String> result = new HashMap<>();
        for(String key : data.keySet()) {
            result.put(key,String.valueOf(data.get(key)));
        }

        for (Map.Entry<String, String> entry : result.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }

        try {
            properties.store(new FileOutputStream("datas/membersCoinDatas/"+userName+".txt"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
