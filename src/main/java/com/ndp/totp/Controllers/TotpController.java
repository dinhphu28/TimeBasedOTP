package com.ndp.totp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ndp.totp.Models.RequestMultiTotpModel;
import com.ndp.totp.Models.ReturnModel;
import com.ndp.totp.Utils.TOTPUtils;

@RestController
@RequestMapping("/api/v1/totp")
public class TotpController {
    @Autowired
    private TOTPUtils totpUtils;

    @GetMapping(
        value = "/secretkey",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> retrieveQR() {
        ResponseEntity<Object> entity;

        String secKey = totpUtils.generateSecretKey();

        entity = new ResponseEntity<>(new ReturnModel("SecKey", "otpauth://totp/user?secret=" + secKey + "&issuer=2fademo"), HttpStatus.OK);

        return entity;
    }

    @GetMapping(
        value = "/verifytotp",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> verifyTotp(@RequestParam(value = "secret", required = true) String secret, @RequestParam(value = "code", required = true) String code) {
        ResponseEntity<Object> entity;

        Boolean isVerified = totpUtils.verifyTotp(secret, code);

        entity = new ResponseEntity<>(new ReturnModel("verified is " + isVerified, "This token is verified"), HttpStatus.OK);

        return entity;
    }

    @GetMapping(
        value = "/verifyOnlyOneCodeRequired",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> verifyOnlyOneCodeRequired(@RequestParam(value = "secret", required = true) String secret, @RequestParam(value = "code", required = true) String code) {
        ResponseEntity<Object> entity;

        Boolean isVerified = totpUtils.verifyTotp(secret, code);

        entity = new ResponseEntity<>(new ReturnModel("verified is " + isVerified, "This token is verified"), HttpStatus.OK);

        return entity;
    }

    @PostMapping(
        value = "/verifyMultipleCodeRequired",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> verifyMultipleCodeRequired(@RequestBody RequestMultiTotpModel requestMultiTotpModel) {
        ResponseEntity<Object> entity;

        Boolean isVerified = totpUtils.verifyMultipleCodeRequired(requestMultiTotpModel.getSecret(), requestMultiTotpModel.getCode());

        entity = new ResponseEntity<>(new ReturnModel("verified is " + isVerified, "This token is verified"), HttpStatus.OK);

        return entity;
    }
}
