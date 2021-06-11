package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsPageDao;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageDaoTest {

    @Autowired
    private CmsPageDao cmsPageDao;

    /**
     * 测试分页查询所有
     */
    @Test
    public void findAll(){
       /* List<CmsPage> all = cmsPageDao.findAll();
        System.out.println(all);*/
        int page = 0;
        int size = 2;
        //条件匹配器
        ExampleMatcher matcher = ExampleMatcher.matching();
        //页面别名模糊查询，需要自定义字符串的匹配器实现模糊查询
        matcher = matcher.withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        CmsPage cmsPage = new CmsPage();
//        cmsPage.setPageName("index.html");
//        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setPageAliase("课程详情");
        Example<CmsPage> example = Example.of(cmsPage,matcher);
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageDao.findAll(example, pageable);
        System.out.println(all.getContent());

    }
    /**
     * 分页查询测试
     */
    @Test
    public void findCmsPage(){
        int page = 0;
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageDao.findAll(pageRequest);
        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }

    @Test
    public void testfindByPageNameAndSiteIdAndPageWebPath(){
        CmsPage cmsPage = cmsPageDao.findByPageNameAndSiteIdAndPageWebPath("index.html",
                "5a751fab6abb544e0d19ea1", "/index.html");
        System.out.println(cmsPage);
    }




}