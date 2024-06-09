package sdk.unleash;

import io.getunleash.UnleashContext;
import io.getunleash.UnleashContextProvider;


public class CustomContextHandler implements UnleashContextProvider {


    private final UnleashProperties unleashProperties;

    public CustomContextHandler(UnleashProperties unleashProperties) {
        this.unleashProperties = unleashProperties;
    }

    @Override
    public UnleashContext getContext() {

        System.out.println("7c080ad1-8119-4013-8ca5-d53c0e321f6d");
       return UnleashContext.builder()
                .appName(unleashProperties.getClient().getAppName())
                .environment(unleashProperties.getClient().getEnvironment())
                .addProperty("tenant", "7c080ad1-8119-4013-8ca5-d53c0e321f6d")
                .build();
    }
}
