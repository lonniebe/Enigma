package Machine.DTO;

import Machine.Reflector;
import Machine.Rotor;

import java.util.Map;

public class SettingsDTO {
    private final int[] userRotorIndexes;
    private final char[] userRotorsTop;
    private final String userChosenReflection;
    private final Map<Character,Character> userChosenPlugs;

    public SettingsDTO(int[] userRotorIndexes,char[] userRotorsTop,String userChosenReflection,Map<Character,Character> userChosenPlugs){
        this.userRotorIndexes=userRotorIndexes;
        this.userRotorsTop=userRotorsTop;
        this.userChosenReflection=userChosenReflection;
        this.userChosenPlugs=userChosenPlugs;
    }

    public String getFormattedRotorsTop(){
        StringBuilder res=new StringBuilder();
        res.append("<");
        for (int i = 0; i < userRotorsTop.length; i++) {
            res.append(userRotorsTop[i]);
        }
        res.append(">");
        return res.toString();
    }
    public int[] getUserRotorIndexes() {
        return userRotorIndexes;
    }
    public char[] getUserRotorsTop() {return userRotorsTop;}
    public String getUserChosenReflection() {
        return userChosenReflection;
    }
    public Map<Character, Character> getUserChosenPlugs() {
        return userChosenPlugs;
    }
}

