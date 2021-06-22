package com.xuecheng.manage_cms.controller;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFSTest extends TestCase {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    //存文件
    @Test
    public void testGridFsTemplate() throws FileNotFoundException {
        File file = new File("C:/Develop/index_banner.ftl");
        FileInputStream fis = new FileInputStream(file);
        ObjectId objectId = gridFsTemplate.store(fis, "index_banner.ftl");
        System.out.println(objectId);

    }

    @Test
    public void queryFile() throws IOException {

        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("60d1ae17992f4a530888f0de")));
        //打开一个文件下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getId());

        GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
        //从流中取数据
        String content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");

        System.out.println(content);

    }
    @Test
    public void deleteFile(){
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is("60d1ae17992f4a530888f0de")));
    }


}