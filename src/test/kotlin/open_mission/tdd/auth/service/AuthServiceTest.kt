package open_mission.tdd.auth.service

import io.mockk.every
import io.mockk.mockk
import open_mission.tdd.auth.entity.User
import open_mission.tdd.auth.repository.UserRepository
import open_mission.tdd.auth.request.SignupRequest
import open_mission.tdd.common.error.CustomException
import open_mission.tdd.common.error.ErrorCode
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder


class AuthServiceTest {

    val userRepository: UserRepository = mockk()
    val passwordEncoder: PasswordEncoder = mockk()
    val authService = AuthService(userRepository, passwordEncoder)

    @DisplayName("회원가입 테스트")
    @Test
    fun signupTest() {
        // given
        val request = SignupRequest("email@test.com", "password123")

        every { userRepository.existsByEmail("email@test.com") } returns false
        every { passwordEncoder.encode("password123") } returns "encodedPassword"
        every { userRepository.save(any()) } returns User(1L, "email@test.com", "encodedPassword")

        // when
        val response = authService.signup(request)

        // then
        assertThat(response.id).isEqualTo(1L)
        assertThat(response.email).isEqualTo("email@test.com")
    }

    @DisplayName("회원가입 실패 테스트 - 중복 이메일")
    @Test
    fun duplicatedEmailTest() {
        // given
        val request = SignupRequest("email@test.com", "password123")

        every { userRepository.existsByEmail("email@test.com") } returns true
//        every { passwordEncoder.encode("password123") } returns "encodedPassword"
//        every { userRepository.save(any()) } returns User(1L, "email@test.com", "encodedPassword")

        // when & then
        assertThatThrownBy{ authService.signup(request)}
            .isExactlyInstanceOf(CustomException::class.java)
            .hasMessage(ErrorCode.DUPLICATED_USER_EMAIL.message)
    }
}
