package sdk.infra;

public interface FeatureFlag {

    boolean isEnabled(String featureName);
}
