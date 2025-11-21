package open_mission.tdd.todo.response

import open_mission.tdd.todo.entity.TodoStatus

data class TodoResponse(
    val id: Long,
    val title: String,
    val content: String,
    val status: TodoStatus
)