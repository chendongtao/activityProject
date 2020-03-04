package com.cdt.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestHistoryService {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    @Test
    /**
     * 查询历史流程,历史活动节点、历史任务、历史变量
     */
    public void testHistoryService(){
        HistoryService historyService = this.processEngine.getHistoryService();
        //查询历史实例任务
        String deploymentId ="1";
        List<HistoricProcessInstance> list =
                historyService.createHistoricProcessInstanceQuery().deploymentId(deploymentId).list();
        //查询历史活动节点
        String activityId ="_2";
        List<HistoricActivityInstance> list1 = historyService.createHistoricActivityInstanceQuery().activityId(activityId).list();

        //查询历史任务
        String processInstanceId ="22501";
        historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();

        //查询历史变量
        String variableName ="请假天数";
        HistoricVariableInstance historicVariableInstance = historyService.createHistoricVariableInstanceQuery().variableName(variableName).singleResult();

    }
}
