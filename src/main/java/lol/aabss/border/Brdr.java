package lol.aabss.border;

public class Brdr {
    public static String getConfig(String s){
        Border.getPlugin(Border.class).getConfig().get(s);
        return (String) Border.getPlugin(Border.class).getConfig().get(s);
    }

    public static Boolean configBoolean(String s){
        Border.getPlugin(Border.class).getConfig().get(s);
        return (Boolean) Border.getPlugin(Border.class).getConfig().get(s);
    }

    public static Number configNumber(String s){
        Border.getPlugin(Border.class).getConfig().get(s);
        return (Number) Border.getPlugin(Border.class).getConfig().get(s);
    }

}
