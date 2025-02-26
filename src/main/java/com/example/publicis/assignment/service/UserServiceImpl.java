package com.example.publicis.assignment.service;

import com.example.publicis.assignment.exception.UserNotFoundException;
import com.example.publicis.assignment.model.User;
import com.example.publicis.assignment.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityManager;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    EntityManager entityManager;

    @Value("${dummy.json.url}")
    String dummyJsonUrl;

    @Override
    @Retry(name = "dummyJsonRetry", fallbackMethod = "fallbackLoadUsers")
    public void loadUsersFromExternalApi() {
        if (userRepository.count() > 0) {
            System.out.println("Users already loaded. Skipping external API call.");
            return;
        }

        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(dummyJsonUrl, JsonNode.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode usersArray = response.getBody().get("users");
                List<User> users = new ArrayList<>();

                for (JsonNode node : usersArray) {
                    users.add(new User(
                            node.get("id").asLong(),
                            node.get("firstName").asText(),
                            node.get("lastName").asText(),
                            node.get("age").asInt(),
                            node.get("gender").asText(),
                            node.get("email").asText(),
                            node.get("phone").asText(),
                            node.get("birthDate").asText(),
                            node.has("ssn") ? node.get("ssn").asText() : "N/A"
                    ));
                }

                userRepository.saveAll(users);

                SearchSession searchSession = Search.session(entityManager);
                searchSession.massIndexer(User.class).startAndWait();

                System.out.println("Users successfully loaded and indexed.");
            } else {
                throw new RestClientException("Failed to fetch users: " + response.getStatusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while fetching users from external API.", e);
        }
    }

    public void fallbackLoadUsers(Throwable exception) {
        System.err.println("Fallback: Could not load users from DummyJSON. Reason: " + exception.getMessage());
    }


    @Override
    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    @Cacheable(value = "users", key = "#email")
    public User getUserByEmail(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @Override
    public List<User> searchUsers(String keyword) {
        SearchSession searchSession = Search.session(entityManager);

        List<User> users = searchSession.search(User.class)
                .where(f -> f.bool(b -> b
                        .should(f.wildcard()
                                .fields("firstName", "lastName", "ssn")
                                .matching("*" + keyword.toLowerCase() + "*"))
                        .should(f.match()
                                .fields("firstName", "lastName", "ssn")
                                .matching(keyword))
                ))
                .fetchAllHits();

        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found matching keyword: " + keyword);
        }

        return users;
    }
}
