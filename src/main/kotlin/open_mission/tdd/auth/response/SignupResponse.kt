package open_mission.tdd.auth.response

import java.time.LocalDateTime

data class SignupResponse(val id: Long, val email: String, val createdAt: LocalDateTime)
