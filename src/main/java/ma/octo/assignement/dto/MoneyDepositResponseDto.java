package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDepositResponseDto {
    private BigDecimal Montant;
    private Date dateExecution;
    private String fullNameEmetteur;
    private String nrCompteBeneficiaire;
    private String motif;
}
