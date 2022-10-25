package ma.octo.assignement;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.User;
import ma.octo.assignement.repository.AccountRepository;
import ma.octo.assignement.repository.UserRepository;
import ma.octo.assignement.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class NiceBankApplication implements CommandLineRunner {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransferRepository transferRepository;

	public static void main(String[] args) {
		SpringApplication.run(NiceBankApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		User user1 = new User();
		user1.setUsername("user1");
		user1.setLastname("last1");
		user1.setFirstname("first1");
		user1.setGender("Male");

		userRepository.save(user1);


		User user2 = new User();
		user2.setUsername("user2");
		user2.setLastname("last2");
		user2.setFirstname("first2");
		user2.setGender("Female");

		userRepository.save(user2);

		Account account1 = new Account();
		account1.setNrAccount("010000A000001000");
		account1.setRib("RIB1");
		account1.setSolde(BigDecimal.valueOf(200000L));
		account1.setUser(user1);

		accountRepository.save(account1);

		Account account2 = new Account();
		account2.setNrAccount("010000B025001000");
		account2.setRib("RIB2");
		account2.setSolde(BigDecimal.valueOf(140000L));
		account2.setUser(user2);

		accountRepository.save(account2);

	}
}
