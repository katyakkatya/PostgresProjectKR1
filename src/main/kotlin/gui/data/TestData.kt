package data

import models.Subtask
import models.Task
import models.TaskStatus
import java.time.LocalDate

object TestData {
    val sampleTasks = listOf(
        Task(
            title = "Дизайн интерфейса",
            status = TaskStatus.IN_PROGRESS,
            subtasks = listOf(
                Subtask("Создать wireframes", true),
                Subtask("Разработать компоненты", false)
            ),
            relatedTasks = listOf(
                Task(
                    title = "Создать логотип",
                    status = TaskStatus.TODO,
                    progress = 0.0f
                ),
                Task(
                    title = "Подобрать цветовую схему",
                    status = TaskStatus.IN_PROGRESS,
                    progress = 0.7f
                )
            ),
            date = LocalDate.of(2024, 12, 20),
            progress = 0.5f
        ),
        Task(
            title = "Написать документацию",
            status = TaskStatus.TODO,
            relatedTasks = listOf(
                Task(
                    title = "Изучить требования",
                    status = TaskStatus.DONE,
                    progress = 1.0f
                )
            ),
            date = LocalDate.of(2024, 12, 15),
            progress = 0.0f
        ),
        Task(
            title = "Встреча с клиентом",
            status = TaskStatus.DONE,
            subtasks = listOf(
                Subtask("Подготовить презентацию", true),
                Subtask("Составить agenda", true),
                Subtask("Разослать приглашения", true)
            ),
            relatedTasks = listOf(
                Task(
                    title = "Подготовить коммерческое предложение",
                    status = TaskStatus.DONE,
                    progress = 1.0f
                )
            ),
            progress = 1.0f
        ),
        Task(
            title = "Тестирование приложения",
            status = TaskStatus.IN_PROGRESS,
            subtasks = listOf(
                Subtask("Юнит-тесты", false),
                Subtask("Интеграционные тесты", false),
                Subtask("UI тесты", false)
            ),
            relatedTasks = listOf(
                Task(
                    title = "Написать тестовые сценарии",
                    status = TaskStatus.DONE,
                    progress = 1.0f
                ),
                Task(
                    title = "Автоматизировать тестирование",
                    status = TaskStatus.IN_PROGRESS,
                    progress = 0.4f
                )
            ),
            progress = 0.2f
        ),
        Task(
            title = "Рефакторинг кода",
            status = TaskStatus.IN_PROGRESS,
            subtasks = listOf(
                Subtask("Анализ кодовой базы", true),
                Subtask("Оптимизация производительности", false)
            ),
            relatedTasks = listOf(
                Task(
                    title = "Изучить лучшие практики",
                    status = TaskStatus.DONE,
                    progress = 1.0f
                )
            ),
            progress = 0.3f
        )
    )

    val highPriorityTasks = sampleTasks.filter { it.status == TaskStatus.IN_PROGRESS }
    val completedTasks = sampleTasks.filter { it.status == TaskStatus.DONE }

    fun getTasksByStatus(status: TaskStatus) = sampleTasks.filter { it.status == status }
}