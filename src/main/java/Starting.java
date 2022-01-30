import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Starting {
    public static void main(String[] args) throws LoginException {
        JDABuilder builder = JDABuilder.createDefault("NzY5NzU2NjkwNDg4MjI5ODk4.X5TpyA.Pgl1OeaEASmE6K39LKKd2GD6Zyk");
        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Disable compression (not recommended)
        builder.setCompression(Compression.NONE);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.playing("감시"));

        builder.addEventListeners(new DiscordMessage());

        builder.build();
    }
}
