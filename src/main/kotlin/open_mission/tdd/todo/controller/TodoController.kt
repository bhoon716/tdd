package open_mission.tdd.todo.controller

import open_mission.tdd.common.response.ApiResponse
import open_mission.tdd.todo.request.CreateTodoRequest
import open_mission.tdd.todo.request.UpdateTodoRequest
import open_mission.tdd.todo.response.TodoResponse
import open_mission.tdd.todo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService,
) {

    @PostMapping
    fun createTodo(
        @RequestBody request: CreateTodoRequest,
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ApiResponse<TodoResponse>> {
        val response = todoService.createTodo(userId, request)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(response))
    }

    @GetMapping
    fun getTodos(
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ApiResponse<List<TodoResponse>>> {
        val response = todoService.getTodos(userId)
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @GetMapping("/{todoId}")
    fun getTodo(
        @PathVariable todoId: Long,
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ApiResponse<TodoResponse>> {
        val response = todoService.getTodo(userId, todoId)
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @PutMapping("/{todoId}")
    fun updateTodo(
        @PathVariable todoId: Long,
        @RequestBody request: UpdateTodoRequest,
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ApiResponse<TodoResponse>> {
        val response = todoService.updateTodo(userId, todoId, request)
        return ResponseEntity.ok(ApiResponse.success(response))
    }

    @DeleteMapping("/{todoId}")
    fun deleteTodo(
        @PathVariable todoId: Long,
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ApiResponse<Unit>> {
        todoService.deleteTodo(userId, todoId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(ApiResponse.success(Unit))
    }
}
