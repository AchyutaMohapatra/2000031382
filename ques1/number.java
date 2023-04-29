import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class NumberManagementServiceApplication {

    private final RestTemplate restTemplate;

    public NumberManagementServiceApplication(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/numbers")
    public Map<String, List<Integer>> getNumbers(@RequestParam List<String> url) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(url.size());
        List<Future<List<Integer>>> futures = new ArrayList<>();
        for (String u : url) {
            futures.add(executorService.submit(() -> getNumbersFromUrl(u)));
        }
        List<Integer> mergedNumbers = new ArrayList<>();
        for (Future<List<Integer>> future : futures) {
            try {
                mergedNumbers.addAll(future.get(500, TimeUnit.MILLISECONDS));
            } catch (TimeoutException e) {
                System.err.println("URL took too long to respond: " + e.getMessage());
            }
        }
        executorService.shutdown();
        Collections.sort(mergedNumbers);
        List<Integer> uniqueNumbers = mergedNumbers.stream().distinct().collect(Collectors.toList());
        Map<String, List<Integer>> response = new HashMap<>();
        response.put("numbers", uniqueNumbers);
        return response;
    }

    private List<Integer> getNumbersFromUrl(String url) {
        try {
            Map<String, Object> result = restTemplate.getForObject(url, Map.class);
            List<Integer> numbers = (List<Integer>) result.get("numbers");
            return numbers != null ? numbers : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error fetching numbers from URL " + url + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(NumberManagementServiceApplication.class, args);
    }

}
