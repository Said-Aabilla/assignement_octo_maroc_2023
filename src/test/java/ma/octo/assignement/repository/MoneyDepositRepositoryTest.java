package ma.octo.assignement.repository;

import ma.octo.assignement.domain.MoneyDeposit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
public class MoneyDepositRepositoryTest {

  @Autowired
  private MoneyDepositRepository moneyDepositRepository;

  @Test
  void findAll() {
    MoneyDeposit deposit1 = MoneyDeposit.builder()
            .amount(new BigDecimal(120)).motif("testMotif1").build();
    MoneyDeposit deposit2 = MoneyDeposit.builder()
            .amount(new BigDecimal(100)).motif("testMotif2").build();



    moneyDepositRepository.save(deposit1);
    moneyDepositRepository.save(deposit2);

    List<MoneyDeposit> result = moneyDepositRepository.findAll();

    assertEquals(2,result.size());
    assertEquals("testMotif1",result.get(0).getMotif());
    assertEquals(new BigDecimal(120),result.get(0).getAmount());

    assertEquals("testMotif2",result.get(1).getMotif());
    assertEquals(new BigDecimal(100),result.get(1).getAmount());

  }
}