package com.qianjisan.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.core.Result;
import com.qianjisan.system.request.SysDictTypeQueryRequest;
import com.qianjisan.system.request.SysDictDataRequest;
import com.qianjisan.system.request.SysDictTypeRequest;
import com.qianjisan.core.PageVO;
import com.qianjisan.system.vo.SysDictDataVO;
import com.qianjisan.system.vo.SysDictTypeVO;
import com.qianjisan.system.mapper.SysDictDataMapper;
import com.qianjisan.system.service.ISysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 字典管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "字典管理", description = "Dict相关接口")
@RestController
@RequestMapping("/rbac/dict")
@RequiredArgsConstructor
@Slf4j
public class SysDictController {

    private final ISysDictService dictService;
    private final SysDictDataMapper dictDataMapper;

    // ==================== 字典类型管理 ====================

    @Operation(summary = "创建字典类型")
    @PostMapping("/type")
    public Result<Void> createType(@Valid @RequestBody SysDictTypeRequest request) {
        try {
            dictService.saveDictType(request);
            return Result.success();
        } catch (Exception e) {
            log.error("创建字典类型失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新字典类型")
    @PutMapping("/type/{id}")
    public Result<Void> updateType(@PathVariable Long id, @Valid @RequestBody SysDictTypeRequest request) {
        try {
            dictService.updateDictTypeById(id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新字典类型失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除字典类型")
    @DeleteMapping("/type/{id}")
    public Result<Void> deleteType(@PathVariable Long id) {
        try {
            dictService.removeById(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除字典类型失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询字典类型")
    @PostMapping("/type/page")
    public Result<PageVO<SysDictTypeVO>> pageDictType(@RequestBody SysDictTypeQueryRequest request) {
        try {
            Page<SysDictTypeVO> page = dictService.queryDictTypePage(request);
            PageVO<SysDictTypeVO> pageVO = new PageVO<>();
            pageVO.setRecords(page.getRecords());
            pageVO.setTotal(page.getTotal());
            pageVO.setCurrent(page.getCurrent());
            pageVO.setSize(page.getSize());
            pageVO.setPages(page.getPages());
            return Result.success(pageVO);
        } catch (Exception e) {
            log.error("分页查询字典类型失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    // ==================== 字典数据管理 ====================

    @Operation(summary = "创建字典数据")
    @PostMapping("/data")
    public Result<Void> createData(@Valid @RequestBody SysDictDataRequest request) {
        try {
            dictService.saveDictData(request);
            return Result.success();
        } catch (Exception e) {
            log.error("创建字典数据失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新字典数据")
    @PutMapping("/data/{id}")
    public Result<Void> updateData(@PathVariable Long id, @Valid @RequestBody SysDictDataRequest request) {
        try {
            dictService.updateDictDataById(id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新字典数据失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除字典数据")
    @DeleteMapping("/data/{id}")
    public Result<Void> deleteData(@PathVariable Long id) {
        try {
            dictDataMapper.deleteById(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除字典数据失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "查询字典数据列表")
    @GetMapping("/data/list/{dictTypeId}")
    public Result<List<SysDictDataVO>> listData(@PathVariable Long dictTypeId) {
        try {
            List<SysDictDataVO> list = dictService.getDictDataList(dictTypeId);
            return Result.success(list);
        } catch (Exception e) {
            log.error("查询字典数据列表失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据字典编码查询字典数据")
    @GetMapping("/data/code/{dictCode}")
    public Result<List<SysDictDataVO>> getDataByCode(@PathVariable String dictCode) {
        try {
            List<SysDictDataVO> list = dictService.getDictDataByCode(dictCode);
            return Result.success(list);
        } catch (Exception e) {
            log.error("根据字典编码查询字典数据失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
