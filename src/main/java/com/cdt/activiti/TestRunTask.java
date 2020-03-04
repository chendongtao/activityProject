package com.cdt.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TestRunTask  {
    private  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    private TaskService taskService =this.processEngine.getTaskService();

    @Test
    /**
     * 查询我的个人任务 act_ru_task
     */
    public void queryMyTask(){
        List<Task> taskList = taskService.createTaskQuery().taskAssignee("张三")
                .orderByTaskCreateTime().desc().list();
        if (taskList!=null&&!taskList.isEmpty()){
            for (Task task : taskList) {
                String id = task.getId();//任务ID
                String name = task.getName();//任务名次
                String assignee = task.getAssignee();//任务执行人
                Date createTime = task.getCreateTime();//任务创建时间
                System.out.println("id="+id+",name="+name+",assignee="+assignee+",createTime="+createTime);
            }
        }
    }


    @Test
    /**
     * 完成任务，当完成任务后，act_ru_task删除完成任务，新增下个节点代办任务
     */
    public void completeTask(){
        String taskId ="22504";
        taskService.complete(taskId);
    }

    @Test
    /**
     *根据流程实例ID查询流程状态，act_ru_execution表无对应数据，则流程结束，反之，未结束
     * 目的：更新业务表状态,如果流程结束，则更新对应的业务表状态
     */
    public void queryProcessState(){
        String processInstanceId ="22501";
        RuntimeService runtimeService =this.processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.
                createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if (processInstance==null){
            System.out.println("流程结束");
        }else {
            System.out.println("流程未结束");
        }

    }
}
