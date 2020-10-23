package com.alloy.cloud.ucenter.biz.repository;

import com.alloy.cloud.ucenter.biz.entity.EsLibrary;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @Author: tankechao
 * @Date: 2020/10/22 10:46
 */
@Repository
public interface EsLibraryRepository extends ElasticsearchRepository<EsLibrary, Serializable> {
}
