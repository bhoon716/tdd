package open_mission.tdd.security.jwt

import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import open_mission.tdd.auth.repository.UserRepository
import open_mission.tdd.common.error.CustomException
import open_mission.tdd.common.error.ErrorCode
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var accessToken = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (accessToken == null || !accessToken.startsWith("Bearer")) {
            filterChain.doFilter(request, response)
            return
        }

        accessToken = accessToken.substring("Bearer ".length)

        try {
            jwtTokenProvider.isValid(accessToken)
        } catch (_: JwtException) {
            throw CustomException(ErrorCode.INVALID_TOKEN)
        }

        val userId = jwtTokenProvider.getUserIdFromToken(accessToken)
        val user = userRepository.findById(userId)
            .orElseThrow { CustomException(ErrorCode.INVALID_TOKEN) }

        val authentication = UsernamePasswordAuthenticationToken(user.id, null, emptyList())

        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }
}
