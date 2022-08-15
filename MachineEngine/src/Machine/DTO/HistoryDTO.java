package Machine.DTO;

import java.util.ArrayList;

public class HistoryDTO {

    private ArrayList<String> SettingsHistory;
    private ArrayList<ArrayList<Long>> messagesEncryptionTime;
    private ArrayList<ArrayList<String>> messagesBeforeEncrypt;
    private ArrayList<ArrayList<String>> messagesAfterEncrypt;
    private ArrayList<Integer> encryptedMessages;
    private int fileIndexOfSettings=0;

    public HistoryDTO(ArrayList<String> settingsHistory,
                      ArrayList<ArrayList<Long>> messagesEncryptionTime,
                      ArrayList<ArrayList<String>> messagesBeforeEncrypt,
                      ArrayList<ArrayList<String>> messagesAfterEncrypt,
                      ArrayList<Integer> encryptedMessages) {
        SettingsHistory = settingsHistory;
        this.messagesEncryptionTime = messagesEncryptionTime;
        this.messagesBeforeEncrypt = messagesBeforeEncrypt;
        this.messagesAfterEncrypt = messagesAfterEncrypt;
        this.encryptedMessages = encryptedMessages;
    }


    public ArrayList<ArrayList<String>> getMessagesAfterEncrypt() {
        return messagesAfterEncrypt;
    }

    public ArrayList<ArrayList<String>> getMessagesBeforeEncrypt() {
        return messagesBeforeEncrypt;
    }

    public ArrayList<ArrayList<Long>> getMessagesEncryptionTime() {
        return messagesEncryptionTime;
    }

    public ArrayList<Integer> getEncryptedMessages() {return encryptedMessages;}

    public int getFileIndexOfSettings() {return fileIndexOfSettings;}

    public ArrayList<String> getSettingsHistory() {return SettingsHistory;}
}
