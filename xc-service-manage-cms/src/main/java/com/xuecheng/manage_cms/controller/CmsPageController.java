package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.CmsPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/cms")
@Api(value = "cms页面管理接口", description = "cms页面管理接口，提供页面的增、删、改、查")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private CmsPageService cmsPageService;

    @Override
    @GetMapping("/list/{page}/{size}")
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页 码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页记录数", required = true, paramType = "path", dataType = "int")})
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {

        return cmsPageService.findList(page, size, queryPageRequest);
    }


    @Override
    @GetMapping("/{pageId}")
    @ApiOperation("根据Id查询页面")   //页面id是mongodb数据库自动生成的
    public CmsPage findByPageId(@PathVariable("pageId") String pageId) {
        return cmsPageService.findByPageId(pageId);
    }

    @Override
    @PostMapping("/add")
    @ApiOperation("添加页面")
    public CmsPageResult addPage(@RequestBody CmsPage cmsPage) {
        return cmsPageService.add(cmsPage);
    }

    @Override
    @PostMapping("/edit/{pageId}")
    @ApiOperation("修改页面")
    public CmsPageResult update(@PathVariable("pageId") String pageId,@RequestBody CmsPage cmsPage) {

        return cmsPageService.update(pageId,cmsPage);
    }

    @Override
    @DeleteMapping("/del/{pageId}")
    @ApiOperation("删除页面")
    public ResponseResult delete(@PathVariable("pageId") String pageId) {
        return cmsPageService.delete(pageId);
    }

}
