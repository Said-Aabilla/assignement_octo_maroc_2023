package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {

    @NotBlank(message = "Champ numero du compte emetteur est obligatoire")
    private String nrCompteEmetteur;
    @NotBlank(message = "Champ numero du compte beneficiaire est obligatoire")
    private String nrCompteBeneficiaire;
    @NotBlank(message = "Champ motif est obligatoire")
    private String motif;
    @NotBlank(message = "Champ montant est obligatoire")
    private BigDecimal montant;
    @NotBlank(message = "Champ date est obligatoire")
    private Date date;
}
