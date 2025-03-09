package vn.cloud.bitcoin_mcp_server;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class BitcoinServiceClient {

    private static final String BASE_URL = "";
    private final RestClient restClient;

    public BitcoinServiceClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl(BASE_URL).build();
    }

    public double getBitcoinPrice() {
        return 0.0;
    }
}
