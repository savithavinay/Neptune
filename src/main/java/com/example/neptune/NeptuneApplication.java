package com.example.neptune;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class NeptuneApplication {

	public static void main(String[] args) {
		System.out.println("888888");
		SpringApplication.run(NeptuneApplication.class, args);
	}

	@Bean(initMethod = "init")
	public NeptuneConnect neptuneConnect(){
		return new NeptuneConnect();
	}

}

/*
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class NeptuneApplication extends SpringBootServletInitializer {

	@SuppressWarnings("resource")
	public static void main(final String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NeptuneApplication.class, args);

		context.getBean(NeptuneConnect.class).init(); // <-- here
	}
}*/
