package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CmsPageService {

    @Autowired
    private CmsPageDao cmsPageDao;

    /**
     * 分页查询界面
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) {
        if (page <= 0) {
            page = 1;
        }
        page -= 1;
        if (size <= 0) {
            size = 5;
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        CmsPage cmsPage = new CmsPage();
        //设置查询模板
        if (queryPageRequest.getPageAliase() != null) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        if(queryPageRequest.getSiteId()!=null){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }

        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("pageAliase",
                ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example = Example.of(cmsPage, matcher);
        Page<CmsPage> all = cmsPageDao.findAll(example, pageRequest);
        List<CmsPage> pages = all.getContent();
        //封装结果
        QueryResult<CmsPage> results = new QueryResult<>();
        if (pages != null) {
            results.setList(pages);
            results.setTotal(all.getTotalElements());
            return new QueryResponseResult(CommonCode.SUCCESS, results);
        }

        return new QueryResponseResult(CommonCode.FAIL, null);

    }

    /**
     * 新增界面
     * @param one
     * @return
     */
    public CmsPageResult add(CmsPage one) {
        //先查询一下页面是否存在
        CmsPage cmsPage = cmsPageDao.findByPageNameAndSiteIdAndPageWebPath(one.getPageName(),
                one.getSiteId(),one.getPageWebPath());
        //处理页面已经存在
        if (cmsPage!=null){
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }

        if(cmsPage==null){
            one.setPageId(null);//添加页面主键由spring data 自动生成
            cmsPageDao.save(one);
            return new CmsPageResult(CommonCode.SUCCESS,one);
        }
        return new CmsPageResult(CommonCode.FAIL,cmsPage);

    }

    /**
     * 根据Id查询cms页面
     * @param pageId
     * @return
     */
    public CmsPage findByPageId(String pageId) {
        Optional<CmsPage> optional = cmsPageDao.findById(pageId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public CmsPageResult update(String pageId, CmsPage cmsPage) {
        CmsPage oldPage = this.findByPageId(pageId);
        if (oldPage!=null){
            oldPage.setTemplateId(cmsPage.getTemplateId()); //更新所属站点
            oldPage.setSiteId(cmsPage.getSiteId()); //更新页面别名
            oldPage.setPageAliase(cmsPage.getPageAliase()); //更新页面名称
            oldPage.setPageName(cmsPage.getPageName()); //更新访问路径
            oldPage.setPageWebPath(cmsPage.getPageWebPath()); //更新物理路径
            oldPage.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            CmsPage newPage = cmsPageDao.save(oldPage);
            return new CmsPageResult(CommonCode.SUCCESS,newPage);
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    /**
     * 根据id删除页面
     * @param id
     * @return
     */
    public ResponseResult delete(String id) {
        CmsPage delPage = this.findByPageId(id);
        if(delPage!=null){
            cmsPageDao.delete(delPage);
            return ResponseResult.SUCCESS();
        }

        return ResponseResult.FAIL();

    }
}
