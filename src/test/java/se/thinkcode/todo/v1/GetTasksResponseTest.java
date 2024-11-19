package se.thinkcode.todo.v1;

import org.junit.jupiter.api.Test;
import se.thinkcode.todo.Task;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GetTasksResponseTest {

    @Test
    void should_transform_from_model() {
        GetTasksResponse expected = new GetTasksResponse("Buy cat food");
        List<Task> tasks = List.of(new Task("Buy cat food"));

        List<GetTasksResponse> actual = GetTasksResponse.fromModel(tasks);

        assertThat(actual).containsExactly(expected);
    }
}