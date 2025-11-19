package open_mission.tdd.common.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: String,
    val message: String,
    val status: HttpStatus,
) {
    // 401 Unauthorized
    INVALID_LOGIN("4011", "이메일 또는 비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED),

    // 409 Conflict
    DUPLICATED_USER_EMAIL("4091", "이미 가입된 이메일입니다.", HttpStatus.CONFLICT),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR("5000", "서버 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NON_PERSISTED_USER_ID("5001", "저장되지 않은 사용자입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NON_PERSISTED_USER_CREATED_AT("5002", "저장되지 않은 사용자입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
}
