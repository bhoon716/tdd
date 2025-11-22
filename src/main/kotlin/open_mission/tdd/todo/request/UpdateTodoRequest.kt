package open_mission.tdd.todo.request

import open_mission.tdd.todo.entity.TodoStatus

data class UpdateTodoRequest(val title: String, val content: String, val status: TodoStatus)
