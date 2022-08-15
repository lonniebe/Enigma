package UI;
import Machine.DTO.HardwareDTO;
import Machine.DTO.SettingsDTO;
import Machine.Rotor;

import java.io.IOException;
import java.util.*;

import static UI.MenuManager.*;

public class Settings {

    private static String ABC;
    private static int numOfRotors;
    private static int reflectorCount;

    private static int countOfChosenRotors;
    private static String plugs="";

    public static SettingsDTO getSettings(String ABCs, int countOfRotors, int countOfReflectors) throws Exception {
        ABC=ABCs.toUpperCase();
        numOfRotors=countOfRotors;
        reflectorCount=countOfReflectors;
        try {
            Scanner sc = new Scanner(System.in);
            int[] chosenRotors = getRotorIndexesFromUser(sc);
            if(chosenRotors==null)
                return null;
            char[] rotorTops = getRotorTopsFromUser(sc);
            if(rotorTops==null)
                return null;
            String chosenReflector = getChosenReflector(sc);
            if(chosenReflector==null)
                return null;
            Map<Character, Character> plugs = getPlugsFromUsers(sc);
            if(plugs==null)
                return null;

            return new SettingsDTO(chosenRotors,new int[chosenRotors.length], rotorTops, chosenReflector, plugs);
        }catch(Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }
    private static int romicToInt(String s) {
        switch(s){
            case("I"):return 1;
            case("II"):return 2;
            case("III"):return 3;
            case("IV"):return 4;
            case("V"):return 5;
            default:return -1;
        }
    }
    public static SettingsDTO setRandomSettings(String ABC,int numOfRotors){
        String chosenReflector=randomReflector(reflectorCount);
        int countOfChosenRotors=randInt(1,numOfRotors);
        int[] chosenRotors=randomNumbers(countOfChosenRotors,numOfRotors);
        char[] rotorTops=randomTops(countOfChosenRotors,ABC);
        Map<Character,Character> plugs=new HashMap<>();
        return new SettingsDTO(chosenRotors,new int[chosenRotors.length],rotorTops,chosenReflector,plugs);
    }
    public static String randomReflector(int reflectorCount){
        int chosenReflector=randInt(1,reflectorCount);
        return intToRomic(chosenReflector);
    }

    public static String intToRomic(int chosenReflector){
        switch(chosenReflector){
            case(1):return "I";
            case(2):return "II";
            case(3):return "III";
            case(4):return "IV";
            case(5):return "V";
            default:return "";
        }
    }

    public static char[] randomTops(int countOfChosenRotors,String ABC){
        char[] res=new char[countOfChosenRotors];
        ArrayList<Character> list=new ArrayList<>(countOfChosenRotors);

        for (int i = 0; i < countOfChosenRotors; i++) {
            list.add(ABC.charAt(i));
        }

        Collections.shuffle(list);

        for (int n = 0; n < countOfChosenRotors; n++) {
            res[n]=list.get(n);
        }
        return res;
    }
    public static int[] randomNumbers(int countOfChosenRotors, int numOfRotors) {
        int[] res=new int[countOfChosenRotors];
        ArrayList<Integer> list=new ArrayList<>(numOfRotors);
        for (int i = 1; i <= numOfRotors; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int n = 0; n < countOfChosenRotors; n++) {
            res[n]=list.get(n);
        }
        return res;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
    public static boolean isRomic(String input){
        return Objects.equals(input, "I")   ||
                Objects.equals(input, "II") ||
                Objects.equals(input, "III")||
                Objects.equals(input, "IV") ||
                Objects.equals(input, "V");
    }


    public static Map<Character,Character> getPlugsFromUsers(Scanner sc) throws Exception {
        System.out.println("Enter which Plugs you'd like to connect - 'ABCD' -> <A|B,C|D>");
        System.out.println("Enter 'n' to go back or Empty line to skip");
        while(true) {
            try {
                Map<Character,Character> res=new HashMap<>();
                String input = sc.nextLine().toUpperCase();
                if(input.equals("")){
                    return res;
                }
                if(input.equals("N"))
                    return null;
                if (input.length() % 2 != 0) {
                    throw new Exception("Every plugs must connect 2 keys. make sure plugs length are even.");
                }
                for (int i = 0; i < input.length(); i += 2) {
                    if(!checkPlugs(res,input.charAt(i), input.charAt(i + 1)))
                        throw new Exception("one of the plugs is not in the ABC, or already entered before.");
                    else {
                        res.put(input.charAt(i), input.charAt(i + 1));
                        res.put(input.charAt(i + 1), input.charAt(i));
                    }
                }
                plugs=input;
                return res;
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        }
    }


    public static boolean checkPlugs(Map<Character,Character> res,char key, char value){
        if (ABC.indexOf(key) == -1 || ABC.indexOf(value) == -1 ||
                res.containsKey(key) || res.containsKey(value))
            return false;
        return true;
    }


    public static String getChosenReflector(Scanner sc){
        while(true){
            System.out.println("Which reflector you'd like to insert? (1-"+reflectorCount+") In Romic. or 'n' to go back.;");
            String input=sc.nextLine().toUpperCase();
            if(input.equals("N"))
                return null;
            try{
                if(isRomic(input) && romicToInt(input)>=1 && romicToInt(input)<=reflectorCount)
                    return input;
                else{
                    throw new Exception();
                }
            }catch(Exception e){
                System.out.println("Invalid input for rotor tops.");
            }
        }
    }
    public static char[] getRotorTopsFromUser(Scanner sc){
        while(true)
        {
            System.out.println("Enter the tops for the rotors. (xyz) - <Z,X,Y> or 'n' to go back.");
            String input=sc.nextLine().toUpperCase();
            if(input.equals("N"))
                return null;
            input.replaceAll(" ", "");
            char[] tops=new char[input.length()];
            try{
                if(input.length()!=countOfChosenRotors)
                    throw new Exception();
                for(int i = 0;i < input.length();i++)
                {
                    tops[i] = input.charAt(i);
                    if(ABC.indexOf(tops[i])==-1)
                        throw new Exception();
                }
                return tops;
            }catch(Exception e){
                System.out.println("Invalid input for rotor tops.");
            }
        }
    }
    public static int[] getRotorIndexesFromUser(Scanner sc){
        while(true) {
            System.out.println("Enter the indexes of the rotors you would like to use. (x,y,..,z) or 'n' to go back.");
            String input = sc.nextLine().toUpperCase();
            if(input.equals("N"))
                return null;
            input.replaceAll(" ", "");
            String[] numbers = input.split(",");
            int[] indexes = new int[numbers.length];
            try {
                if(numbers.length>99 || input.length()%2==0)
                    throw new Exception();
                for (int i = 0; i < numbers.length; i++) {
                    indexes[i] = Integer.parseInt(numbers[i]);
                    if(indexes[i]<1 || indexes[i]>numOfRotors)
                        throw new Exception();
                }
                countOfChosenRotors= numbers.length;
                return indexes;
            } catch (Exception e) {
                System.out.println("Invalid input for rotor indexes.");
            }
        }
    }


    public static String getPlugs() {
        return plugs;
    }
}
