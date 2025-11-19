package open_mission.tdd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class TddApplication

fun main(args: Array<String>) {
	runApplication<TddApplication>(*args)
}
