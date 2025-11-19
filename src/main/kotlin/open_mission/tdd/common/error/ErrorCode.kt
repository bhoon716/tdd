package open_mission.tdd.common.error

import org.springframework.http.HttpStatus

enum class ErrorCode(val code: String, val message: String, val status: HttpStatus) {

    // 409 Conflict
    DUPLICATED_USER_EMAIL("4091", "이미 가입된 이메일입니다.", HttpStatus.CONFLICT),
    ;
}