package ma.octo.assignement.domain;

import lombok.Data;
import ma.octo.assignement.domain.util.EventType;

import javax.persistence.*;

@Entity
@Table(name = "AUDIT_DEPOSIT")
@Data
public class AuditDeposit {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 100)
  private String message;

  @Enumerated(EnumType.STRING)
  private EventType eventType;
}
