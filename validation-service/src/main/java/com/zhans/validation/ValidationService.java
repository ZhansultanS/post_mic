package com.zhans.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhans.validation.dto.ValidationRequestDto;
import com.zhans.validation.dto.ValidationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationService {

    @Value("${app.validation.kafka.topic.response}")
    private String validationTopic;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    @KafkaListener(topics = "${app.validation.kafka.topic.request}")
    private void listenValidationRequest(String validationRequestString) {
        ValidationRequestDto validationRequest = objectMapper.readValue(validationRequestString, ValidationRequestDto.class);
        System.out.println(validationRequest);
        new Thread(() -> {
            try {
                Thread.sleep(5 * 1000);
                System.out.println(validationRequest);
                List<ValidationResponseDto.BadWord> badWords = validate(validationRequest.getContent());
                sendValidationResponse(validationRequest.getPostId(), badWords);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @SneakyThrows
    private void sendValidationResponse(Long postId, List<ValidationResponseDto.BadWord> badWords) {
        ValidationResponseDto validationResponse = new ValidationResponseDto(postId, badWords.size() == 0, badWords);
        String validationResponseString = objectMapper.writeValueAsString(validationResponse);
        kafkaTemplate.send(validationTopic, validationResponseString);
    }

    private List<ValidationResponseDto.BadWord> validate(String content) {
        String[] badWordList = {"fuck", "shit"};
        List<ValidationResponseDto.BadWord> badWords = new ArrayList<>();
        for (String badWord : badWordList) {
            List<Integer> indexes = findWord(content, badWord);
            for (Integer index : indexes) {
                ValidationResponseDto.BadWord b = new ValidationResponseDto.BadWord(badWord, index, index + badWord.length() - 1);
                badWords.add(b);
            }
        }
        return badWords;
    }

    private List<Integer> findWord(String text, String word) {
        List<Integer> indexes = new ArrayList<>();
        String lowerCaseTextString = text.toLowerCase();
        String lowerCaseWord = word.toLowerCase();
        int wordLength = 0;

        int index = 0;
        while (index != -1) {
            index = lowerCaseTextString.indexOf(lowerCaseWord, index + wordLength);
            if (index != -1) {
                indexes.add(index);
            }
            wordLength = lowerCaseWord.length();
        }
        return indexes;
    }
}
