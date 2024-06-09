package sdk.growthbook;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import growthbook.sdk.java.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sdk.infra.FeatureFlag;

public class GrowthBookService implements FeatureFlag {

    private final GrowthBook growthBook;

    public GrowthBookService(GrowthBook growthBook) {
        this.growthBook = growthBook;
    }

    @Override
    public boolean isEnabled(String flag) {
        return growthBook.isOn(flag);
    }

}
