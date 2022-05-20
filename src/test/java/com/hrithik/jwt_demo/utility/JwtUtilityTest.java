package com.hrithik.jwt_demo.utility;

import com.hrithik.jwt_demo.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilityTest {
    JwtUtility jwtUtility = new JwtUtility();

    @Test
    void validToken() {
        String token = getValidToken();
        assertTrue(jwtUtility.validate(token));
    }

    @Test
    void charReplacedToken() {
        //1. take a valid token, replace one char and try to parse it
        StringBuilder token = new StringBuilder(getValidToken());
        if (token.charAt(70) == 'B')
            token.setCharAt(70, 'Y');
        else
            token.setCharAt(70, 'B');
        assertThatThrownBy(() -> jwtUtility.validate(String.valueOf(token)))
                .isInstanceOf(MalformedJwtException.class);
    }

    @Test
    void DotRemovedToken() {
//        3. take a valid token, remove one . and then try to parse it
        String validToken = getValidToken();
        String replace = validToken.replace(".", "");
        assertThatThrownBy(() -> jwtUtility.validate(replace))
                .isInstanceOf(MalformedJwtException.class);
    }

    @Test
    void expiredToken() {
        // 2. take a valid expired token, try to parse it
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOiIxMSIsInJvbGUiOiJQYXRpZW50IiwiaWF0IjoxNjUzMDAwNDg4LCJleHAiOjE2NTMwMDA3ODh9.qpBMnp1BFQeUXG1i9IrAUc3bSwG4Ij75igeJyZIMfm8W0cMyaHaDfZNP66aQzXvDQ7cLR35I-ixrXGx79krcog";
        assertThatThrownBy(() -> jwtUtility.validate(token))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    void randomToken() {
        // 4. take any random string with two dots (format: abc.pqr.xyz) and try to parse it
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciO.eyJ1c2VySWsSWQiOiJocml0aGlrQG9uZW4uY29t.OiJKV1QiLCJhbGciOeyJ1c2VySWsSWQiOiJocml";
        assertThatThrownBy(() -> jwtUtility.validate(token))
                .isInstanceOf(MalformedJwtException.class);
    }

    @Test
    void differentSecretKey() {
        //5. parse a valid and non expired token but with a different secret key (it should fail)
        //vaid for 2 days with key("secret")
        String validToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOiIxMSIsInJvbGUiOiJQYXRpZW50IiwiaWF0IjoxNjUzMDAxMTc2LCJleHAiOjE2NTMxNzM5NzZ9.w8FoD0r4kY6xOvX8VD6iW4pjWMLTE2CHKzYSQFkLEh01JSkCHzOs27TpJnUJf--VYA9LstXVvAmchFkLH2F9qw";
        assertThatThrownBy(() -> jwtUtility.validate(validToken))
                .isInstanceOf(SignatureException.class);
    }

    private String getValidToken() {
        User user = new User(1L, "Patient");
        return jwtUtility.generateToken(user);
    }
}