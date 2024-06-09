package sdk.growthbook;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "growthbook")
@Data
@Component
public class GrowthBookProperties {

    private Server server = new Server();
    private Client client = new Client();

    @Data
    public static class Server {
        private String url;
    }

    @Data
    public static class Client {
        private String appName;
        private String apiKey;
        private String environment;
    }
}
