package open_mission.tdd.auth.entity

import jakarta.persistence.*
import open_mission.tdd.common.base.BaseEntity

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var password: String,
) : BaseEntity() {

    companion object {
        fun of(email: String, encodedPassword: String): User =
            User(
                email = email,
                password = encodedPassword,
            )
    }
}
