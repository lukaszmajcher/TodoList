package pl.majchrosoft.ToDoList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ToDoListConfiguration {

    @Bean
    public Clock clock(){
        log.info("Registering Clock as Spring bean");
        return new SystemClock();
    }
}
