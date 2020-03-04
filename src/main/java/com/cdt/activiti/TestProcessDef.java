package com.cdt.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.zip.ZipInputStream;

public class TestProcessDef {
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    private RepositoryService repositoryService =this.processEngine.getRepositoryService();


    @Test
    /**
     * 再次部署流程定义，创建新的流程定义
     */
    public void deploymentProcess(){
        repositoryService.createDeployment().name("查询流程定义信息")
                .addZipInputStream(new ZipInputStream(this.getClass().getResourceAsStream("/HelloActiviti.zip")))
                .deploy();
    }


    @Test
    /**
     *  查询流程定义信息表
     */
    public void queryProcessDef(){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .orderByDeploymentId().desc().list();
        for (ProcessDefinition p : list) {
            String id = p.getId();
            String deploymentId = p.getDeploymentId();
            String key = p.getKey();
            String name = p.getName();
            String resourceName = p.getResourceName();
            System.out.println("id:"+id+",deploymentId="+deploymentId+",key="+key+",name="+name+",resourceName="+resourceName);
        }
    }

    @Test
    /**
     * 根据部署id获取流程定义图
     */
    public void getProcessDefPNG() throws IOException {
        String deploymentId ="7501";
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        String id = processDefinition.getId();
        String name = processDefinition.getDiagramResourceName();
        //根据流程定义主键ID获取流程图流
        InputStream in = repositoryService.getProcessDiagram(id);

        File file =new File("D:/"+name);
        FileOutputStream fileOutputStream =null;
        try {
            fileOutputStream =new FileOutputStream(file);
            int len =0;
            byte[] b =new byte[1024];
            while ((len=in.read(b))!=-1){
                try {
                    fileOutputStream.write(b,0,len);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            fileOutputStream.close();
            in.close();
        }
    }

    @Test
    /**
     * 获取最新版本的流程定义
     */
    public void getNewProcessDef(){
        String key ="Hello";
        List<ProcessDefinition> processDefinitions =
                repositoryService.createProcessDefinitionQuery()
                        .processDefinitionKey(key)
                        .orderByProcessDefinitionVersion().desc()
                        .listPage(0, 1);
        ProcessDefinition processDefinition = processDefinitions.get(0);
        int version =processDefinition.getVersion();
        System.out.println(version);
    }

    @Test
    /**
     * 根据key删除不同版本的流程定义
     */
    public void deleteSameProcessDefByKey(){
        List<ProcessDefinition> list =
                repositoryService.createProcessDefinitionQuery()
                        .processDefinitionKey("Hello").list();
        if (null!=list&&list.size()!=0){
            for (ProcessDefinition p : list) {
                String deploymentId = p.getDeploymentId();
                repositoryService.deleteDeployment(deploymentId,true);
            }
        }
    }
}
