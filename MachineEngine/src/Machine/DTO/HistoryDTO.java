package Machine.DTO;

import java.util.ArrayList;

public class HistoryDTO {
    private ArrayList<Long> messagesEncryptionTime;
    private ArrayList<String> messagesBeforeEncrypt;
    private ArrayList<String> messagesAfterEncrypt;

    public HistoryDTO(ArrayList<Long> time,ArrayList<String> before,ArrayList<String> after){
        messagesAfterEncrypt=after;
        messagesBeforeEncrypt=before;
        messagesEncryptionTime=time;
    }


    public ArrayList<String> getMessagesAfterEncrypt() {
        return messagesAfterEncrypt;
    }

    public ArrayList<String> getMessagesBeforeEncrypt() {
        return messagesBeforeEncrypt;
    }

    public ArrayList<Long> getMessagesEncryptionTime() {
        return messagesEncryptionTime;
    }
}
