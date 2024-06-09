package sdk.growthbook;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import growthbook.sdk.java.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Slf4j
@Configuration
public class GrowthBookAutoConfiguration {

    @Autowired
    private GrowthBookProperties growthBookProperties;


    @Bean
    @ConditionalOnProperty(name = "featureflag.provider", havingValue = "growthbook")
    @RequestScope
    public GrowthBook growthBook() {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                growthBookProperties.getServer().getUrl() + "/features/" +
                        growthBookProperties.getClient().getApiKey(), String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to fetch GrowthBook features.");
        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.getBody(), JsonObject.class);
        String featuresJson = jsonObject.get("features").toString();

        TrackingCallback trackingCallback = new TrackingCallback() {
            public <ValueType> void onTrack(
                    Experiment<ValueType> experiment,
                    ExperimentResult<ValueType> experimentResult
            ) {
                // TODO: Use your real analytics tracking system
                System.out.println("Viewed Experiment");
                System.out.println("Experiment Id: " + experiment.getKey());
                System.out.println("Variation Id: " + experimentResult.getVariationId());
            }
        };

        JsonObject userAttributesObj = new JsonObject();
        userAttributesObj.addProperty("tenant", "ff669c5d-dc18-433f-8a81-8a569358c391");

        GBContext context = GBContext.builder()
                .featuresJson(featuresJson)
                .attributesJson(userAttributesObj.toString()) // Optional
                .trackingCallback(trackingCallback)
                .build();
        GrowthBook growthBook = new GrowthBook(context);
        log.info("{} - listener - features {} ", growthBook.getClass().getPackageName(), featuresJson);
        return growthBook;
    }

}
