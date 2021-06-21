package com.xuecheng.test.freemarker;

import com.xuecheng.test.freemarker.model.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FreemarkerTest {

    //测试静态化,基于ftl模板文件生成html文件
    @Test
    public void testGenerateHtml() throws IOException, TemplateException {
        //定义配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
       //得到classPath的路径
        String classPath = this.getClass().getResource("/").getPath();   // "/"对应当前的resources目录
        //定义模板路径
        configuration.setDirectoryForTemplateLoading(new File(classPath+"/templates/"));
        //定义数据模型
        Template template = configuration.getTemplate("test1.ftl");
        Map map = getMap();
        map.put("name","杨炳辉");
        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        System.out.println(content);


    }


    @Test
    //基于模板字符串生成html
    public void generateHtmlByString() throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.getVersion());
        //定义模板字符串
        String templateString="" + "<html>\n" + " <head></head>\n" + " <body>\n" + " 名称：${name}\n" + " </body>\n" + "</html>";
        //定义模板加载器
        StringTemplateLoader templateLoader  =new StringTemplateLoader();
        templateLoader.putTemplate("template",templateString);
        configuration.setTemplateLoader(templateLoader);
        //获取模板
        Template template = configuration.getTemplate("template");

        Map map = new HashMap();
        map.put("name","杨炳辉");
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        InputStream inputStream = IOUtils.toInputStream(content);
        FileOutputStream fos = new FileOutputStream(new File("C:/test1.html"));
        IOUtils.copy(inputStream,fos);

        inputStream.close();
        fos.close();


    }



    public  Map getMap(){
        Map map = new HashMap();
        //初始化学生1
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());
        //初始化学生2
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19); //
        stu2.setBirthday(new Date());
        //初始化学生朋友列表
        List<Student> friends = new ArrayList<>();
        friends.add(stu1);
        stu2.setFriends(friends);
        stu2.setBestFriend(stu1);
        //初始化学生列表
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);

        //向数据模型放数据
        map.put("stus", stus);

        //准备map数据
        HashMap<String, Student> stuMap = new HashMap<>();
        stuMap.put("stu1", stu1);
        stuMap.put("stu2", stu2);
        //向数据模型放数据
        map.put("stu1",stu1);
        //向数据模型放数据
        map.put("stuMap",stuMap);
        return map;
    }


}
