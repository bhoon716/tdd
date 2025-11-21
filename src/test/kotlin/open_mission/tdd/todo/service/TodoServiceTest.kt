package open_mission.tdd.todo.service

import io.mockk.every
import io.mockk.mockk
import open_mission.tdd.auth.entity.User
import open_mission.tdd.auth.repository.UserRepository
import open_mission.tdd.todo.entity.Todo
import open_mission.tdd.todo.entity.TodoStatus
import open_mission.tdd.todo.repository.TodoRepository
import open_mission.tdd.todo.request.CreateTodoRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Optional

class TodoServiceTest {

    val todoRepository: TodoRepository = mockk()
    val userRepository: UserRepository = mockk()
    val todoService = TodoService(userRepository, todoRepository)

    @DisplayName("투두 생성 테스트")
    @Test
    fun createTest() {
        // given
        val request = CreateTodoRequest("todo title", "todo content")
        val user = User(1L, "email@test.com", "encodedPassword")
        every { userRepository.findById(1L) } returns Optional.ofNullable(user)
        every { todoRepository.save<Todo>(any()) } returns Todo(1L, user, "todo title", "todo content", TodoStatus.DONE)

        // when
        val response = todoService.createTodo(1L, request)

        // then
        assertThat(response.id).isEqualTo(1L)
        assertThat(response.title).isEqualTo("todo title")
        assertThat(response.content).isEqualTo("todo content")
        assertThat(response.status).isEqualTo(TodoStatus.DONE)
    }

    @DisplayName("투두 전체 조회 테스트")
    @Test
    fun getTodosTest() {
        // given
        val user = User(1L, "email@test.com", "encodedPassword")
        val todo1 = Todo.of(user, "todo1", "content1")
        val todo2 = Todo.of(user, "todo2", "content2")
        val todo3 = Todo.of(user, "todo3", "content3")
        every { todoRepository.findAllByUserId(1L) } returns listOf(todo1, todo2, todo3)

        // when
        val todos = todoService.getTodos(1L)

        // then
        assertThat(todos.size).isEqualTo(3)
        assertThat(todos[1].title).isEqualTo("todo1")
        assertThat(todos[2].title).isEqualTo("todo2")
        assertThat(todos[3].title).isEqualTo("todo3")
    }
}
