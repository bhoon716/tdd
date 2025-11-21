package open_mission.tdd.todo.repository

import open_mission.tdd.todo.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long> {

    fun findAllByUserId(userId: Long): List<Todo>
}