package com.xiao.store.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiao.store.entity.query.BannerQuery;
import com.xiao.store.entity.ResultData;
import com.xiao.store.entity.XiaoBanner;
import com.xiao.store.service.IXiaoBannerService;
import com.xiao.store.util.IdUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jing
 * @since 2021-05-12
 */
@RestController
@RequestMapping("/xiaoBanner")
public class XiaoBannerController {

    @Resource
    private IXiaoBannerService iXiaoBannerService;

    @PostMapping("/addBanner")
    @ApiOperation("新增banner")
    public ResultData addBanner(@ApiParam("适宜摄入量（ai）")
                                @RequestBody XiaoBanner xiaoBanner) {
        String id = IdUtil.getId();
        xiaoBanner.setId(id);
        if (iXiaoBannerService.save(xiaoBanner)) {
            return ResultData.resultData(id);
        }
        return ResultData.returnFail("保存失败");
    }

    @Transactional
    @DeleteMapping("deleteBannerById")
    @ApiOperation(("根据id删除BannerAi"))
    @ApiImplicitParam(name = "id", value = "banner id")
    public ResultData deleteBannerById(String id) {
        if (iXiaoBannerService.removeById(id)) {
            return ResultData.returnMessage("删除成功");
        }
        return ResultData.returnFail("删除失败");
    }

    @PutMapping("updateBanner")
    @ApiOperation("修改Banner")
    public ResultData updateBanner(@ApiParam("Banner实体类带着id") @RequestBody XiaoBanner xiaoBanner) {
        if (iXiaoBannerService.updateById(xiaoBanner)) {
            return ResultData.returnMessage("更新成功");
        }
        return ResultData.returnFail("更新失败");
    }

    @GetMapping("/getBannerById")
    @ApiOperation(value = "根据 id  查询Banner", response = XiaoBanner.class)
    public ResultData getBannerById(String id) {
        return ResultData.resultData(iXiaoBannerService.getById(id));
    }

    @GetMapping("/getBannerTable")
    @ApiOperation(value = ("根据条件查询Banner列表"), response = XiaoBanner.class)
    public ResultData getBannerTable(@ApiParam("BannerAi查询条件类") BannerQuery bannerQuery) {
        Page<XiaoBanner> page = iXiaoBannerService.page(new Page<>(bannerQuery.getStart(), bannerQuery.getSize()), new QueryWrapper<>());
        return ResultData.resultData(page);
    }

}

