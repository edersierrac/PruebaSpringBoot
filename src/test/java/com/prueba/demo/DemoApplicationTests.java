package com.prueba.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
    void testMain() {

        mockStatic(SpringApplication.class);
        when(SpringApplication.run(DemoApplication.class, new String[] {})).thenReturn(null);

        DemoApplication.main(new String[] {});

        verify(SpringApplication.class);
        SpringApplication.run(DemoApplication.class, new String[] {});

    }

    @Test
    void contextLoads() {
        DemoApplication aplication = new DemoApplication();
        assertNotNull(aplication);
    }


}
