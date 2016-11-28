package stream.bug;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import stream.bug.dao.CustomerRepository;
import stream.bug.domain.Customer;

@SpringBootApplication
public class StreamBugApplication {

  @Autowired
  private StreamService service;

  public static void main(String[] args) {
    SpringApplication.run(StreamBugApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo(CustomerRepository repository) {
    return (args) -> {
      for (int i = 1; i <= 3000; i++) {
        repository.save(new Customer("First" + i, "Last" + i));
      }

      try {
        service.doStuff();
      } catch (Exception e) {
        e.printStackTrace();
        throw new BeanCreationException("A stream bug has occurred!");
      }
    };
  }
}
