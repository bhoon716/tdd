package open_mission.tdd.auth.response

import open_mission.tdd.auth.entity.User
import open_mission.tdd.common.error.CustomException
import open_mission.tdd.common.error.ErrorCode
import java.time.LocalDateTime

data class SignupResponse(
    val id: Long,
    val email: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(user: User): SignupResponse =
            SignupResponse(
                id = user.id
                    ?: throw CustomException(ErrorCode.NON_PERSISTED_USER_ID),
                email = user.email,
                createdAt = user.createdAt
                    ?: throw CustomException(ErrorCode.NON_PERSISTED_USER_CREATED_AT),
            )
    }
}
