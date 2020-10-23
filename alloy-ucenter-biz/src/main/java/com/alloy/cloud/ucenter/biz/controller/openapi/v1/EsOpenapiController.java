package com.alloy.cloud.ucenter.biz.controller.openapi.v1;

import com.alloy.cloud.common.core.base.R;
import com.alloy.cloud.ucenter.biz.entity.EsLibrary;
import com.alloy.cloud.ucenter.biz.repository.EsLibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: tankechao
 * @Date: 2020/10/22 10:54
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/openapi/v1/es")
public class EsOpenapiController {

    private final EsLibraryRepository esLibraryRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @PostMapping("/index")
    public R<Boolean> createEsIndex() {
        boolean index = elasticsearchRestTemplate.indexOps(EsLibrary.class).create();
        return R.ok(index);
    }

    @DeleteMapping("/index")
    public R<Boolean> deleteEsIndex() {
        boolean deleteIndex = elasticsearchRestTemplate.indexOps(EsLibrary.class).delete();
        return R.ok(deleteIndex);
    }

    @GetMapping("/index/exists")
    public R<Boolean> existEsIndex() {
        boolean existsIndex = elasticsearchRestTemplate.indexOps(EsLibrary.class).exists();
        return R.ok(existsIndex);
    }

    @PostMapping
    public R<EsLibrary> saveEsDoc(@RequestBody EsLibrary esLibrary) {
        EsLibrary result = esLibraryRepository.save(esLibrary);
        return R.ok(result);
    }

    @GetMapping("/{id}")
    public R<EsLibrary> findById(@PathVariable("id") Long id) {
        return R.ok(esLibraryRepository.findById(id).get());
    }

    @PostMapping("/page")
    public R<Page<EsLibrary>> findAll(String name) {
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<EsLibrary> result = esLibraryRepository.findAll(pageRequest);
        return R.ok(result);
    }

}
