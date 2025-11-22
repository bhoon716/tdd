package open_mission.tdd.common.error

class CustomException(
    val errorCode: ErrorCode,
    val detail: String? = null,
) : RuntimeException(
    detail?.let { "${errorCode.message} - $it" } ?: errorCode.message,
)
