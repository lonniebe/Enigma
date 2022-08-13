package Machine;
import java.util.Arrays;

public class Rotor {

    private int lengthOfABC=0;
    private final int rotorId;
    private int notch=0;
    private final Layer[] layers;
    private int initialNotch=0;

    private char Top;
    public Layer getLayerById(int id){
        return layers[id];
    }
    public int getNotch(){
        return notch;
    }
    public char getTop(){
        return layers[0].getRight();
    }
    public int getRotorId(){
        return rotorId;
    }



    public Rotor(Layer[] layers,int notch,int rotorId)
    {
        this.notch=notch;
        this.layers=layers;
        this.rotorId=rotorId;
        this.lengthOfABC=layers.length;
        this.Top=getTop();
    }
    public int getNextExit(int index,boolean forward)
    {
        int nextIndex=0;
        if(forward) {
            char Entrance=layers[index].getRight();
            for (int i = 0; i < lengthOfABC; i++) {
                if (Entrance == layers[i].getLeft()) {
                    nextIndex = i;
                }
            }
        }
        else {
            char Entrance=layers[index].getLeft();
            for (int i = 0; i < lengthOfABC; i++) {
                if (Entrance == layers[i].getRight()) {
                    nextIndex = i;
                }
            }
        }
        return nextIndex;
    }
    public boolean shiftUp()
    {
        int size=lengthOfABC;
        Layer temp=layers[0];

        for (int i = 0; i <size-1; i++) {
            layers[i]=layers[i+1];
        }
        layers[size-1]=temp;

        notch-=1;
        if(notch==-1)
            notch= layers.length-1;
        this.Top=getTop();
        return notch == 0;
    }

    public boolean shiftDown()
    {
        int size=lengthOfABC;
        Layer temp=layers[size-1];

        for (int i = size-1; i >0; i--) {
            layers[i]=layers[i-1];
        }
        layers[0]=temp;

        notch+=1;
        if(notch==layers.length)
        {
            notch=0;
            return true;
        }
        this.Top=getTop();
        return false;
    }

    public void setInitialNotch(int initialNotch) {
        this.initialNotch = initialNotch;
    }

    public int getInitialNotch() {
        return initialNotch;
    }
}
