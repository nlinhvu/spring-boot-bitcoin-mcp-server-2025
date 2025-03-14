# Bitcoin MCP Server

A demonstration of A **Spring Boot/AI** MCP Server that tracks Bitcoin prices using **CoinGecko APIs** (api.coingecko.com/api/v3)

> **Note:** The implementation in `BitcoinServiceClient` class is generated by [**Claude 3.7 Sonnet**](https://www.anthropic.com/claude)

## Prerequisites

- Java 17 or later
- Maven 3.6 or later 
- Claude Desktop installed with a Claude Account

## Build

To integrate with Claude Desktop, need to build a jar file:

```bash
./gradlew clean build -x test
```

## Claude Desktop Integration

To integrate with Claude Desktop, add the following configuration to your Claude Desktop settings:

```json
{
  "mcpServers": {
    "bitcoin-mcp-server": {
      "command": "java",
      "args": [
        "-jar",
        "/absolute/path/to/bitcoin-mcp-server-0.0.1-SNAPSHOT.jar"
      ]
    }
  }
}
```

## Additional Resources

- [**Weather STDIO MCP Server - Christian Tzolov**](https://github.com/spring-projects/spring-ai-examples/tree/main/model-context-protocol/weather/starter-stdio-server)
- [**MCP Server Boot Starter**](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html)
- [**CoinGecko APIs References**](https://docs.coingecko.com/reference/simple-price)