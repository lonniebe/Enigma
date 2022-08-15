package UI;
import Machine.DTO.HardwareDTO;
import Machine.DTO.HistoryDTO;
import Machine.DTO.MachineDTO;
import Machine.DTO.SettingsDTO;
import Machine.Enigma;
import Machine.FileLoader;

import java.util.Scanner;

import static UI.MenuManager.GetMenu;
import static UI.MenuManager.getInputForMenu;
import static UI.Settings.*;


public class  UIManager {
    private static final Enigma enigma=new Enigma();

    private static String formattedInitialSettings;
    private static boolean hasSettings=false;
    private static boolean hasMachine=false;

    public static void main(String[] args) throws Exception {
        start();
    }

    public static void mainMenuHandler(int choice) throws Exception {
        switch (choice){
            case(1):loadXML()                   ;break;
            case(2):printMachineHardware()      ;break;
            case(3):getSettingsFromUser()       ;break;
            case(4):setSettingsAutomatically()  ;break;
            case(5):encrypt()                   ;break;
            case(6):adjustMachineRotorTops()    ;break;
            case(7):getMachineHistory()         ;break;
            case(8):System.exit(0)        ;break;
        }
    }
    public static void adjustMachineRotorTops(){
        if (!hasSettings) {
            System.out.println("Please initiate machine's Hardware before doing this.");
        } else {
            enigma.adjustRotorTops();
        }
    }

    public static void getMachineHistory() {
        /*HistoryDTO historyDTO=enigma.getHistoryDTO();
        int sizeOfSettings=historyDTO.getFileIndexOfSettings();
        for (int i = 0; i < sizeOfSettings; i++) {
            System.out.println("For Settings : "+historyDTO.getSettingsHistory().get(i));
            for (int j = 0; j < historyDTO.getEncryptedMessages().get(i); j++) {


            }

        }*/

    }

    public static void setSettingsAutomatically() {
        if (!hasMachine) {
            System.out.println("Please initiate machine's Hardware before doing this.");
        } else {
            enigma.initSettings(setRandomSettings(enigma.getABC(), enigma.getCountOfRotors()));
            hasSettings = true;
        }
    }
    public static void loadXML(){
        Scanner sc=new Scanner(System.in);
        while(true){
            try{
            System.out.println("Please enter the full path of the XML or 'n' to go back.");
            String path=sc.nextLine();
            if(path.equals("n"))
                return;
            FileLoader fileLoader=new FileLoader(path);
            HardwareDTO hardwareDTO=fileLoader.getHardware();
            enigma.initHardware(hardwareDTO);
            hasMachine=true;
            System.out.println("XML File loaded successfully.");
            return;
            }catch(Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }
    public static void start() throws Exception {
        while(true){
            String[] mainMenu=GetMenu();
            assert mainMenu != null;
            mainMenuHandler(getInputForMenu(mainMenu));
        }

    }
    public static void encrypt(){
        if(!hasMachine || !hasSettings){
            System.out.println("Please initiate machine's Hardware before doing this.");
        }
        else {
            Scanner sc = new Scanner(System.in);
            while(true) {
                System.out.println("Please enter the message you'd like to to encrypt:");
                String input = sc.nextLine().toUpperCase();
                if(checkEncryption(input)){
                    String output = enigma.encryptMessage(input);
                    System.out.println("Your Encrypted message is: " + output);
                    return;
                }
                else{
                    System.out.println("some letters in you're message are not in the machine's ABC.");
                }
            }
        }
    }
    public static boolean checkEncryption(String input){
        for (int i = 0; i < input.length(); i++) {
            if(enigma.getABC().indexOf(input.charAt(i))==-1){
                return false;
            }
        }
        return true;
    }
    private static void getSettingsFromUser() throws Exception {
        if(!hasMachine)
            System.out.println("Please initiate machine's Hardware before doing this.");
        else {
            SettingsDTO settings = getSettings(enigma.getABC(), enigma.getCountOfRotors(), enigma.getNumberOfReflectors());
            if(settings==null)
                return;
            settings.setRotorsNotch(getUsedRotorsNotches(settings.getUserRotorIndexes()));
            enigma.initSettings(settings);
            formattedInitialSettings=enigma.getSettingsDTO().getFormattedSettings();
            hasSettings = true;
        }
    }

    public static int[] getUsedRotorsNotches(int[] chosenRotors){
        int[] notches=new int[chosenRotors.length];
        for (int i = 0; i < chosenRotors.length; i++) {
            notches[i]=enigma.getRotorByIndex(chosenRotors[i]).getNotch();
        }
        return notches;
    }
    public static void printMachineHardware(){
        if(hasMachine) {
            MachineDTO machineDTO = enigma.getMachineDTO();
            System.out.println("------ Machine Details ------");
            System.out.println("- Count of rotors in use: ( " + machineDTO.getUsedRotorsCount() + " / " + machineDTO.getAvailableRotorsCount() + " ).");
            System.out.println("- Count of Reflectors: " + machineDTO.getReflectionCount());
            System.out.println("- Count of encrypted messages: " + machineDTO.getEncryptedMessagesCount());
            if(hasSettings) {
                printMachineSettings();
            }
            else {
                System.out.println("Machine has no settings yet.");
            }
        } else{
            System.out.println("You have to set the machine to see the details.");
        }
    }
    public static void printMachineSettings(){
        if (hasSettings) {
            SettingsDTO settingsDTO = enigma.getSettingsDTO();
            String currentSettings=settingsDTO.getFormattedSettings();
            System.out.println("Initial Settings: "+formattedInitialSettings);
            System.out.println("Current Settings: "+currentSettings);
        }
    }
    public static String getFormattedPlugs(){
        String input=getPlugs();
        if(input.equals(""))
            return input;
        StringBuilder res=new StringBuilder();
        for (int i = 0; i < input.length(); i+=2) {
            res.append(input.charAt(i));
            res.append("|");
            res.append(input.charAt(i + 1));
            res.append(",");
        }
        return res.substring(0,res.length()-1);
    }

    public static void setInitialFormattedSettings(SettingsDTO settingsDTO){
        String initialRotorsIndexes= enigma.getFormattedRotorsIndexes();
        String initialRotorsTops= enigma.getFormattedInitiatedRotorsTops();
        String reflection = "<" + settingsDTO.getUserChosenReflection() + ">";
        for (int i = 0; i < settingsDTO.getUserRotorIndexes().length; i++) {
            int index=settingsDTO.getUserRotorIndexes()[i];
            int initialNotch=enigma.getRotorByIndex(index).getInitialNotch();
            int currentNotch=enigma.getRotorByIndex(index).getNotch();
        }
        String Plugs= getFormattedPlugs();
        formattedInitialSettings=initialRotorsIndexes+initialRotorsTops+reflection+Plugs;
    }

    public static String getFormattedInitialSettings() {
        return formattedInitialSettings;
    }
}
