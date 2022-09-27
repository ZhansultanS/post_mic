package com.zhans.post.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhans.post.dto.ValidationRequestDto;
import com.zhans.post.dto.ValidationResponseDto;
import com.zhans.post.model.Post;
import com.zhans.post.model.PostStatus;
import com.zhans.post.repository.PostRepository;
import com.zhans.post.websocket.WebsocketMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService {

    @Value("${app.validation.kafka.topic.request}")
    private String validationTopic;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final PostRepository postRepository;
    private final WebsocketMessageHandler websocketMessageHandler;

    @SneakyThrows
    public void validatePost(Post post) {
        ValidationRequestDto validationRequest = new ValidationRequestDto(post.getId(), post.getContent());
        String validationRequestString = objectMapper.writeValueAsString(validationRequest);
        kafkaTemplate.send(validationTopic, validationRequestString);
    }

    @SneakyThrows
    @KafkaListener(topics = "${app.validation.kafka.topic.response}", groupId = "group_id")
    private void listenValidationResponse(String validationResponseString) {
        ValidationResponseDto validationResponse = objectMapper.readValue(validationResponseString, ValidationResponseDto.class);
        Post post = postRepository.findById(validationResponse.getPostId()).orElseThrow(() -> new RuntimeException("Post not found"));
        if (validationResponse.getIsValid())
            post.setStatus(PostStatus.ACTIVE);
        else {
            post.setStatus(PostStatus.FAILED);
            validationResponse.getBadWords().forEach(System.out::println);
        }
        postRepository.save(post);
        websocketMessageHandler.sendMessageToUser(
                post.getAuthorId(),
                "Status of post with id=" + post.getId() + " has changed to " + post.getStatus()
        );

    }
}
