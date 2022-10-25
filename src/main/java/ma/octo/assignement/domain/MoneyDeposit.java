package ma.octo.assignement.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "DEPOSIT")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MoneyDeposit {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(precision = 16, scale = 2, nullable = false)
  private BigDecimal amount;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateExecution;

  @Column
  private String fullNameTransmitter;

  @ManyToOne
  private Account accountBeneficiary;

  @Column(length = 200)
  private String motif;

}
