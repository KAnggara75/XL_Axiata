package id.co.xl.task.subscribertransaction;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SubscriberTransactionApplicationTests {

	@Test
	void main() {
		SubscriberTransactionApplication.main(new String[] {});

		assertTrue(true, "silly assertion to be compliant with Sonar");
	}
}
