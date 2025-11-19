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
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@Transactional
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
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
        TODO("Not yet implemented")
    }
}
