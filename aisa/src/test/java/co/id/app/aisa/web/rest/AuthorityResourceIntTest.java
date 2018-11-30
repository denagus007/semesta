package co.id.app.aisa.web.rest;

import co.id.app.aisa.AisaApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the AuthorityResource REST controller.
 *
 * @see AuthorityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AisaApp.class)
public class AuthorityResourceIntTest {

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
/*
        AuthorityResource authorityResource = new AuthorityResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(authorityResource)
            .build();*/
    }

    /**
    * Test authority
    */
    @Test
    public void testAuthority() throws Exception {
        restMockMvc.perform(post("/api/authority/authority"))
            .andExpect(status().isOk());
    }

}
