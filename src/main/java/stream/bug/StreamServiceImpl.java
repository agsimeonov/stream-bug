package stream.bug;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stream.bug.dao.CustomerRepository;
import stream.bug.domain.Customer;

@Service
public class StreamServiceImpl implements StreamService {

  @Autowired
  private CustomerRepository repository;

  @Override
  @Transactional
  public void doStuff() {
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
  }
}
