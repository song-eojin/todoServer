package org.practice.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.practice.model.TaskDto;
import org.practice.service.TaskService;
import org.practice.web.vo.TaskRequestVo;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @Test
    void createTask() throws Exception {
        TaskRequestVo sampleReq = new TaskRequestVo();

        sampleReq.setTitle("test");
        sampleReq.setDescription("test description");

        TaskDto sampleTask = TaskDto.builder()
                .id(1L)
                .title("test")
                .description("test description")
                .build();

        when(taskService.add(eq(sampleReq.getTitle()),
                        eq(sampleReq.getDescription()),
                        eq(sampleReq.getDueDate())))
                .thenReturn(sampleTask);

        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\": 1, \"title\": \"test\", \"description\": \"test description\"}"))
                .andReturn();
    }
}