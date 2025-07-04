package org.example.customer;




import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CustomerService {
    private final Map<Long, CustomerDTO> customerMap = new HashMap<>();
    private Long idCounter = 1L;


    public CustomerDTO createCustomer(CustomerDTO customer) {
        customer.setId(idCounter++);
        customerMap.put(customer.getId(), customer);
        return customer;
    }


    public CustomerDTO getCustomer(Long id) {
        return customerMap.get(id);
    }


    public List<CustomerDTO> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }


    public CustomerDTO updateCustomer(Long id, CustomerDTO updated) {
        CustomerDTO existing = customerMap.get(id);
        if (existing == null) return null;
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        return existing;
    }


    public boolean deleteCustomer(Long id) {
        return customerMap.remove(id) != null;
    }
}
