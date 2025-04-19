package com.yupi.yuzan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuzan.mapper.PostMapper;
import com.yupi.yuzan.service.PostService;
import generator.domain.Post;
import org.springframework.stereotype.Service;

/**
* @author 葛成
* @description 针对表【post(帖子)】的数据库操作Service实现
* @createDate 2025-04-19 20:27:39
*/
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService {

}




