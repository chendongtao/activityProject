package com.cdt.activiti;

import org.activiti.engine.*;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
public class TestProcessVariables {
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();



    @Test
    /**
     * 启动流程，同时设置变量,act_ru_variable表新增变量数据
     * 如果变量为对象且序列化后，表act_ge_bytearray新增数据
     */
    public void setProcessVariable(){
        RuntimeService runtimeService = processEngine.getRuntimeService();
           String businessKey ="1";
        Map variabls =new HashMap();
        variabls.put("请假人","张三");
        variabls.put("请假天数",5);
        variabls.put("user",new Users(1,"小明"));
            runtimeService.startProcessInstanceByKey("leave",businessKey,variabls);
    }

    @Test
    /**
     * 通过RuntimeService设置流程变量
     */
    public void setProcessVariable02(){
        String executionId ="30001";
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        runtimeService.setVariable(executionId,"请假原因","武汉肺炎");
        String executionId02="22501";
        Map variable =new HashMap();
        variable.put("请假时间",new Date());
        runtimeService.setVariables(executionId02,variable);
    }

    @Test
    /**
     * 通过TaskService设置变量
     */
    public void testSetProcessVariable03(){
        TaskService taskService = this.processEngine.getTaskService();
        String taskId ="30009";
        Map map
                =new HashMap();
        map.put("请假力度",12);
        taskService.setVariablesLocal(taskId,map);
    }

    @Test
    /**
     * 获取流程变量
     */
    public void testGetProcessVariable04() {
        RuntimeService runtimeService =this.processEngine.getRuntimeService();
        String executionId ="30001";
        Users users = (Users)runtimeService.getVariable(executionId, "user");
        System.out.println(users);
    }






}
