package com.ozerian.lte.integration;

import com.ozerian.lte.AdminLteApplication;
import com.ozerian.lte.model.LteData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminLteApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LteDataIntegrationTest {

    @Inject
    TestRestTemplate restTemplate;

    @Test
    public void addLteData() {
        ResponseEntity<LteData> responseEntity =
                restTemplate.postForEntity("/tables/data/add",
                        new LteData(1L, "Webkit", "Safari 1.2", "S60", "4.3", "A"),
                        LteData.class);
        LteData lteData = responseEntity.getBody();

        assertEquals(Long.valueOf(1), lteData.getId());
        assertEquals("Webkit", lteData.getRenderingEngine());
        assertEquals("Safari 1.2", lteData.getBrowser());
        assertEquals("S60", lteData.getPlatform());
        assertEquals("4.3", lteData.getEngineVersion());
        assertEquals("A", lteData.getCssGrade());
    }
}
