package Machine.DTO;

import Machine.Layer;
import Machine.Reflector;
import Machine.Rotor;

import java.util.HashMap;
import java.util.Map;

public class SettingsDTO {
    private final int[] userRotorIndexes;
    private final char[] userRotorsTop;
    private int[] rotorsNotch;
    private final String userChosenReflection;
    private final Map<Character,Character> userChosenPlugs;
    public SettingsDTO(int[] userRotorIndexes,
                       int[] rotorsNotch,
                       char[] userRotorsTop,
                       String userChosenReflection,
                       Map<Character,Character> userChosenPlugs){
        this.userRotorIndexes=userRotorIndexes;
        this.userRotorsTop=userRotorsTop;
        this.userChosenReflection=userChosenReflection;
        this.userChosenPlugs=userChosenPlugs;
        this.rotorsNotch=rotorsNotch;
    }

    public void setRotorsNotch(int[] rotorsNotch) {
        this.rotorsNotch = rotorsNotch;
    }

    public String getFormattedSettings(){
        String plugs=getFormattedPlugs();
        String indexes=getFormattedUsedRotorsIndexes();
        String tops=getFormattedRotorsTop();
        String ref=getFormattedReflection();
        return indexes+tops+ref+plugs;
    }
    public String getFormattedPlugs(){
        if(userChosenPlugs.isEmpty())
            return "";
        Map<Character,Character> temp= new HashMap<>(userChosenPlugs);
        StringBuilder plugs=new StringBuilder("<");
        for ( Character key : userChosenPlugs.keySet() ) {
            if(temp.containsKey(temp.get(key)) && temp.containsKey(key))
            {
                temp.remove(key);
            }
        }
        for ( Character key : temp.keySet() ) {
            plugs.append(key).append("|").append(temp.get(key)).append(",");
        }
        plugs= new StringBuilder(plugs.substring(0, plugs.length() - 1));
        return plugs.append(">").toString();
    }

    public String getFormattedUsedRotorsIndexes(){
        StringBuilder currentRotorsIndexes=new StringBuilder("<");
        for (int i = 0; i < userRotorIndexes.length; i++) {
            int index=userRotorIndexes[i];
            int currentNotch=rotorsNotch[i];
            currentRotorsIndexes.append(index).append("(").append(currentNotch).append(")");
            if(i!=userRotorIndexes.length-1){
                currentRotorsIndexes.append(",");
            }
        }
        currentRotorsIndexes.append(">");
        return currentRotorsIndexes.toString();
    }

    public String getFormattedReflection(){
        return "<" + userChosenReflection+ ">";
    }
    public String getFormattedRotorsTop(){
        StringBuilder res=new StringBuilder();
        res.append("<");
        for (char c : userRotorsTop) {
            res.append(c);
        }
        res.append(">");
        return res.toString();
    }

    public int[] getUserRotorIndexes() {return userRotorIndexes;}
    public char[] getUserRotorsTop() {return userRotorsTop;}
    public String getUserChosenReflection() {return userChosenReflection;}
    public Map<Character, Character> getUserChosenPlugs() {return userChosenPlugs;}
    public int[] getRotorsNotch() {return rotorsNotch;}
}

