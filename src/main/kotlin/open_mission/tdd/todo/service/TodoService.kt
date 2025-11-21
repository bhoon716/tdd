package open_mission.tdd.todo.service

import open_mission.tdd.auth.repository.UserRepository
import open_mission.tdd.common.error.CustomException
import open_mission.tdd.common.error.ErrorCode
import open_mission.tdd.todo.entity.Todo
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

    fun createTodo(userId: Long, request: CreateTodoRequest): TodoResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { CustomException(ErrorCode.USER_NOT_FOUND, "userId=$userId") }

        val todo = Todo.of(user, request.title, request.content)
        val saved = todoRepository.save(todo)

        return TodoResponse.of(saved)
    }

    @Transactional(readOnly = true)
    fun getTodos(userId: Long): List<TodoResponse> {
        return todoRepository.findAllByUserId(userId)
            .map { todo -> TodoResponse.of(todo) }
    }

    @Transactional(readOnly = true)
    fun getTodo(userId: Long, todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdAndUserId(todoId, userId)
            .orElseThrow { CustomException(ErrorCode.TODO_NOT_FOUND, "todoId=$todoId") }
        return TodoResponse.of(todo)
    }

    fun updateTodo(userId: Long, todoId: Long, request: UpdateTodoRequest): TodoResponse {
        val todo = todoRepository.findByIdAndUserId(todoId, userId)
            .orElseThrow { CustomException(ErrorCode.TODO_NOT_FOUND, "todoId=$todoId") }

        todo.update(request.title, request.content, request.status)
        val saved = todoRepository.save(todo)

        return TodoResponse.of(saved)
    }

    fun deleteTodo(userId: Long, todoId: Long) {
        val todo = todoRepository.findByIdAndUserId(todoId, userId)
            .orElseThrow { CustomException(ErrorCode.TODO_NOT_FOUND, "todoId=$todoId") }

        todoRepository.delete(todo)
    }
}