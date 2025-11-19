package open_mission.tdd.auth.controller

import open_mission.tdd.auth.request.LoginRequest
import open_mission.tdd.auth.request.SignupRequest
import open_mission.tdd.auth.response.SignupResponse
import open_mission.tdd.auth.service.AuthService
import open_mission.tdd.common.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/signup")
    fun signup(
        @RequestBody request: SignupRequest
    ): ResponseEntity<ApiResponse<SignupResponse>> {
        val response = authService.signup(request)
        val location = URI.create("/api/users/${response.id}")
        return ResponseEntity.created(location).body(ApiResponse.success(response, "회원가입 성공"))
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest
    ) {
        val response = authService.login(request)
    }
}