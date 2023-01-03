package com.piaskowy.urlshortenerbackend.user.service;

import com.piaskowy.urlshortenerbackend.user.exception.UserAlreadyExistsException;
import com.piaskowy.urlshortenerbackend.user.model.entity.User;
import com.piaskowy.urlshortenerbackend.user.model.request.RegisterRequest;
import com.piaskowy.urlshortenerbackend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
@ExtendWith(value = MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userServiceUnderTests;

    @Test
    void register_shouldThrowIfUserAlreadyExists() {
        //given
        RegisterRequest registerRequest = RegisterRequest
                .builder()
                .email("test@test.com")
                .password("12345678")
                .name("name")
                .lastName("lastname")
                .build();
        given(userRepository.findByEmail(any())).willReturn(Optional.of(new User()));
        //when
        //then
        assertThatThrownBy(() -> userServiceUnderTests.registerUser(registerRequest))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("User with email: " + registerRequest.getEmail() + " already exists");
    }

    @Test
    void register_shouldReturnRegisteredUser() {
        //given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("test@test.com")
                .password("12345678")
                .name("name")
                .lastName("lastname")
                .build();

        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .password("hashedPassword")
                .name("name")
                .lastName("lastname")
                .build();

        given(userRepository.findByEmail(any())).willReturn(Optional.empty());
        given(userRepository.saveAndFlush(any())).willReturn(user);
        //when
        userServiceUnderTests.registerUser(registerRequest);
        //then

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).saveAndFlush(userArgumentCaptor.capture());

        final User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(capturedUser.getName()).isEqualTo(user.getName());
        assertThat(capturedUser.getLastName()).isEqualTo(user.getLastName());
    }

}