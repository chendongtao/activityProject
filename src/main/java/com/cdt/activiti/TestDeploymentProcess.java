package com.cdt.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;

import java.util.zip.ZipInputStream;

public class TestDeploymentProcess {

    private ProcessEngine processEngine =ProcessEngines.getDefaultProcessEngine();

    private RepositoryService repositoryService =this.processEngine.getRepositoryService();


    @Test
    /**
     * 部署流程定义,通过ZIPInputStream流部署
     */
    public void deploymentProcess(){
        RepositoryService repositoryService = this.processEngine.getRepositoryService();
        repositoryService.createDeployment().name("流程定义查询").
                addZipInputStream(new ZipInputStream(
                this.getClass().getResourceAsStream("/HelloActiviti.zip")))
        .deploy();
    }

    @Test
    /**
     * 查询流程部署信息
     */
    public void queryDeploymentProcessInfo(){
        String deploymentId ="1";
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        System.out.println(deployment.getName());
    }

    @Test
    /**
     * 删除流程部署
     */
    public void delDeploymentProcess(){
        String deploymentId ="7501";
        //如果流程定义已经部署，则删除报错
        repositoryService.deleteDeployment(deploymentId);
//        //如果流程定义已经部署，则删除所有相关表数据act_re_*,act_hi_*
//        repositoryService.deleteDeployment(deploymentId,true);
    }

}
