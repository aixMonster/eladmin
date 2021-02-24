/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.cms.tag.rest;

import io.github.perplexhub.rsql.RSQLQueryDslSupport;
import me.zhengjie.annotation.Log;
import me.zhengjie.cms.tag.domain.QQslTag;
import me.zhengjie.cms.tag.domain.QslTag;
import me.zhengjie.cms.tag.service.QslTagService;
import me.zhengjie.cms.tag.service.dto.QslTagQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author ray
* @date 2021-02-24
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "内容管理系统：标签管理管理")
@RequestMapping("/api/qslTag")
public class QslTagController {

    private final QslTagService qslTagService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('qslTag:list')")
    public void download(HttpServletResponse response, QslTagQueryCriteria criteria) throws IOException {
        qslTagService.download(qslTagService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询内容管理系统：标签管理")
    @ApiOperation("查询内容管理系统：标签管理")
    @PreAuthorize("@el.check('qslTag:list')")
    public ResponseEntity<Object> query(@RequestParam(required = false) String filter, Pageable pageable){
        QslTagQueryCriteria criteria = new QslTagQueryCriteria();
        return new ResponseEntity<>(qslTagService.queryAll(criteria, RSQLQueryDslSupport.toPredicate(filter, QQslTag.qslTag), pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增内容管理系统：标签管理")
    @ApiOperation("新增内容管理系统：标签管理")
    @PreAuthorize("@el.check('qslTag:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody QslTag resources){
        return new ResponseEntity<>(qslTagService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改内容管理系统：标签管理")
    @ApiOperation("修改内容管理系统：标签管理")
    @PreAuthorize("@el.check('qslTag:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody QslTag resources){
        qslTagService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除内容管理系统：标签管理")
    @ApiOperation("删除内容管理系统：标签管理")
    @PreAuthorize("@el.check('qslTag:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        qslTagService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
