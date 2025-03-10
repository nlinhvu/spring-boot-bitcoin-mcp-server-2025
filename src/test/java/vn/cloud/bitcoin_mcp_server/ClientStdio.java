package vn.cloud.bitcoin_mcp_server;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.spec.McpSchema;

import java.util.Map;

public class ClientStdio {
    public static void main(String[] args) {

        var stdioParams = ServerParameters.builder("java")
                .args("-jar",
                        "build/libs/bitcoin-mcp-server-0.0.1-SNAPSHOT.jar")
                .build();

        var transport = new StdioClientTransport(stdioParams);
        var client = McpClient.sync(transport).build();

        client.initialize();

        McpSchema.ListToolsResult toolsList = client.listTools();
        System.out.println("Available Tools = " + toolsList);

        McpSchema.CallToolResult result = client.callTool(new McpSchema.CallToolRequest("getBitcoinPriceByCurrency", Map.of("currency", "USD")));
        System.out.println("Bitcoin Price in USD: " + result);

        client.closeGracefully();
    }
}
