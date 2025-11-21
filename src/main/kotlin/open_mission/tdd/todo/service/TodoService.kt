package open_mission.tdd.todo.service

import open_mission.tdd.auth.repository.UserRepository
import open_mission.tdd.todo.repository.TodoRepository
import open_mission.tdd.todo.request.CreateTodoRequest
import open_mission.tdd.todo.request.UpdateTodoRequest
import open_mission.tdd.todo.response.TodoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TodoService(
    private val userRepository: UserRepository,
    private val todoRepository: TodoRepository
) {

    fun createTodo(userId: Long, request: CreateTodoRequest) : TodoResponse {
        TODO()
    }

    @Transactional(readOnly = true)
    fun getTodos(userId: Long): List<TodoResponse> {
        TODO()
    }

    @Transactional(readOnly = true)
    fun getTodo(userId: Long, todoId: Long) : TodoResponse {
        TODO()
    }

    fun updateTodo(userId: Long, request: UpdateTodoRequest) : TodoResponse {
        TODO()
    }

    fun deleteTodo(userId: Long, todoId: Long) {
        TODO()
    }
}