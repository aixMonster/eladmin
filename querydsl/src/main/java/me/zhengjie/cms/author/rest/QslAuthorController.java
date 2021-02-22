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
package me.zhengjie.cms.author.rest;

import com.querydsl.core.types.Predicate;
import io.github.perplexhub.rsql.RSQLCommonSupport;
import io.github.perplexhub.rsql.RSQLQueryDslSupport;
import me.zhengjie.annotation.Log;
import me.zhengjie.cms.author.domain.QQslAuthor;
import me.zhengjie.cms.author.domain.QslAuthor;
import me.zhengjie.cms.author.service.QslAuthorService;
import me.zhengjie.cms.author.service.dto.QslAuthorQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
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
* @date 2021-02-05
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "内容管理系统：作者管理管理")
@RequestMapping("/api/qslAuthor")
public class QslAuthorController {

    private final QslAuthorService qslAuthorService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('qslAuthor:list')")
    public void download(HttpServletResponse response, @QuerydslPredicate(root = QslAuthor.class) Predicate predicate) throws IOException {
        QslAuthorQueryCriteria criteria = new QslAuthorQueryCriteria();
        qslAuthorService.download(qslAuthorService.queryAll(criteria, predicate), response);
    }

    @GetMapping
    @Log("查询内容管理系统：作者管理")
    @ApiOperation("查询内容管理系统：作者管理")
    @PreAuthorize("@el.check('qslAuthor:list')")
//    public ResponseEntity<Object> query(@QuerydslPredicate(root = QslAuthor.class) Predicate predicate, Pageable pageable){
//        QslAuthorQueryCriteria criteria = new QslAuthorQueryCriteria();
//        return new ResponseEntity<>(qslAuthorService.queryAll(criteria, predicate, pageable),HttpStatus.OK);
//    }
    public ResponseEntity<Object> query(@RequestParam(required = false) String filter, Pageable pageable){
        QslAuthorQueryCriteria criteria = new QslAuthorQueryCriteria();
        return new ResponseEntity<>(qslAuthorService.queryAll(criteria, RSQLQueryDslSupport.toPredicate(filter, QQslAuthor.qslAuthor), pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增内容管理系统：作者管理")
    @ApiOperation("新增内容管理系统：作者管理")
    @PreAuthorize("@el.check('qslAuthor:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody QslAuthor resources){
        return new ResponseEntity<>(qslAuthorService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改内容管理系统：作者管理")
    @ApiOperation("修改内容管理系统：作者管理")
    @PreAuthorize("@el.check('qslAuthor:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody QslAuthor resources){
        qslAuthorService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除内容管理系统：作者管理")
    @ApiOperation("删除内容管理系统：作者管理")
    @PreAuthorize("@el.check('qslAuthor:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        qslAuthorService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}