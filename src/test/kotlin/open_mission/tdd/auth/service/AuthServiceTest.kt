package open_mission.tdd.auth.service

import io.mockk.every
import io.mockk.mockk
import open_mission.tdd.auth.entity.User
import open_mission.tdd.auth.repository.UserRepository
import open_mission.tdd.auth.request.LoginRequest
import open_mission.tdd.auth.request.SignupRequest
import open_mission.tdd.common.error.CustomException
import open_mission.tdd.common.error.ErrorCode
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

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

        val persistedUser = User(1L, "email@test.com", "encodedPassword").apply {
            createdAt = LocalDateTime.of(2025, 1, 1, 1, 0)
        }
        every { userRepository.save(any()) } returns persistedUser

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

        // when & then
        assertThatThrownBy { authService.signup(request) }
            .isExactlyInstanceOf(CustomException::class.java)
            .hasMessage(ErrorCode.DUPLICATED_USER_EMAIL.message)
    }

    @DisplayName("로그인 성공 테스트")
    @Test
    fun loginTest() {
        // given
        val email = "test@email.com"
        val password = "password123"
        val request = LoginRequest(email, password)

        // when
        val response = authService.login(request)

        // then
        assertThat(response.tokenType).isEqualTo("Bearer")
        assertThat(response.accessToken).isEqualTo("access")
        assertThat(response.refreshToken).isEqualTo("refresh")
        assertThat(response.expiresIn).isEqualTo(24*3600)
    }

    @DisplayName("로그인 실패 테스트")
    @Test
    fun loginFailTest() {
        // given
        val email = "test@email.com"
        val password = "password123"
        every { userRepository.findByEmail(email) } returns User(1L, "test@email.com", "encodedPassword")
        every { passwordEncoder.matches(any(), any()) } returns false

        // when & then
        assertThatThrownBy { authService.login(LoginRequest(email, password) )}
            .isExactlyInstanceOf(CustomException::class.java)
            .hasMessage(ErrorCode.INVALID_LOGIN.message)
    }
}
