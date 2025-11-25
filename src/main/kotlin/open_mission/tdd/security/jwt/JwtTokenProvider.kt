package open_mission.tdd.security.jwt

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
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
) {

    private val secretKey: SecretKey =
        Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

    fun generateAccess(id: Long, email: String): String {
        val jti = UUID.randomUUID().toString()
        val now = System.currentTimeMillis()
        val expMillis = now + ACCESS_TTL.toMillis()
        return generateToken(jti, id, email, "access", Date(now), Date(expMillis))
    }

    fun generateRefresh(id: Long, email: String): String {
        val jti = UUID.randomUUID().toString()
        val now = System.currentTimeMillis()
        val expMillis = now + REFRESH_TTL.toMillis()
        return generateToken(jti, id, email, "refresh", Date(now), Date(expMillis))
    }

    private fun generateToken(
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

    fun validate(token: String): Boolean {
        try {
            val claimsJws = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)

                val exp = claimsJws.payload.expiration
                if (exp.before(Date())) {
                    throw JwtException("Token expired")
            }

            return true
        } catch (e: JwtException) {
            throw e
        } catch (e: IllegalArgumentException) {
            throw JwtException("Invalid token", e)
        }
    }

    fun getUserIdFromToken(token: String): Long {
        try {
            val claimsJws = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)

            val subject = claimsJws.payload.subject
                ?: throw JwtException("Token subject is null")

            return subject.toLong()
        } catch (e: Exception) {
            throw JwtException("Failed to parse user id from token", e)
        }
    }

    companion object {
        val ACCESS_TTL: Duration = Duration.ofMinutes(30L)
        val REFRESH_TTL: Duration = Duration.ofDays(14L)
    }
}
