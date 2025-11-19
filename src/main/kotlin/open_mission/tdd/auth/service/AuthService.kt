package open_mission.tdd.auth.service

import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import open_mission.tdd.auth.repository.UserRepository
import open_mission.tdd.auth.request.SignupRequest
import open_mission.tdd.auth.response.SignupResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
@RequiredArgsConstructor
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun signup(signupRequest: SignupRequest): SignupResponse {
        return SignupResponse(1L, "", LocalDateTime.now())
    }
}