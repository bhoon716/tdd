package open_mission.tdd.todo.repository

import open_mission.tdd.todo.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TodoRepository : JpaRepository<Todo, Long> {

    fun findAllByUserId(userId: Long): List<Todo>

    fun findByIdAndUserId(todoId: Long, userId: Long) : Optional<Todo>
}