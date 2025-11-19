package open_mission.tdd.common.response

import com.fasterxml.jackson.annotation.JsonInclude
import open_mission.tdd.common.error.ErrorCode

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val success: Boolean,
    val code: String,
    val message: String? = null,
    val data: T? = null,
) {
    companion object {
        fun <T> success(
            data: T,
            code: String = "SUCCESS",
            message: String? = null,
        ): ApiResponse<T> =
            ApiResponse(
                true,
                code,
                message,
                data
            )

        fun <T> fail(
            errorCode: ErrorCode,
            data: T? = null,
        ): ApiResponse<T> = ApiResponse(
            false,
            errorCode.code,
            errorCode.message,
            data
        )
    }
}
