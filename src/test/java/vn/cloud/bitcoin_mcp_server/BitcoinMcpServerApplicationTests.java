package vn.cloud.bitcoin_mcp_server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class BitcoinMcpServerApplicationTests {

	@Autowired
	private BitcoinServiceClient bitcoinServiceClient;

	@Test
	void contextLoads() {
		Map<String, Object> historicalBitcoinPrice = bitcoinServiceClient.getHistoricalBitcoinPrice(7);
		System.out.println(historicalBitcoinPrice);
	}

}
