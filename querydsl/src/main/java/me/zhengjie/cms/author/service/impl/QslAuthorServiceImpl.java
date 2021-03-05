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
package me.zhengjie.cms.author.service.impl;

import com.querydsl.core.types.Predicate;
import me.zhengjie.cms.author.domain.QslAuthor;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.cms.author.repository.QslAuthorRepository;
import me.zhengjie.cms.author.service.QslAuthorService;
import me.zhengjie.cms.author.service.dto.QslAuthorDto;
import me.zhengjie.cms.author.service.dto.QslAuthorQueryCriteria;
import me.zhengjie.cms.author.service.mapstruct.QslAuthorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author ray
* @date 2021-02-05
**/
@Service
@RequiredArgsConstructor
public class QslAuthorServiceImpl implements QslAuthorService {

    private final QslAuthorRepository qslAuthorRepository;
    private final QslAuthorMapper qslAuthorMapper;

    @Override
    public Map<String,Object> queryAll(QslAuthorQueryCriteria criteria, Predicate predicate, Pageable pageable){
        Page<QslAuthor> page = qslAuthorRepository.findAll(predicate, pageable);
//        Page<QslAuthor> page = qslAuthorRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(qslAuthorMapper::toDto));
    }

    @Override
    public List<QslAuthorDto> queryAll(QslAuthorQueryCriteria criteria, Predicate predicate){
//        return qslAuthorMapper.toDto(qslAuthorRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
        return qslAuthorMapper.toDto(StreamSupport.stream(qslAuthorRepository.findAll(predicate).spliterator(), false).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public QslAuthorDto findById(Long id) {
        QslAuthor qslAuthor = qslAuthorRepository.findById(id).orElseGet(QslAuthor::new);
        ValidationUtil.isNull(qslAuthor.getId(),"QslAuthor","id",id);
        return qslAuthorMapper.toDto(qslAuthor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QslAuthorDto create(QslAuthor resources) {
        return qslAuthorMapper.toDto(qslAuthorRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QslAuthor resources) {
        QslAuthor qslAuthor = qslAuthorRepository.findById(resources.getId()).orElseGet(QslAuthor::new);
        ValidationUtil.isNull( qslAuthor.getId(),"QslAuthor","id",resources.getId());
        qslAuthor.copy(resources);
        qslAuthorRepository.save(qslAuthor);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            qslAuthorRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<QslAuthorDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QslAuthorDto qslAuthor : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("姓名", qslAuthor.getName());
            map.put("年龄", qslAuthor.getAge());
            map.put("创建者", qslAuthor.getCreateBy());
            map.put("更新者", qslAuthor.getUpdateBy());
            map.put("创建日期", qslAuthor.getCreateTime());
            map.put("更新时间", qslAuthor.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
