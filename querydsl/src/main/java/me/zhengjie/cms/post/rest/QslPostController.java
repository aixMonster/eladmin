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
package me.zhengjie.cms.post.rest;

import io.github.perplexhub.rsql.RSQLQueryDslSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.cms.post.domain.QQslPost;
import me.zhengjie.cms.post.domain.QslPost;
import me.zhengjie.cms.post.service.QslPostService;
import me.zhengjie.cms.post.service.dto.QslPostQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @website https://el-admin.vip
* @author ray
* @date 2021-02-22
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "内容管理系统：内容管理管理")
@RequestMapping("/api/qslPost")
public class QslPostController {

    private final QslPostService qslPostService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('qslPost:list')")
    public void download(HttpServletResponse response, QslPostQueryCriteria criteria) throws IOException {
        qslPostService.download(qslPostService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询内容管理系统：内容管理")
    @ApiOperation("查询内容管理系统：内容管理")
    @PreAuthorize("@el.check('qslPost:list')")
    public ResponseEntity<Object> query(@RequestParam(required = false) String filter, Pageable pageable){
        QslPostQueryCriteria qslPostQueryCriteria = new QslPostQueryCriteria();
        return new ResponseEntity<>(qslPostService.queryAll(qslPostQueryCriteria, RSQLQueryDslSupport.toPredicate(filter, QQslPost.qslPost), pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增内容管理系统：内容管理")
    @ApiOperation("新增内容管理系统：内容管理")
    @PreAuthorize("@el.check('qslPost:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody QslPost resources){
        return new ResponseEntity<>(qslPostService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改内容管理系统：内容管理")
    @ApiOperation("修改内容管理系统：内容管理")
    @PreAuthorize("@el.check('qslPost:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody QslPost resources){
        qslPostService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除内容管理系统：内容管理")
    @ApiOperation("删除内容管理系统：内容管理")
    @PreAuthorize("@el.check('qslPost:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        qslPostService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
