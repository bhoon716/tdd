package open_mission.tdd.auth.repository

import open_mission.tdd.auth.entity.User
import open_mission.tdd.auth.request.SignupRequest
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository: JpaRepository<User, Long> {

    fun existsByEmail(email: String): Boolean
}