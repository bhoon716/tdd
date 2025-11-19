package open_mission.tdd.auth.service

import io.mockk.every
import io.mockk.mockk
import open_mission.tdd.auth.entity.User
import open_mission.tdd.auth.repository.UserRepository
import open_mission.tdd.auth.request.SignupRequest
import org.assertj.core.api.Assertions.assertThat
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

        every { userRepository.findByEmail("email@test.com") } returns null
        every { passwordEncoder.encode("password123") } returns "encodedPassword"
        every { userRepository.save(any()) } returns User(1L, "email@test.com", "encodedPassword")

        // when
        val response = authService.signup(request)

        // then
        assertThat(response.id).isEqualTo(1L)
        assertThat(response.email).isEqualTo("email@test.com")
    }
}