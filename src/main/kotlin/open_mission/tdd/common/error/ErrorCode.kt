package open_mission.tdd.common.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: String,
    val message: String,
    val status: HttpStatus,
) {
    // 409 Conflict
    DUPLICATED_USER_EMAIL("4091", "이미 가입된 이메일입니다.", HttpStatus.CONFLICT),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR("5000", "서버 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
}
