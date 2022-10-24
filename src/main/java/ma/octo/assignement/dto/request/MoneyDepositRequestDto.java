package ma.octo.assignement.dto.request;

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
public class MoneyDepositRequestDto {

    @NotBlank(message = "Champ montant est obligatoire")
    private BigDecimal Montant;
    @NotBlank(message = "Champ RIB du beneficiaire est obligatoire")
    private String rib;
    @NotBlank(message = "Champ motif est obligatoire")
    private String motif;
    @NotBlank(message = "Champ nom complet est obligatoire")
    private String fullNameEmetteur;
    @NotBlank(message = "Champ date est obligatoire")
    private Date dateExecution;
}
