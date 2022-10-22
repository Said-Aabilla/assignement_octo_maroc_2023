package ma.octo.assignement.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "TRAN")
public class Transfer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(precision = 16, scale = 2, nullable = false)
  private BigDecimal montantTransfer;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date dateExecution;

  @ManyToOne
  private Compte compteEmetteur;

  @ManyToOne
  private Compte compteBeneficiaire;

  @Column(length = 200)
  private String motifTransfer;

  public BigDecimal getMontantTransfer() {
    return montantTransfer;
  }

  public void setMontantTransfer(BigDecimal montantTransfer) {
    this.montantTransfer = montantTransfer;
  }

  public Date getDateExecution() {
    return dateExecution;
  }

  public void setDateExecution(Date dateExecution) {
    this.dateExecution = dateExecution;
  }

  public Compte getCompteEmetteur() {
    return compteEmetteur;
  }

  public void setCompteEmetteur(Compte compteEmetteur) {
    this.compteEmetteur = compteEmetteur;
  }

  public Compte getCompteBeneficiaire() {
    return compteBeneficiaire;
  }

  public void setCompteBeneficiaire(Compte compteBeneficiaire) {
    this.compteBeneficiaire = compteBeneficiaire;
  }

  public String getMotifTransfer() {
    return motifTransfer;
  }

  public void setMotifTransfer(String motifTransfer) {
    this.motifTransfer = motifTransfer;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
