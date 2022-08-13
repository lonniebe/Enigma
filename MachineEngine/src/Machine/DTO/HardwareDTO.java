package Machine.DTO;

import Machine.Reflector;
import Machine.Rotor;

public class HardwareDTO {
    private final Rotor[] rotors;
    private final Reflector[] reflectors;
    private final int rotorsCount;

    private final String ABC;

    public HardwareDTO(Rotor[] rotors,Reflector[] reflectors,int rotorsCount,String ABC){
        this.rotors=rotors;
        this.reflectors=reflectors;
        this.rotorsCount=rotorsCount;
        this.ABC=ABC;
    }


    public int getRotorsCount() {
        return rotorsCount;
    }

    public Reflector[] getReflectors() {
        return reflectors;
    }

    public Rotor[] getRotors() {
        return rotors;
    }

    public String getABC() {
        return ABC;
    }
}
