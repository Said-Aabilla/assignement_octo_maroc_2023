package ma.octo.assignement.dto.response;

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
public class TransferResponseDto {
  private String nrAccountTransmitter;
  private String nrAccountBeneficiary;
  private String motif;
  private BigDecimal amount;
  private Date dateExecution;

}
