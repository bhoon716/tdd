package open_mission.tdd.security.jwt

import io.mockk.every
import io.mockk.mockk
import open_mission.tdd.auth.entity.User
import open_mission.tdd.auth.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

class JwtFilterTest {

    @DisplayName("유효한 액세스 토큰 테스트")
    @Test
    fun validAccessTokenTest() {
        // given
        val token = "valid"
        val userId = 1L
        val user = User(userId, "email@test.com", "encodedPassword")

        val request = MockHttpServletRequest().apply {
            addHeader(HttpHeaders.AUTHORIZATION, "Bearer $token")
        }
        val response = MockHttpServletResponse()
        val filterChain = MockFilterChain()

        val jwtTokenProvider = mockk<JwtTokenProvider>()
        val userRepository = mockk<UserRepository>()
        val jwtFilter = JwtFilter(jwtTokenProvider, userRepository)

        every { jwtTokenProvider.isValid(token) } returns true
        every { jwtTokenProvider.getUserIdFromToken(token)} returns userId
        every { userRepository.findById(userId) } returns Optional.ofNullable(user)

        // when
        jwtFilter.doFilter(request, response, filterChain)

        // then
        val auth = SecurityContextHolder.getContext().authentication

        assertThat(auth).isNotNull
        assertThat(auth.principal).isEqualTo(userId)
        assertThat(auth.isAuthenticated).isTrue
    }
}
