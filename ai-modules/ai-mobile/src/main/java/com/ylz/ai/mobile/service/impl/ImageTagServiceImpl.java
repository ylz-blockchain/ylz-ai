package com.ylz.ai.mobile.service.impl;

import com.ylz.ai.mobile.entity.ImageTag;
import com.ylz.ai.mobile.mapper.ImageTagMapper;
import com.ylz.ai.mobile.service.IImageTagService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 * @Description: 照片标签
 * @Author: haifeng.lv
 * @Date: 2020-04-26 15:02
 */
@Service
public class ImageTagServiceImpl extends ServiceImpl<ImageTagMapper, ImageTag> implements IImageTagService {
}
