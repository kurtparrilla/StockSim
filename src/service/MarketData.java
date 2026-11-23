package service;
import java.io.IOException;
import java.net.http.HttpClient;
import java.time.Duration;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.Set;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nyx
 */
public class MarketData {
    private final String apiKey = System.getenv("STOCKSIM_MARKET_API_KEY");
    private final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    private final Set<String> supportedSymbol = Set.of("AAPL", "MSFT", "NVDA", "GOOGL", "AMZN");
    public MarketData(){
        if(apiKey == null || apiKey.isBlank()){
            throw new IllegalStateException("Market api is not configured");
        }
    }
    public String fetchDailyData(String symbol) throws IOException, InterruptedException, IllegalArgumentException{
        if(symbol == null || !supportedSymbol.contains(symbol)){
            throw new IllegalArgumentException("The symbol is not supported");
        }
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="
                + symbol +"&outputsize=compact&apikey="+ apiKey;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(20)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            throw new IOException("Market request failed: HTTP " + response.statusCode());
        }
        return response.body();
    }
}
