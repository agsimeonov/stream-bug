package stream.bug.dao;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import stream.bug.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  @Query("SELECT c FROM Customer c")
  Stream<Customer> streamAll();
}
