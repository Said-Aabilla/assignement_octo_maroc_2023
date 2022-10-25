package ma.octo.assignement.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {

    @NotBlank(message = "Champ numero du compte emetteur est obligatoire")
    private String nrAccountTransmitter;
    @NotBlank(message = "Champ numero du compte beneficiaire est obligatoire")
    private String nrAccountBeneficiary;
    @NotBlank(message = "Champ motif est obligatoire")
    private String motif;
    @NotBlank(message = "Champ montant est obligatoire")
    private BigDecimal amount;
    @NotBlank(message = "Champ date est obligatoire")
    private Date dateExecution;
}
