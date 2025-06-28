package magalu.challenger.challenger.application.v1;

import magalu.challenger.challenger.shared.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


public class OrderControllerIT extends IntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void shouldImportOrdersFromFile() {
        ClassPathResource resource = new ClassPathResource("data_1.txt");
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/v1/order/upload",
                requestEntity,
                String.class
        );

        assert response.getStatusCode().is2xxSuccessful();
    }
}
