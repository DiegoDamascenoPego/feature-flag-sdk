package sdk.unleash;

import io.getunleash.Unleash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sdk.infra.FeatureFlag;

import java.util.function.Supplier;

public class UnleashService implements FeatureFlag {

    private Unleash unleash;

    public UnleashService(Unleash unleash) {
        this.unleash = unleash;
    }

    public boolean isEnabled(String featureName) {
        return unleash.isEnabled(featureName);
    }

    public <T> T executeIfEnabled(String featureName, Supplier<T> supplier) {
        if (isEnabled(featureName)) {
            return supplier.get();
        }
        return null;
    }
}
