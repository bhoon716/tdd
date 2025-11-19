package open_mission.tdd.security.jwt

import org.springframework.stereotype.Component

@Component
class JwtTokenProvider {

    fun generateAccess(): String {
        return "access"
    }

    fun generateRefresh(): String {
        return "refresh"
    }
}