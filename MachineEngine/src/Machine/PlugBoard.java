package Machine;
import java.util.HashMap;
import java.util.Map;

public class PlugBoard {
    private final Map<Character,Character> plugs;
    public PlugBoard(Map<Character, Character> plugs){this.plugs=plugs;}
    public Map<Character, Character> getPlugs(){
        return plugs;
    }
    public char getValue(char key){
        return plugs.getOrDefault(key, key);
    }


}



