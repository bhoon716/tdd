package open_mission.tdd.todo.response

import open_mission.tdd.todo.entity.Todo
import open_mission.tdd.todo.entity.TodoStatus

data class TodoResponse(
    val id: Long,
    val title: String,
    val content: String,
    val status: TodoStatus
) {
    companion object {
        fun of(todo: Todo): TodoResponse {
            return TodoResponse(
                id = todo.id!!,
                title = todo.title,
                content = todo.content,
                status = todo.status,
            )
        }
    }
}
