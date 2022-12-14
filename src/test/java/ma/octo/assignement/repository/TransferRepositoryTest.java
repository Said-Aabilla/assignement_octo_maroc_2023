package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Transfer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
public class TransferRepositoryTest {

  @Autowired
  private TransferRepository transferRepository;

  @Test
  void findAll() {
    Transfer transfer1 = Transfer.builder()
            .amount(new BigDecimal(120)).motif("testMotif1").build();

    Transfer transfer2 = Transfer.builder()
            .amount(new BigDecimal(100)).motif("testMotif2").build();


    transferRepository.save(transfer1);
    transferRepository.save(transfer2);

    List<Transfer> result = transferRepository.findAll();

    assertEquals(2,result.size());
    assertEquals("testMotif1",result.get(0).getMotif());
    assertEquals(new BigDecimal(120),result.get(0).getAmount());
    assertEquals("testMotif2",result.get(1).getMotif());
    assertEquals(new BigDecimal(100),result.get(1).getAmount());

  }
}