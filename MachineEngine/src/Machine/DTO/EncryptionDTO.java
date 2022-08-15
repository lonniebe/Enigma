package Machine.DTO;

public class EncryptionDTO {
    private final String messageBeforeEncryption;
    private final String messageAfterEncryption;
    private final Long encryptionTime;

    public EncryptionDTO(String messageBeforeEncryption, String messageAfterEncryption, Long encryptionTime) {
        this.messageBeforeEncryption = messageBeforeEncryption;
        this.messageAfterEncryption = messageAfterEncryption;
        this.encryptionTime = encryptionTime;
    }

    @Override
    public String toString() {
        return "<" + messageBeforeEncryption + ">" +
                " --> " +
                "<" + messageAfterEncryption + ">" +
                "(" + encryptionTime + ")" + " Nanoseconds.";
    }

    public String getMessageBeforeEncryption() {
        return messageBeforeEncryption;
    }

    public String getMessageAfterEncryption() {
        return messageAfterEncryption;
    }

    public Long getEncryptionTime() {
        return encryptionTime;
    }
}
