package Machine;
import Machine.DTO.*;
import UI.Settings;

import java.util.*;

import static UI.UIManager.getFormattedInitialSettings;

public class Enigma {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    private String ABC;
    private String chosenReflectorId;
    private int usedRotors;
    private int[] chosenRotors;
    private PlugBoard plugBoard;
    private Reflector[] reflectors;
    private Rotor[] rotors;
    private String FormattedRotorsIndexes;
    private ArrayList<String> SettingsHistory;

    private int encryptMessages=0;
    public void setInitialFormattedSettings(String Settings) {

    }
    private ArrayList<ArrayList<EncryptionDTO>> historyPerSettings;
    private int fileIndexOfSettings=-1;


    private char[] InitialRotorsTop;

    public void setInitialFormat(){

    }

    public int getCountOfRotors(){
        return rotors.length;
    }
    public int getNumberOfReflectors(){
        return reflectors.length;
    }
    public String getABC(){
        return ABC;
    }
    public void initHardware(HardwareDTO hardwareDTO){
        this.rotors=hardwareDTO.getRotors();
        this.reflectors=hardwareDTO.getReflectors();
        this.usedRotors=hardwareDTO.getRotorsCount();
        this.chosenRotors=new int[rotors.length];
        this.ABC=hardwareDTO.getABC();
    }
    public void initSettings(SettingsDTO settingsDTO){
        chosenRotors=settingsDTO.getUserRotorIndexes();
        chosenReflectorId=settingsDTO.getUserChosenReflection();
        InitialRotorsTop=settingsDTO.getUserRotorsTop();
        plugBoard=new PlugBoard(settingsDTO.getUserChosenPlugs());
        usedRotors=chosenRotors.length;
        adjustRotorTops();
        resetHistory();
        historyPerSettings.add(new ArrayList<>());
    }
    public void resetHistory(){
        fileIndexOfSettings=0;
        SettingsHistory=new ArrayList<>();
        historyPerSettings= new ArrayList<>();
        SettingsHistory.add(getFormattedInitialSettings());
    }
    public void printState(char c){
        System.out.println("Encrypting: "+c);
        Reflector ref=getReflectorById(chosenReflectorId);
        for (int i = 0; i <ABC.length() ; i++) {
            int refId=ref.getIndex(i);
            if(refId<10)
                System.out.print("0"+ref.getIndex(i)+"| ");
            else
                System.out.print(ref.getIndex(i)+"| ");
            for(int j = 0; j<chosenRotors.length;j++) {
                char left = getRotorByIndex(chosenRotors[j]).getLayerById(i).getLeft();
                char right =  getRotorByIndex(chosenRotors[j]).getLayerById(i).getRight();
                if(getRotorByIndex(chosenRotors[j]).getNotch()==i)
                {
                    System.out.print(ANSI_RED+left + "," + right + " "+ANSI_RESET);
                }
                else{
                    System.out.print(left + "," + right + " ");
                }
            }
            System.out.println("");
        }
    }
    public void adjustRotorTops(){
        int rotorsToAdjust=chosenRotors.length;
        for (int i = 0; i <rotorsToAdjust ; i++) {
            while(getRotorByIndex(chosenRotors[i]).getTop() !=InitialRotorsTop[i])
            {
                getRotorByIndex(chosenRotors[i]).shiftUp();
            }
            getRotorByIndex(chosenRotors[i]).setInitialNotch( getRotorByIndex(chosenRotors[i]).getNotch());
        }
    }
    public String encryptMessage(String message){
        fileIndexOfSettings+=1;
        long begin = System.nanoTime();
        StringBuilder encryptedMessage= new StringBuilder();
        for (char ch: message.toCharArray()) {
            ShiftRotors();
            char encryptedChar=encryptLetter(ch);
            encryptedMessage.append(encryptedChar);
        }
        EncryptionDTO encryptionDTO=new EncryptionDTO(message,encryptedMessage.toString(),System.nanoTime()-begin);
        historyPerSettings.get(0).add(encryptionDTO);
        return encryptedMessage.toString();
    }
    public Rotor getRotorByIndex(int id) {
        for (Rotor rotor : rotors) {
            if (rotor.getRotorId() == id) {
                return rotor;
            }
        }
        return null;
    }
    public Reflector getReflectorById(String id){
        for(Reflector reflector:reflectors){
            if(Objects.equals(reflector.getReflectorId(), chosenReflectorId))
                return reflector;
        }
        return null;
    }
    public char encryptLetter(char c){


        c= plugBoard.getValue(c);
        int nextIndex=ABC.indexOf(c);
        for (int i = chosenRotors.length-1; i >=0 ; i--) {
            nextIndex=getRotorByIndex(chosenRotors[i]).getNextExit(nextIndex,true);
        }
        Reflector chosenReflector=getReflectorById(chosenReflectorId);
        nextIndex=chosenReflector.getNextExit(nextIndex);

        for (int chosenRotor : chosenRotors) {
            Rotor current = getRotorByIndex(chosenRotor);
            assert current != null;
            nextIndex = current.getNextExit(nextIndex, false);
        }
        return plugBoard.getValue(ABC.charAt(nextIndex));
    }
    public void ShiftRotors() {
        boolean shiftNext=true;
        for (int i = chosenRotors.length-1; i >=0 ; i--) {
            if(shiftNext){
                shiftNext=getRotorByIndex(chosenRotors[i]).shiftUp();
            }
        }
    }
    public String getFormattedInitiatedRotorsTops(){
        StringBuilder res=new StringBuilder();
        res.append("<");
        for (int i = chosenRotors.length-1; i >=0 ; i--) {
            res.append(InitialRotorsTop[i]);
            res.append(",");
        }
        res= new StringBuilder(res.substring(0, res.length() - 1));
        res.append(">");
        return res.toString();
    }
    public String getFormattedCurrentRotorsTops(){
        StringBuilder res=new StringBuilder();
        res.append("<");
        for (int i = chosenRotors.length-1; i >=0 ; i--) {
            res.append(getRotorByIndex(chosenRotors[i]).getTop()).append(",");;
        }
        res= new StringBuilder(res.substring(0, res.length() - 1));
        res.append(">");
        return res.toString();
    }
    public void initInitialRotorsIndexesFormat(){
        StringBuilder res=new StringBuilder();
        res.append("<");
        for (int i = 0; i < chosenRotors.length; i++) {
            res.append(getRotorByIndex(chosenRotors[i]).getRotorId()).append("(");;
            res.append(getRotorByIndex(chosenRotors[i]).getNotch()).append(")");
            if(i!=chosenRotors.length-1)
                res.append(",");
        }
        res.append(">");
        FormattedRotorsIndexes= res.toString();
    }
    public MachineDTO getMachineDTO(){
        return new MachineDTO(rotors,usedRotors,reflectors.length,chosenReflectorId,chosenRotors,encryptMessages);

    }
    public SettingsDTO getSettingsDTO(){
        int[] notches=new int[chosenRotors.length];
        for (int i = 0; i < chosenRotors.length; i++) {
            notches[i]=getRotorByIndex(chosenRotors[i]).getNotch();
        }
        return new SettingsDTO(chosenRotors,notches,getRotorTops(),chosenReflectorId,plugBoard.getPlugs());
    }

    public char[] getRotorTops(){
        char[] res=new char[chosenRotors.length];
        for (int i = 0; i < chosenRotors.length; i++) {
            res[i]=getRotorByIndex(chosenRotors[i]).getTop();
        }
        return res;
    }
    public String getFormattedRotorsIndexes() {
        return FormattedRotorsIndexes;
    }
}
