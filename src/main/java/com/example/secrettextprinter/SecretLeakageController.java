package com.example.secrettextprinter;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SecretLeakageController {

    private static Logger log = LoggerFactory.getLogger(SecretLeakageController.class);


    @Value("${password}")
    String hardcodedPassword;

    @Value("${ARG_BASED_PASSWORD}")
    String argBasedPassword;

    @Value("${DOCKER_ENV_PASSWORD}")
    String hardcodedEnvPassword;

    @Value("${SPECIAL_K8S_SECRET}")
    String configmapK8sSecret;

    @Value("${SPECIAL_SPECIAL_K8S_SECRET}")
    String secretK8sSecret;

    @GetMapping("/spoil-1")
    public String getHardcodedSecret() {
        return hardcodedPassword;
    }

    @GetMapping("/spoil-2")
    public String getEnvArgBasedSecret() {
        return argBasedPassword;
    }

    @GetMapping("/spoil-3")
    public String getEnvStaticSecret() {
        return hardcodedEnvPassword;
    }

    @GetMapping("/spoil-4")
    public String getOldSecret() {
        return Constants.password;
    }

    @GetMapping("/spoil-5")
    public String getK8sSecret() {
        return configmapK8sSecret;
    }

    @GetMapping("/spoil-6")
    public String getSecretK8sSecret() {
        return secretK8sSecret;
    }

    @GetMapping("/challenge")
    public String greetingForm(Model model) {
        model.addAttribute("challengeForm", new ChallengeForm());
        return "challenge";
    }

    @PostMapping("/challenge/1")
    public ResponseEntity postController(@RequestBody ChallengeForm challengeForm) {
        log.info("POST received - serializing form: solution: " + challengeForm.getSolution());
        return setResponse(hardcodedPassword, challengeForm.getSolution());
    }

    @PostMapping("/challenge/2")
    public ResponseEntity postController2(@RequestBody ChallengeForm challengeForm) {
        log.info("POST received - serializing form: solution: " + challengeForm.getSolution());
        return setResponse(argBasedPassword, challengeForm.getSolution());
    }

    @PostMapping("/challenge/3")
    public ResponseEntity postController3(@RequestBody ChallengeForm challengeForm) {
        log.info("POST received - serializing form: solution: " + challengeForm.getSolution());
        return setResponse(hardcodedEnvPassword, challengeForm.getSolution());
    }

    @PostMapping("/challenge/4")
    public ResponseEntity postController4(@RequestBody ChallengeForm challengeForm) {
        log.info("POST received - serializing form: solution: " + challengeForm.getSolution());
        return setResponse(Constants.password, challengeForm.getSolution());
    }

    @PostMapping("/challenge/5")
    public ResponseEntity postController5(@RequestBody ChallengeForm challengeForm) {
        log.info("POST received - serializing form: solution: " + challengeForm.getSolution());
        return setResponse(configmapK8sSecret, challengeForm.getSolution());
    }

    @PostMapping("/challenge/6")
    public ResponseEntity postController6(@RequestBody ChallengeForm challengeForm) {
        log.info("POST received - serializing form: solution: " + challengeForm.getSolution());
        return setResponse(secretK8sSecret, challengeForm.getSolution());
    }

    private ResponseEntity setResponse(String target, String providedSolution) {
        if (target.equals(providedSolution)) {
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("Wrong anser!");
        }
    }


}