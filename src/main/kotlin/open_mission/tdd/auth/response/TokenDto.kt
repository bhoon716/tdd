package open_mission.tdd.auth.response

data class TokenDto(
    val accessToken: String,
    val refreshToken: String
)
