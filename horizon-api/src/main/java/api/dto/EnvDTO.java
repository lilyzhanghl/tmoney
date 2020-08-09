package api.dto;

import api.item.AppType;
import lombok.Data;

import java.util.HashMap;
@Data
public class EnvDTO {
    public String host;
    public CorpDTO corp;
    public HashMap<AppType,AppDTO> app;
    public StaffDTO staff;
}
