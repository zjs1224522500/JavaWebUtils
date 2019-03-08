package tech.shunzi.demo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Before
    public void setUpEnv()
    {
        environmentVariables.set("key","value");
    }
}
