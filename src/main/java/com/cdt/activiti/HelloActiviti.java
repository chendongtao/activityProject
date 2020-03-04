package com.cdt.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

public class HelloActiviti {

    //获取流程引擎
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    @Test
    /**
     * 初始化数据库方式一
     */
    public void createTables() throws Exception {
        ProcessEngineConfiguration config = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        config.setJdbcDriver("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?createDatabaseIfNotExist=true");
        config.setJdbcUsername("root");
        config.setJdbcPassword("123456");
        config.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        ProcessEngine engine = config.buildProcessEngine();
        System.out.println(engine);
    }
    /**
     * 方式3：缺省的方式，需要保证配置文件的名称为activiti.cfg.xml
     *
     * @throws Exception
     */
    @Test
    public void testdefault() throws Exception {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = engine.getRuntimeService();
        System.out.println(engine);
    }
    //部署流程定义
    @Test
    /**
     * act_re_* act_ge_bytearray(存储bpm文件和png图片)表增加数据
     */
    public void deployProcess(){
        RepositoryService repositoryService = this.processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().name("leave002")
                .addClasspathResource("HelloActiviti.png")
                .addClasspathResource("HelloActiviti.xml").deploy();
        System.out.println("部署成功:流程部署ID："+deploy.getId()+"部署名称 ："+deploy.getName());
    }

    @Test
    public void deployProcess02(){
        InputStream inputStream =this.getClass().getResourceAsStream("/HelloActiviti.zip");
        RepositoryService repositoryService = this.processEngine.getRepositoryService();
        ZipInputStream zipInputStream =new ZipInputStream(inputStream);
        repositoryService.createDeployment().name("对公评级审核").addZipInputStream(zipInputStream)
        .deploy();
        System.out.println("通过ZIP方式部署成功");
    }

    @Test
    /**
     * 启动流程，act_ru_*表增加数据
     */
    public void startProcess(){
        RuntimeService runtimeService = this.processEngine.getRuntimeService();
        //表act_re_procdef的主键id
//        String processInstanceId ="Hello:1:4";
//        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processInstanceId);
        //也可以通过获取key来启动,同一个流程定义，如果key相同，根据version启动最新版本的号的流程定义
        String key ="Hello";
        runtimeService.startProcessInstanceByKey(key);
        System.out.println("流程启动成功");
    }


    @Test
    /**
     * 查询执行人任务,taskService查询表act_ru_task
     */
    public void queryTask(){
        TaskService taskService = this.processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery().taskAssignee("李四").list();
        for (Task task : list) {
            System.out.println(task.getId());
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getExecutionId());
            System.out.println(task.getName());
            System.out.println(task.getAssignee());

            System.out.println("-----------------------");
        }

    }

    @Test
    /**
     * 执行人完成指定任务
     */
    public void completeTask(){
        TaskService taskService = this.processEngine.getTaskService();
        String taskId ="7502";
        taskService.complete(taskId);
        System.out.println("执行任务完成");

    }
}
