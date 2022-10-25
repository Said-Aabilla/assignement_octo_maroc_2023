package ma.octo.assignement.dto.response;

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
    private BigDecimal amount;
    private Date dateExecution;
    private String fullNameTransmitter;
    private String nrAccountBeneficiary;
    private String motif;
}
