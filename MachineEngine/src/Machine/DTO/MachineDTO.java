package Machine.DTO;

import Machine.Rotor;

import java.util.Map;

public class MachineDTO {

    private final int usedRotorsCount;
    private final int reflectionCount;
    private final String chosenReflector;
    private final int[] indexesOfChosenRotors;
    private final int encryptedMessagesCount;

    private final Rotor[] rotors;

    public MachineDTO(Rotor[] rotors,int usedRotorsCount, int reflectionCount, String chosenReflector, int[] indexesOfChosenRotors,
                      int encryptedMessagesCount) {

        this.rotors=rotors;
        this.usedRotorsCount = usedRotorsCount;
        this.reflectionCount = reflectionCount;
        this.chosenReflector = chosenReflector;
        this.indexesOfChosenRotors = indexesOfChosenRotors;
        this.encryptedMessagesCount = encryptedMessagesCount;
    }

    public int[] getNotchPositions(){
        int[] notches=new int[rotors.length];
        for (int i = 0; i < rotors.length; i++) {
            notches[i]=rotors[i].getNotch();
        }
        return notches;
    }
    public int getUsedRotorsCount() {
        return usedRotorsCount;
    }

    public int getAvailableRotorsCount() {
        return rotors.length;
    }

    public int getReflectionCount() {
        return reflectionCount;
    }

    public String getChosenReflector() {
        return chosenReflector;
    }

    public char[] getRotorsTop() {
        char[] tops=new char[rotors.length];
        for (int i = 0; i < rotors.length; i++) {
            tops[i]=rotors[i].getTop();
        }
        return tops;
    }

    public int[] getIndexesOfChosenRotors() {
        return indexesOfChosenRotors;
    }


    public int getEncryptedMessagesCount() {
        return encryptedMessagesCount;
    }
}
