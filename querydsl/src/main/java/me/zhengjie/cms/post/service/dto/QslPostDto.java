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
package me.zhengjie.cms.post.service.dto;

import lombok.Data;
import me.zhengjie.cms.author.service.dto.QslAuthorDto;
import me.zhengjie.cms.author.service.dto.QslAuthorSmallDto;
import me.zhengjie.cms.tag.domain.QslTag;
import me.zhengjie.cms.tag.service.dto.QslTagSmallDto;

import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;

/**
* @website https://el-admin.vip
* @description /
* @author ray
* @date 2021-02-22
**/
@Data
public class QslPostDto implements Serializable {

    /** ID */
    private Long id;

    /** 标题 */
    private String title;

    /** 内容 */
    private String content;

    /** 作者 */
    private QslAuthorSmallDto qslAuthor;
    /** 标签 */
    private List<QslTagSmallDto> qslTags;

    /** 创建者 */
    private String createBy;

    /** 更新者 */
    private String updateBy;

    /** 创建日期 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}
