package sdk.infra;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sdk.unleash.UnleashService;

@Aspect
@Component
public class FeatureToggleAspect {

    @Autowired
    private FeatureFlag featureFlag;

    @Around("@annotation(featureToggle)")
    public Object around(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        if (featureFlag.isEnabled(featureToggle.value())) {
            return joinPoint.proceed();
        } else {
            return null;
        }
    }
}
