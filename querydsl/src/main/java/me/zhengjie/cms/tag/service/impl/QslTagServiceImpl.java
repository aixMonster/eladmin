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
package me.zhengjie.cms.tag.service.impl;

import com.querydsl.core.types.Predicate;
import me.zhengjie.cms.tag.domain.QslTag;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.cms.tag.repository.QslTagRepository;
import me.zhengjie.cms.tag.service.QslTagService;
import me.zhengjie.cms.tag.service.dto.QslTagDto;
import me.zhengjie.cms.tag.service.dto.QslTagQueryCriteria;
import me.zhengjie.cms.tag.service.mapstruct.QslTagMapper;
import org.springframework.data.jpa.domain.Specification;
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

/**
* @website https://el-admin.vip
* @description 服务实现
* @author ray
* @date 2021-02-24
**/
@Service
@RequiredArgsConstructor
public class QslTagServiceImpl implements QslTagService {

    private final QslTagRepository qslTagRepository;
    private final QslTagMapper qslTagMapper;

    @Override
    public Map<String,Object> queryAll(QslTagQueryCriteria criteria, Predicate predicate, Pageable pageable){
        Page<QslTag> page = qslTagRepository.findAll(predicate,pageable);
        return PageUtil.toPage(page.map(qslTagMapper::toDto));
    }

    @Override
    public List<QslTagDto> queryAll(QslTagQueryCriteria criteria){
        return qslTagMapper.toDto(qslTagRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public QslTagDto findById(Long id) {
        QslTag qslTag = qslTagRepository.findById(id).orElseGet(QslTag::new);
        ValidationUtil.isNull(qslTag.getId(),"QslTag","id",id);
        return qslTagMapper.toDto(qslTag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QslTagDto create(QslTag resources) {
        return qslTagMapper.toDto(qslTagRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QslTag resources) {
        QslTag qslTag = qslTagRepository.findById(resources.getId()).orElseGet(QslTag::new);
        ValidationUtil.isNull( qslTag.getId(),"QslTag","id",resources.getId());
        qslTag.copy(resources);
        qslTagRepository.save(qslTag);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            qslTagRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<QslTagDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QslTagDto qslTag : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("标签名", qslTag.getName());
            map.put("创建者", qslTag.getCreateBy());
            map.put("更新者", qslTag.getUpdateBy());
            map.put("创建日期", qslTag.getCreateTime());
            map.put("更新时间", qslTag.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
