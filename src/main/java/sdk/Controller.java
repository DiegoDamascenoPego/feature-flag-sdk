package sdk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sdk.infra.FeatureFlag;
import sdk.infra.FeatureToggle;


@RestController
@RequestMapping("/cadastros")
public class CadastroController {


    @Autowired
    FeatureFlag featureFlag;

    @GetMapping("/{flag}")
    public ResponseEntity<String> flag(@PathVariable String flag) {

        if (featureFlag.isEnabled(flag)) {
            return new ResponseEntity<>(flag + " Enabled", HttpStatus.OK);
        }
        return new ResponseEntity<>(flag + " Disabled", HttpStatus.OK);
    }

    @GetMapping("/annotation/{flag}")
    @FeatureToggle("growthbook")
    public ResponseEntity<String> annotation(@PathVariable String flag) {

        if (featureFlag.isEnabled(flag)) {
            return new ResponseEntity<>(flag + " Enabled", HttpStatus.OK);
        }
        return new ResponseEntity<>(flag + " Disabled", HttpStatus.OK);
    }


}
