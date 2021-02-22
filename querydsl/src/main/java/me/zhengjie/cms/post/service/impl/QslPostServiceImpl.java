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
package me.zhengjie.cms.post.service.impl;

import com.querydsl.core.types.Predicate;
import me.zhengjie.cms.post.domain.QslPost;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.cms.post.repository.QslPostRepository;
import me.zhengjie.cms.post.service.QslPostService;
import me.zhengjie.cms.post.service.dto.QslPostDto;
import me.zhengjie.cms.post.service.dto.QslPostQueryCriteria;
import me.zhengjie.cms.post.service.mapstruct.QslPostMapper;
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
* @date 2021-02-22
**/
@Service
@RequiredArgsConstructor
public class QslPostServiceImpl implements QslPostService {

    private final QslPostRepository qslPostRepository;
    private final QslPostMapper qslPostMapper;

    @Override
    public Map<String,Object> queryAll(QslPostQueryCriteria criteria, Predicate predicate, Pageable pageable){
//        Page<QslPost> page = qslPostRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        Page<QslPost> page = qslPostRepository.findAll(predicate, pageable);
        return PageUtil.toPage(page.map(qslPostMapper::toDto));
    }

    @Override
    public List<QslPostDto> queryAll(QslPostQueryCriteria criteria){
        return qslPostMapper.toDto(qslPostRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public QslPostDto findById(Long id) {
        QslPost qslPost = qslPostRepository.findById(id).orElseGet(QslPost::new);
        ValidationUtil.isNull(qslPost.getId(),"QslPost","id",id);
        return qslPostMapper.toDto(qslPost);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QslPostDto create(QslPost resources) {
        return qslPostMapper.toDto(qslPostRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QslPost resources) {
        QslPost qslPost = qslPostRepository.findById(resources.getId()).orElseGet(QslPost::new);
        ValidationUtil.isNull( qslPost.getId(),"QslPost","id",resources.getId());
        qslPost.copy(resources);
        qslPostRepository.save(qslPost);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            qslPostRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<QslPostDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QslPostDto qslPost : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("标题", qslPost.getTitle());
            map.put("内容", qslPost.getContent());
            map.put("作者", qslPost.getQslAuthor().getName());
            map.put("创建者", qslPost.getCreateBy());
            map.put("更新者", qslPost.getUpdateBy());
            map.put("创建日期", qslPost.getCreateTime());
            map.put("更新时间", qslPost.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
