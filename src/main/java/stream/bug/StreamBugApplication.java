package stream.bug;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import stream.bug.dao.CustomerRepository;
import stream.bug.domain.Customer;

@SpringBootApplication
public class StreamBugApplication {

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
        try (Stream<Customer> stream = repository.streamAll()) {
          stream.forEach(customer -> {
            try {
              File data = new File(getClass().getClassLoader().getResource("data.txt").getFile());
              try (BufferedReader reader = new BufferedReader(new FileReader(data))) {
                while (reader.readLine() != null) {
                  // Do stuff for the current customer
                }
              }
            } catch (IOException e) {}
            System.out.println(customer);
          });
        }
      } catch (Exception e) {
        e.printStackTrace();
        throw new BeanCreationException("A stream bug has occurred!");
      }
    };
  }
}
