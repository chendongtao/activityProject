package com.cdt.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.zip.ZipInputStream;

public class TestStartProcess {
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    private RuntimeService runtimeService =processEngine.getRuntimeService();

    /**
     * 部署流程
     */
    @Test
    public void deploymentProcess(){
        RepositoryService repositoryService = this.processEngine.getRepositoryService();
        repositoryService.createDeployment().name("测试启动流程")
                .addZipInputStream(new ZipInputStream(this.getClass().getResourceAsStream("/HelloActiviti.zip")))
                .deploy();
    }
    /**
     * 启动流程 表act_ru_execution、act_ru_task表生成实例数据
     */
    @Test
    public void startProcess(){
        String processDefinitionKey ="leave";
        String businessKey ="1";
        //通过流程定义key和业务key启动流程
        ProcessInstance processInstance = runtimeService.
                startProcessInstanceByKey(processDefinitionKey, businessKey);
        //获取流程实例ID
        String processDefinitionId = processInstance.getProcessDefinitionId();
        String name = processInstance.getName();
        System.out.println(processDefinitionId+",name="+name);
    }
}
