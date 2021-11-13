package com.prjctr.hsa.stresstest.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import com.prjctr.hsa.stresstest.entity.Customer;
import com.prjctr.hsa.stresstest.entity.dto.CustomerDto;
import com.prjctr.hsa.stresstest.repository.CustomerRepo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepo repo;
    private RedisTemplate<String, Customer> template;
    
    private final Random random = new Random();

    CustomerServiceImpl(CustomerRepo repo, RedisTemplate<String, Customer> template) {
        this.repo = repo;
        this.template = template;
    }

    private static final String CUSTOMERS_KEY = "customers";

    @Value("${app.redis.ttl.value:60}")
    private int CACHE_TTL_SECONDS;

    @Value("${app.redis.ttl.origin:10}")
    private int CACHE_TTL_ORIGIN;
    
    public Customer createCustomer(CustomerDto customerDto) {
        Customer c = new Customer();
        c.setFirstName(customerDto.getFirstName());
        c.setLastName(customerDto.getFirstName());

        var customer = repo.save(c);
        template.opsForList().rightPush(CUSTOMERS_KEY, customer);
        template.expire(CUSTOMERS_KEY, Duration.ofSeconds(CACHE_TTL_SECONDS));

        return customer;
    }
    
    public List<Customer> getCustomers() {
        var size = template.opsForList().size(CUSTOMERS_KEY);
        if (size == 0) {
            var customers = repo.findAll();
            template.opsForList().rightPushAll(CUSTOMERS_KEY, customers);
            template.expire(CUSTOMERS_KEY, Duration.ofSeconds(CACHE_TTL_SECONDS));
            log.info("Load from DB");
            return customers;
        }

        var ttl = template.getExpire(CUSTOMERS_KEY);
        if (isCacheUpdateRequired(ttl)) {
            log.info("Update cache. TTL: {}", ttl);
            template.delete(CUSTOMERS_KEY);
            var customers = repo.findAll();
            template.opsForList().rightPushAll(CUSTOMERS_KEY, customers);
            template.expire(CUSTOMERS_KEY, Duration.ofSeconds(CACHE_TTL_SECONDS));
            return customers;
        }
        return template.opsForList().range(CUSTOMERS_KEY, 0, size);
    }
    
    private boolean isCacheUpdateRequired(long ttl) {
        return CACHE_TTL_ORIGIN + random.nextLong(CACHE_TTL_ORIGIN) >= ttl;
    }

    public String clearCache() {
        var res = template.delete(CUSTOMERS_KEY);
        return "Cache was deteled: " + res;
    }
}
