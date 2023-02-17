package gigjob.firebase;

import com.google.firebase.auth.FirebaseAuthException;
import gigjob.firebase.authentication.TokenVerifier;
import gigjob.firebase.authentication.UserManagementService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class AuthTest {
//    @MockBean
//    UserManagementService userManagementService;

    @Test
    void TestGetInfoFromIdToken() throws Exception {
        String idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImI0OWM1MDYyZDg5MGY1Y2U0NDllODkwYzg4ZThkZDk4YzRmZWUwYWIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIzOTU1NDAxMTc0MzktMnBtNXZpbDk2MHY3bjQ4OHZtNnNkajdzZHJ2OWp2NXMuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIzOTU1NDAxMTc0MzktaGtiajVsazJobnJ0Y3RlaWhrN2N2OGd0c21tbWJwM3UuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDQ2NTc3MTM5MTAzODk1NTk2NDEiLCJoZCI6ImZwdC5lZHUudm4iLCJlbWFpbCI6InRhaWN0c2UxNjE1NjlAZnB0LmVkdS52biIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiQ2hhdSBUYW4gVGFpIChLMTZfSENNKSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BRWRGVHA0XzJpNEpFeVBTOGc5SWFaR3pTekJRSVo2UndKWGFOZXI2djFOVz1zOTYtYyIsImdpdmVuX25hbWUiOiJDaGF1IFRhbiBUYWkiLCJmYW1pbHlfbmFtZSI6IihLMTZfSENNKSIsImxvY2FsZSI6ImVuLUdCIiwiaWF0IjoxNjc2MjEwODYzLCJleHAiOjE2NzYyMTQ0NjN9.dnxTkOoxs8oUH19kz39boGvQ951ukIQQyahSJ-30I3Y84R7GcXgpd-AeIw1fZ4NB6YARJoq24bttwu-M53ZDS_u91Hr2ZjiuWxXO5jiUlw73bzNBuj5Atg8MMkr7lhZkJ232ujPPtAgIMDdSpKAmm__nBXVyVlkfbHm7Vx6Yp7GsGc7Du2nijpfhcUzpMZ";
        TokenVerifier tokenVerifier = new TokenVerifier();
        Assertions.assertEquals("taictse161569@fpt.edu.vn", tokenVerifier.validate(idToken).getEmail());
    }

    @Test
    void TestGetFirebaseUserByEmail() throws FirebaseAuthException {
        UserManagementService userManagementService = new UserManagementService();
        String uid = "1XnpjfLMS0MGvXeowUfzoEOcsfj1";
        String actualEmail = userManagementService.getFirebaseUserById(uid).getEmail();
        Assertions.assertEquals("anhthuyn2412@gmail.com", actualEmail);
    }

    @Test
    void GeneratingRandomAlphanumericString() {
        String generatedString = RandomStringUtils.randomAlphanumeric(15);
        String PREFIX = "shop";
        System.out.println(PREFIX + "_" + generatedString);
    }

    @Test
    void PasswordEncode() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean result = passwordEncoder.matches("1", "$2a$10$i4O71WZuR8OJQdQ.aRTBqOMwwOrVS/NNygShu8rZuU6S9HrV76gMG");
        Assertions.assertTrue(result);
    }

}
