package com.parkour.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.parkour.ParkourApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = ParkourApplication.class)
@ContextConfiguration(classes = ParkourApplication.class)
public class CucumberSpringConfiguration {
}
