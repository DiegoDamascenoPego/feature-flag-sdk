package sdk.infra;

import growthbook.sdk.java.GrowthBook;
import io.getunleash.Unleash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import sdk.growthbook.GrowthBookService;
import sdk.unleash.UnleashService;

@Configuration
@Slf4j
public class FeatureFlagConfiguration {
    @Bean
    @ConditionalOnProperty(name = "featureflag.provider", havingValue = "unleash")
    public FeatureFlag featureFlagUnleash(Unleash unleash) {

        UnleashService unleashService = new UnleashService(unleash);
        log.info("{} - started ", unleashService.getClass().getPackageName());
        return unleashService;
    }

    @Bean
    @ConditionalOnProperty(name = "featureflag.provider", havingValue = "growthbook")
    public FeatureFlag featureFlagGrowthBook(GrowthBook growthBook) {
        GrowthBookService growthBookService = new GrowthBookService(growthBook);
        log.info("{} - started ", growthBookService.getClass().getPackageName());
        return growthBookService;
    }

}
