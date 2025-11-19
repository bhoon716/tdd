package open_mission.tdd.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import open_mission.tdd.auth.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${spring.jwt.secret}")
    private val secret: String,
    private val userRepository: UserRepository,
) {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

    fun generateAccess(id: Long, email: String): String {
        val jti = UUID.randomUUID().toString()
        val now = System.currentTimeMillis()
        val expMillis = now + ACCESS_TTL
        return generateToken(jti, id, email, "access", Date(), Date(expMillis))
    }

    fun generateRefresh(id: Long, email: String): String {
        val jti = UUID.randomUUID().toString()
        val now = System.currentTimeMillis()
        val expMillis = now + REFRESH_TTL
        return generateToken(jti, id, email, "refresh", Date(), Date(expMillis))
    }

    fun generateToken(
        jti: String,
        id: Long,
        email: String,
        type: String,
        issuedAt: Date,
        expiredAt: Date
    ): String {
        return Jwts.builder()
            .subject(id.toString())
            .id(jti)
            .claim("email", email)
            .claim("type", type)
            .issuedAt(issuedAt)
            .expiration(expiredAt)
            .signWith(secretKey)
            .compact()
    }

    companion object {
        val ACCESS_TTL = Duration.ofMinutes(30L).toMillis()
        val REFRESH_TTL = Duration.ofDays(14L).toMillis()
    }
}