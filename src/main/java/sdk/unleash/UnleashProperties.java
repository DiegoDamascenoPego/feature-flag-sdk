package sdk.unleash;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "unleash")
@Data
public class UnleashProperties {

    private Server server = new Server();
    private Client client = new Client();
    private Database database = new Database();

    @Data
    public static class Server {
        private String url;
    }

    @Data
    public static class Client {
        private String appName;
        private String instanceId;
        private String apiKey;

        private String projectName;

        private String environment;


    }

    @Data
    public static class Database {
        private String driver;
        private String url;
        private String username;
        private String password;
    }
}
