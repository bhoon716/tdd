package open_mission.tdd.auth.service

import jakarta.transaction.Transactional
import open_mission.tdd.auth.entity.User
import open_mission.tdd.auth.repository.UserRepository
import open_mission.tdd.auth.request.LoginRequest
import open_mission.tdd.auth.request.SignupRequest
import open_mission.tdd.auth.response.LoginResponse
import open_mission.tdd.auth.response.SignupResponse
import open_mission.tdd.common.error.CustomException
import open_mission.tdd.common.error.ErrorCode
import open_mission.tdd.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@Transactional
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun signup(request: SignupRequest): SignupResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw CustomException(ErrorCode.DUPLICATED_USER_EMAIL)
        }

        val encodedPassword = passwordEncoder.encode(request.password)
        val user = User.of(request.email, encodedPassword)

        val saved = userRepository.save(user)

        return SignupResponse.from(saved)
    }

    fun login(request: LoginRequest): LoginResponse {
        val user = (userRepository.findByEmail(request.email)
            ?: throw CustomException(ErrorCode.INVALID_LOGIN))

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw CustomException(ErrorCode.INVALID_LOGIN)
        }

        val accessToken = jwtTokenProvider.generateAccess(user.id!!, user.email)
        val refreshToken = jwtTokenProvider.generateRefresh(user.id!!, user.email)
        val expiresIn = 24 * 3600L
        return LoginResponse(accessToken, refreshToken, "Bearer", expiresIn)
    }
}
