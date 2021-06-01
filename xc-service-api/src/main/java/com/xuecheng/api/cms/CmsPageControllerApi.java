package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

public interface CmsPageControllerApi {
    //分页查询所有页面信息
    @ApiOperation("根据条件查询所有页面")
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    //根据id查询某一页面信息
    @ApiOperation("根据id查询页面")
    public CmsPage findByPageId(String pageId);

    //新增界面
    @ApiOperation("添加页面")
    public CmsPageResult addPage(CmsPage cmsPage);

    //修改页面信息
    @ApiOperation("修改页面信息")
    public CmsPageResult update(String pageId,CmsPage cmsPage);

    @ApiOperation("通过ID删除页面")
    public ResponseResult delete(String id);

}
