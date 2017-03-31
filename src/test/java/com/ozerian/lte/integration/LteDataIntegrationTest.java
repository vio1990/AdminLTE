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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminLteApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Sql(scripts = "/createLteData.sql")
public class LteDataIntegrationTest {

    @Inject
    TestRestTemplate restTemplate;

    @Test
    public void addLteData() {
        ResponseEntity<LteData> responseEntity =
                restTemplate.postForEntity("/tables/data/add",
                        new LteData(4L, "Webkit", "Safari 1.2", "S60", "4.3", "A"),
                        LteData.class);
        LteData lteData = responseEntity.getBody();

        assertEquals(Long.valueOf(4L), lteData.getId());
        assertEquals("Webkit", lteData.getRenderingEngine());
        assertEquals("Safari 1.2", lteData.getBrowser());
        assertEquals("S60", lteData.getPlatform());
        assertEquals("4.3", lteData.getEngineVersion());
        assertEquals("A", lteData.getCssGrade());
    }

    @Test
    public void getLteDataById() {
        ResponseEntity<LteData> responseEntity =
                restTemplate.getForEntity("/tables/data/2", LteData.class);
        LteData lteData = responseEntity.getBody();

        assertEquals(Long.valueOf(2), lteData.getId());
        assertEquals("Gecko", lteData.getRenderingEngine());
        assertEquals("Chrome 3.1", lteData.getBrowser());
        assertEquals("60S", lteData.getPlatform());
        assertEquals("3.2", lteData.getEngineVersion());
        assertEquals("A", lteData.getCssGrade());
    }

}
