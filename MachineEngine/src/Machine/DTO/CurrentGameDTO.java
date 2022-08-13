package Machine.DTO;

public class CurrentGameDTO {
    HistoryDTO history;
    MachineDTO machine;
    SettingsDTO settings;

    CurrentGameDTO( HistoryDTO history,MachineDTO machine,SettingsDTO settings){
        this.history=history;
        this.machine=machine;
        this.settings=settings;
    }
}
