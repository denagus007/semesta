package co.id.app.aisa.web.rest;

import co.id.app.aisa.AisaApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the PermissionResource REST controller.
 *
 * @see PermissionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AisaApp.class)
public class PermissionResourceIntTest {

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        PermissionResource permissionResource = new PermissionResource(null,null);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(permissionResource)
            .build();
    }

    /**
    * Test permission
    */
    @Test
    public void testPermission1() throws Exception {
        restMockMvc.perform(post("/api/permission/permission"))
            .andExpect(status().isOk());
    }
    /**
    * Test permission
    */
    @Test
    public void testPermission2() throws Exception {
        restMockMvc.perform(get("/api/permission/permission"))
            .andExpect(status().isOk());
    }
    /**
    * Test permission
    */
    @Test
    public void testPermission3() throws Exception {
        restMockMvc.perform(delete("/api/permission/permission"))
            .andExpect(status().isOk());
    }
    /**
    * Test permission
    */
    @Test
    public void testPermission() throws Exception {
        restMockMvc.perform(put("/api/permission/permission"))
            .andExpect(status().isOk());
    }

}
