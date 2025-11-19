package open_mission.tdd.common.error

import lombok.extern.slf4j.Slf4j
import open_mission.tdd.common.response.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<ApiResponse<String>> {
        val errorCode = exception.errorCode
        if (exception.detail == null) {
            logger.error("[ERROR] code=${errorCode.code}, message=${errorCode.message}, status=${errorCode.status}")
        } else {
            logger.error("[ERROR] code=${errorCode.code}, message=${errorCode.message}, status=${errorCode.status}, detail=${exception.detail}")
        }
        return ResponseEntity.status(errorCode.status).body(ApiResponse.fail(errorCode, exception.detail))
    }
}
