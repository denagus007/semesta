package co.id.app.aisa.cucumber.stepdefs;

import co.id.app.aisa.AisaApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = AisaApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
