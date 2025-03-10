package vn.cloud.bitcoin_mcp_server;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfiguration {

    @Bean
    public ToolCallbackProvider bitcoinTools(BitcoinServiceClient bitcoinServiceClient) {
        return MethodToolCallbackProvider.builder().toolObjects(bitcoinServiceClient).build();
    }
}
