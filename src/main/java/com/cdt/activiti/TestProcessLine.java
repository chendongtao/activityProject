package com.cdt.activiti;

import org.activiti.engine.*;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestProcessLine {
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    @Test
    /**
     * 部署流程
     */
    public void testDeploymentProcess(){
        RepositoryService repositoryService = this.processEngine.getRepositoryService();
        repositoryService.createDeployment().name("报销流程").addClasspathResource("HelloActiviti.xml")
                .addClasspathResource("HelloActiviti.png").deploy();

    }

    @Test
    /**
     * 启动流程
     */
    public void testStartProcerss(){
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        runtimeService.startProcessInstanceByKey("报销");
    }

    @Test
    /**
     * 查询个人任务
     */
    public void queryTask(){
        TaskService taskService = this.processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().taskAssignee("张三").list();
        if (list!=null&&!list.isEmpty()){
            for (Task task : list) {
                String id = task.getId();
                String processDefinitionId = task.getProcessDefinitionId();
                System.out.println("任务id:"+id);
                System.out.println("流程定义id:"+processDefinitionId);
            }
        }
    }

    @Test
    /**
     * 完成任务-张三的 任务
     */
    public void testCompleteTask(){
        TaskService taskService = this.processEngine.getTaskService();
        String taskId ="5004";
        taskService.complete(taskId);
    }
    @Test
    /**
     * 完成任务-李四的 任务
     */
    public void testCompleteTask2(){
        TaskService taskService = this.processEngine.getTaskService();
        String taskId ="7502";
        Map variables =new HashMap();
        variables.put("outcome","重要");
        taskService.complete(taskId,variables);
    }

    @Test
    /**
     * 完成任务-王五最后的 任务
     */
    public void testCompleteTask03(){
        TaskService taskService = this.processEngine.getTaskService();
        String taskId ="10003";
        taskService.complete(taskId);
    }
}
