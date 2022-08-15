package Machine;
public class Layer {
    private final char left;
    private final char right;
    public Layer(char left, char right){
        this.left=left;
        this.right=right;
    }
    char getLeft(){
        return left;
    }
    char getRight(){
        return right;
    }


}
