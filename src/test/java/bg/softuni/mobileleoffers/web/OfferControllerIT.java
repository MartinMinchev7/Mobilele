package bg.softuni.mobileleoffers.web;

import bg.softuni.mobileleoffers.model.entity.OfferEntity;
import bg.softuni.mobileleoffers.model.enums.EngineTypeEnum;
import bg.softuni.mobileleoffers.repository.OfferRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static bg.softuni.mobileleoffers.model.enums.EngineTypeEnum.DIESEL;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerIT {

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testGetById() throws Exception {
        var actualEntity = createTestOffer();

        ResultActions result = mockMvc.perform(get("/offers/{id}", actualEntity.getId())
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.id", is(actualEntity.getId().intValue())))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.description", is(actualEntity.getDescription())))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.mileage", is(actualEntity.getMileage())))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.price", is(actualEntity.getPrice())));

    }

    @Test
    public void testOfferNotFound() throws Exception {
        mockMvc.
                perform(get("/offers/{id}", "1000000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


    }

    @Test
    public void testCreateOffer() throws Exception {
        MvcResult result = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "description": "Test description",
                    "mileage": 12345,
                    "price": 123,
                    "engineType": "DIESEL"
                }
                """)
        ).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andReturn();

        String body = result.getResponse().getContentAsString();

        int id = JsonPath.read(body, "$.id");

        Optional<OfferEntity> createdOfferOpt = offerRepository.findById((long)id);

        Assertions.assertTrue(createdOfferOpt.isPresent());

        OfferEntity createdOffer = createdOfferOpt.get();

        Assertions.assertEquals("Test description", createdOffer.getDescription());
        Assertions.assertEquals(12345, createdOffer.getMileage());
        Assertions.assertEquals(123, createdOffer.getPrice());
        Assertions.assertEquals(DIESEL, createdOffer.getEngine());

    }

    @Test
    public void testDeleteOffer() throws Exception {
        var actualEntity = createTestOffer();

        mockMvc.perform(delete("/offers/{id}", actualEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        Assertions.assertTrue(
               offerRepository.findById(actualEntity.getId()).isEmpty()
        );
    }

    private OfferEntity createTestOffer() {
        return offerRepository.save(
                new OfferEntity()
                        .setDescription("test offer")
                        .setEngine(DIESEL)
                        .setMileage(10000)
                        .setPrice(2000)
        );
    }
}
