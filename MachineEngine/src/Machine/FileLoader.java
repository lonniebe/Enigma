package Machine;
import Machine.DTO.HardwareDTO;
import Machine.generated.*;
import javafx.util.Pair;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static UI.Settings.isRomic;

public class FileLoader {
    CTEEnigma cteEnigma;
    private int availableRotors;
    private int usedRotors;
    private int sizeOfABC;
    private String ABC;


    public FileLoader(String path) throws Exception {
        if(path.length()<4 || !path.substring(path.length() - 4).equals(".xml"))
            throw new Exception("this is not an XML File.");
        File file = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(CTEEnigma.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        cteEnigma = (CTEEnigma) jaxbUnmarshaller.unmarshal(file);
        availableRotors = cteEnigma.getCTEMachine().getCTERotors().getCTERotor().size();
        usedRotors=cteEnigma.getCTEMachine().getRotorsCount();
        sizeOfABC = cteEnigma.getCTEMachine().getABC().toUpperCase().replaceAll("[\\n\\r\\t]+", "").length();
        ABC= cteEnigma.getCTEMachine().getABC().replaceAll("[\\n\\r\\t]+", "");
        checkForFileValidation();
    }

    public int getUsedRotorsCount() {
        return cteEnigma.getCTEMachine().getRotorsCount();
    }
    public boolean isInABC(char c){
        return cteEnigma.getCTEMachine().getABC().indexOf(c) != -1;
    }
    private void checkForFileValidation() throws Exception {
        CTEMachine machine=cteEnigma.getCTEMachine();
        int availableRotors=machine.getCTERotors().getCTERotor().size();
        if(ABC.length()%2 == 1)
            throw new Exception("ABC length must be even.");
        if(machine.getRotorsCount()>availableRotors)
            throw new Exception("Rotor count cannot be greater than the amount of the available rotors in the machine.");
    }
    public HardwareDTO getHardware() throws Exception {
        Rotor[] rotors=getRotorsFromEnigma();
        Reflector[] reflectors=getReflectorsFromEnigma();
        int usedRotorsCount=getUsedRotorsCount();
        return new HardwareDTO(rotors,reflectors,usedRotorsCount,ABC);
    }
    public Rotor[] getRotorsFromEnigma() throws Exception {
        List<CTERotor> rotorList = cteEnigma.getCTEMachine().getCTERotors().getCTERotor();
        ArrayList<Pair<Character, Character>> rotor = new ArrayList<>();
        Rotor[] rotors = new Rotor[availableRotors];
        for (int i = 0; i < availableRotors; i++) {
            int[] rotorsMapping=new int[sizeOfABC];
            Layer[] layers = new Layer[sizeOfABC];
            for (int j = 0; j < sizeOfABC; j++) {
                char lValue = rotorList.get(i).getCTEPositioning().get(j).getLeft().charAt(0);
                char rValue = rotorList.get(i).getCTEPositioning().get(j).getRight().charAt(0);
                if(!isInABC(lValue) || !isInABC(rValue)){
                    throw new Exception("Rotor: invalid value from file."+rValue+" or"+lValue+" Not in our ABC.");
                }
                rotorsMapping[ABC.indexOf(lValue)]+=1;
                rotorsMapping[ABC.indexOf(rValue)]+=1;
                layers[j] = new Layer(lValue, rValue);
            }
            int notch = rotorList.get(i).getNotch()-1;
            int id=rotorList.get(i).getId();
            checkRotorValidation(notch,id,rotorsMapping);
            rotors[id-1] = new Rotor(layers, notch,id);
        }
        return rotors;
    }
    public void checkRotorValidation(int notch,int id,int[] rotorsMapping) throws Exception {
        if(!checkRotorMapping(rotorsMapping))
            throw new Exception("the mappings inside the rotor are invalid.");
        if(notch<0 || notch>sizeOfABC)
            throw new Exception("notch index cannot be greater than the size of ABC / lower than 0");
        if(id<0 || id>availableRotors)
            throw new Exception("Rotor id cannot be greater than the amount of available rotors / lower than 0");
    }
    public boolean checkRotorMapping(int[] rotorsMapping){
        for(int num:rotorsMapping){
            if(num!=2){
                return false;
            }
        }
        return true;
    }
    public Reflector[] getReflectorsFromEnigma() throws Exception {
        int reflectorCount = cteEnigma.getCTEMachine().getCTEReflectors().getCTEReflector().size();
        if(reflectorCount<1 || reflectorCount>5)
            throw new Exception("Count of reflectors in file must be between 1 and 5.");

        Reflector[] reflectors = new Reflector[reflectorCount];
        CTEReflectors refs=cteEnigma.getCTEMachine().getCTEReflectors();
        for (int i = 0; i < reflectorCount; i++) {
            int[] reflector = new int[sizeOfABC];
            String id=refs.getCTEReflector().get(i).getId();
            for (int j = 0; j < sizeOfABC / 2; j++) {
                int input = refs.getCTEReflector().get(i).getCTEReflect().get(j).getInput() - 1;
                int output = refs.getCTEReflector().get(i).getCTEReflect().get(j).getOutput() - 1;
                if(!reflectionIOChecker(input,output) || input==output || !isRomic(id))
                    throw new Exception("Reflector : invalid input from file.");
                reflector[input] = j;
                reflector[output] = j;
            }
            reflectors[i] = new Reflector(reflector,id);
        }
        return reflectors;
    }
    public boolean reflectionIOChecker(int input,int output){
        return input >= 0 && output >= 0 && input <= sizeOfABC && output <= sizeOfABC;
    }
}