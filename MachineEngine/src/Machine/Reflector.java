package Machine;

import java.sql.Ref;
import java.util.Arrays;

public class Reflector {
    private final String reflectorId;
    private final int[] reflector;
    public String getReflectorId(){
        return reflectorId;
    }
    public int getIndex(int id){
        return reflector[id];
    }

    public Reflector(int[] reflector,String reflectorId){
        this.reflector=reflector;
        this.reflectorId=reflectorId;
    }
    public int getNextExit(int entrance){
        for (int i = 0; i < reflector.length; i++) {
            if(reflector[entrance]==reflector[i] && i!=entrance)
                return i;
        }
        return 0;
    }
}
