package open_mission.tdd.todo.entity

import jakarta.persistence.*
import open_mission.tdd.auth.entity.User
import open_mission.tdd.common.base.BaseEntity

@Entity
@Table(name = "todos")
class Todo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false, length = 1000)
    var content: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TodoStatus = TodoStatus.THINK
) : BaseEntity() {

    fun update(title: String, content: String, status: TodoStatus) {
        this.title = title
        this.content = content
        this.status = status
    }

    companion object {
        fun of(user: User, title: String, content: String): Todo = Todo(
            user = user,
            title = title,
            content = content
        )
    }
}
