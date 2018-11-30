package co.id.app.geisa.cucumber.stepdefs;

import co.id.app.geisa.GeisaApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = GeisaApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
