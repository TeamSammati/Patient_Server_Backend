package site.sammati_patient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsentDataDTO {
    Integer consentRequestId;
    Integer patientId;
    Integer duration;
    List<ConsentDataMappingDTO> consentDataMappingDTOList;
}
