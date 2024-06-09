package sdk.unleash;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.event.UnleashReady;
import io.getunleash.event.UnleashSubscriber;
import io.getunleash.repository.FeatureToggleResponse;
import io.getunleash.repository.ToggleCollection;
import io.getunleash.util.UnleashConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import sdk.infra.FeatureFlag;

@Configuration
@EnableConfigurationProperties(UnleashProperties.class)
@ConditionalOnClass(Unleash.class)
@Slf4j
public class UnleashAutoConfiguration {

    @Autowired
    private UnleashProperties unleashProperties;

    @Bean
    @ConditionalOnProperty(name = "featureflag.provider", havingValue = "unleash")
    public Unleash unleash() {
        UnleashConfig unleashConfig = UnleashConfig.builder()
                .appName(unleashProperties.getClient().getAppName())
                .instanceId(unleashProperties.getClient().getInstanceId())
                .unleashAPI(unleashProperties.getServer().getUrl())
                .unleashContextProvider(new CustomContextHandler(unleashProperties) )
                .apiKey(unleashProperties.getClient().getApiKey())
                .projectName(unleashProperties.getClient().getProjectName())
                .environment(unleashProperties.getClient().getEnvironment())
                .subscriber(new UnleashSubscriber() {
                    @Override
                    public void onReady(UnleashReady ready) {
                        System.out.println("Unleash is ready");
                    }

                    @Override
                    public void togglesFetched(FeatureToggleResponse toggleResponse) {
                        System.out.println("Fetch toggles with status: " + toggleResponse.getStatus());
                    }

                    @Override
                    public void togglesBackedUp(ToggleCollection toggleCollection) {
                        System.out.println("Backup stored.");
                    }
                })
                .build();


        DefaultUnleash defaultUnleash = new DefaultUnleash(unleashConfig);

        log.info("{} - listener ", defaultUnleash.getClass().getPackageName());
        return defaultUnleash;
    }

}
