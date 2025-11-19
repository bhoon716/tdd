package open_mission.tdd.common.error

class CustomException(val errorCode: ErrorCode, var detail: String?) : RuntimeException()