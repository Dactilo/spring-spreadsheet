package io.dactilo.sumbawa.spring.spreadsheets.fiters.csv;

import io.dactilo.sumbawa.spring.spreadsheets.converter.api.SpreadsheetStreamerTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        SampleCSVConfiguration.class
})
public class CSVHandlerMethodReturnValueHandlerTest {
    private MockMvc mockMvc;

    private static ServletContext servletContext() {
        return new MockServletContext("/", new FileSystemResourceLoader());
    }

    @Before
    public void setUp() {
        try (AnnotationConfigWebApplicationContext configurableWebApplicationContext = new AnnotationConfigWebApplicationContext()) {
            configurableWebApplicationContext.setServletContext(servletContext());
            configurableWebApplicationContext.register(SampleCSVConfiguration.class);
            configurableWebApplicationContext.refresh();


            mockMvc = MockMvcBuilders.webAppContextSetup(configurableWebApplicationContext)
                    .build();
        }
    }

    @Test
    public void testSampleController_jsonOutputIsNotBroken() throws Exception {
        mockMvc.perform(get("/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"field1\":\"field 1\",\"field2\":2,\"field3\":null,\"field4\":false}]"));
    }

    @Test
    public void testSampleController_jsonInputIsNotBroken() throws Exception {
        mockMvc.perform(post("/json")
                .content("{\"field1\":\"field 123\",\"field2\":2,\"field3\":null,\"field4\":false}")
                .contentType("application/json")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"field1\":\"field 123\",\"field2\":2,\"field3\":null,\"field4\":false}]"));
    }

    @Test
    public void testSampleController_csvWorks() throws Exception {
        mockMvc.perform(get("/csv"))
                .andExpect(status().isOk())
                .andExpect(content().string("field1;field2;field3;field4\r\nfield 1;2;;false\r\n"));
    }
}

@Configuration
@EnableWebMvc
@EnableCSVFormatter
class SampleCSVConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public SampleController sampleController() {
        return new SampleController();
    }
}

@Controller
@RequestMapping("/")
@ResponseStatus(HttpStatus.OK)
class SampleController {
    @RequestMapping(value = "json", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<SpreadsheetStreamerTest.ObjectExampleDTO>> jsonPost(@RequestBody SpreadsheetStreamerTest.ObjectExampleDTO exampleDTO) {
        return new ResponseEntity<>(Collections.singletonList(
                exampleDTO
        ), HttpStatus.OK);
    }

    @RequestMapping(value = "json", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<SpreadsheetStreamerTest.ObjectExampleDTO>> json() {
        return new ResponseEntity<>(Collections.singletonList(
                new SpreadsheetStreamerTest.ObjectExampleDTO("field 1", 2, null, false)
        ), HttpStatus.OK);
    }

    @RequestMapping(value = "csv", method = RequestMethod.GET, produces = "text/csv")
    public ResponseEntity<List<SpreadsheetStreamerTest.ObjectExampleDTO>> csv() {
        return new ResponseEntity<>(Collections.singletonList(
                new SpreadsheetStreamerTest.ObjectExampleDTO("field 1", 2, null, false)
        ), HttpStatus.OK);
    }
}