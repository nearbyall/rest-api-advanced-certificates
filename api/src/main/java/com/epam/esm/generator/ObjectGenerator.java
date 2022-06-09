package com.epam.esm.generator;

import com.epam.esm.exception.GenerationException;
import com.epam.esm.persistence.repository.entity.Role;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.certificate.CertificateNewDTO;
import com.epam.esm.service.dto.tag.TagDTO;
import com.epam.esm.service.dto.tag.TagPostDTO;
import com.epam.esm.service.dto.user.UserPostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ObjectGenerator {

    private final CertificateService certificateService;
    private final TagService tagService;
    private final UserService userService;
    private final OrderService orderService;

    private final RestTemplate restTemplate;
    private final Random random;

    private static final String RANDOM_WORDS_URI = "https://random-word-api.herokuapp.com/word?number={number}";

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin12345";

    @Autowired
    public ObjectGenerator(CertificateService certificateService,
                           TagService tagService,
                           UserService userService,
                           OrderService orderService) {
        this.certificateService = certificateService;
        this.tagService = tagService;
        this.userService = userService;
        this.orderService = orderService;
        this.restTemplate = new RestTemplate();
        this.random = new Random();
    }

    public List<TagDTO> generateTags(int quantity) {
        String[] tagNames = restTemplate.getForEntity(RANDOM_WORDS_URI, String[].class, quantity)
                .getBody();

        if (null != tagNames) {
            return IntStream.range(0, quantity)
                    .mapToObj(i -> tagService.create(new TagPostDTO().setName(tagNames[i])))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public List<Integer> generateCertificates(int quantity, List<TagDTO> tags, List<String> wordsForGenerations) {
        return IntStream.range(0, quantity)
                .mapToObj(i -> certificateService.create(new CertificateNewDTO()
                        .setName(generateCertificateName(wordsForGenerations))
                        .setDescription(generateDescription(wordsForGenerations))
                        .setPrice(generatePrice())
                        .setDuration(random.nextInt(365) + 1)
                        .setTags(getTags(tags))))
                .filter(Optional::isPresent)
                .map(gc -> gc.get().getId())
                .collect(Collectors.toList());
    }

    public List<Integer> generateUsers(int quantity, List<String> wordsForGenerations) {
        Map<String, String> userCredentials = generateUserCredentials(quantity, wordsForGenerations);
        userService.create(new UserPostDTO()
                .setUsername(ADMIN_USERNAME)
                .setPassword(ADMIN_PASSWORD)
                .setRole(Role.ADMIN));
        return userCredentials.entrySet()
                .stream()
                .map(user -> userService.create(new UserPostDTO()
                        .setUsername(user.getKey())
                        .setPassword(user.getValue())
                        .setRole(Role.USER)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public int generateOrders(List<Integer> gcIds, List<Integer> userIds) {
        Collections.shuffle(gcIds, random);

        AtomicInteger ordersCount = new AtomicInteger();

        Queue<Integer> gcQueue = new LinkedList<>(gcIds);
        userIds.forEach(userId -> {
            int ordersPerUser = random.nextInt(8);

            IntStream.range(0, ordersPerUser).forEach(i -> {
                orderService.create(userId, gcQueue.poll());
                ordersCount.getAndIncrement();
            });
        });

        return ordersCount.get();
    }

    public List<String> getGeneratedWords(int quantity) {
        String[] words = restTemplate.getForEntity(RANDOM_WORDS_URI, String[].class, quantity).getBody();

        if (null == words) {
            throw new GenerationException("Generation failed");
        }

        return Arrays.asList(words);
    }

    private Map<String, String> generateUserCredentials(int quantity, List<String> wordsForGenerations) {
        Map<String, String> userCredentials = new HashMap<>();

        IntStream.range(0, quantity).sequential().forEach(i -> {
            String username = generateUsername(wordsForGenerations);
            String password = generatePassword(wordsForGenerations);

            userCredentials.put(username, password);
        });

        return userCredentials;
    }

    private List<TagDTO> getTags(List<TagDTO> tags) {
        int quantity = random.nextInt(10) + 2;

        return IntStream.range(0, quantity)
                .mapToObj(i -> tags.get(random.nextInt(tags.size())))
                .distinct()
                .collect(Collectors.toList());
    }

    private String generatePrice() {
        double price = (random.nextInt(300) + 10) + random.nextDouble();

        return new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.US)).format(price);
    }

    private String generateUsername(List<String> wordsForGenerations) {
        return wordsForGenerations.get(random.nextInt(wordsForGenerations.size() - 1)) +
                random.nextInt(9999);
    }

    private String generatePassword(List<String> wordsForGenerations) {
        StringBuilder password = new StringBuilder();

        IntStream.range(0, 2)
                .forEach(i -> password
                        .append(wordsForGenerations.get(random.nextInt(wordsForGenerations.size() - 1))));
        password.append(random.nextInt(9999));

        return password.toString();
    }

    private String generateCertificateName(List<String> wordsForGenerations) {
        int nameWordsCount = random.nextInt(3) + 1;
        StringBuilder name = generateText(wordsForGenerations, nameWordsCount);
        name.deleteCharAt(name.length() - 1);

        return name.toString();
    }

    private String generateDescription(List<String> wordsForGenerations) {
        int descriptionWordsCount = random.nextInt(30) + 10;

        StringBuilder description = generateText(wordsForGenerations, descriptionWordsCount);
        description.replace(description.length() - 1, description.length(), ".");

        return description.toString();
    }

    private StringBuilder generateText(List<String> wordsForGenerations, int count) {
        List<String> words = getWordsForGeneration(wordsForGenerations, count);
        StringBuilder text = new StringBuilder();

        words.forEach(word -> text.append(word).append(" "));
        text.setCharAt(0, String.valueOf(text.charAt(0)).toUpperCase(Locale.ROOT).charAt(0));

        return text;
    }

    private List<String> getWordsForGeneration(List<String> wordsForGenerations, int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> wordsForGenerations.get(random.nextInt(wordsForGenerations.size() - 1)))
                .collect(Collectors.toList());
    }

}
