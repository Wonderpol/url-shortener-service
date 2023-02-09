package com.piaskowy.urlshortenerbackend.auth.user.service;

import com.piaskowy.urlshortenerbackend.auth.model.request.RegisterRequest;
import com.piaskowy.urlshortenerbackend.user.exception.UserAlreadyExistsException;
import com.piaskowy.urlshortenerbackend.user.model.entity.User;
import com.piaskowy.urlshortenerbackend.user.repository.UserRepository;
import com.piaskowy.urlshortenerbackend.user.service.UserRegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserRegistrationServiceTest {

    @InjectMocks
    UserRegistrationService userRegistrationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void test_signUpNewUser_shouldThrow_UserAlreadyExistsException() {
        //given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("test@test.com")
                .build();
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(new User()));
        //when
        //then
        assertThatThrownBy(() -> userRegistrationService.signUpNewUser(registerRequest))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("User with email: " + registerRequest.email() + " already exists");
    }

    @Test
    void test_signUpNewUser() {
        //given

        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("John")
                .lastName("Kowalski")
                .email("test@test.com")
                .password("password")
                .build();

        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(passwordEncoder.encode("password")).willReturn("encoded_password");
        //when
        userRegistrationService.signUpNewUser(registerRequest);
        //then
        final ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).saveAndFlush(userArgumentCaptor.capture());

        final User capturedValue = userArgumentCaptor.getValue();

        assertThat(registerRequest.email()).isEqualTo(capturedValue.getEmail());
        assertThat("encoded_password").isEqualTo(capturedValue.getPassword());
    }

}