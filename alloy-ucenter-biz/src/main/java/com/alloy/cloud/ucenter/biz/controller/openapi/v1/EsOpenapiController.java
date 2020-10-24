package com.alloy.cloud.ucenter.biz.controller.openapi.v1;

import com.alloy.cloud.common.core.base.R;
import com.alloy.cloud.ucenter.biz.entity.EsLibrary;
import com.alloy.cloud.ucenter.biz.repository.EsLibraryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
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
    public R<Page<EsLibrary>> page(String name,Integer page,Integer size) {
        PageRequest pageRequest = PageRequest.of(0, 20);
//        Page<EsLibrary> result = esLibraryRepository.findAll(pageRequest);
        Pageable pageable = PageRequest.of(page, size);
        //检索条件
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        if(StringUtils.isNotEmpty(name))
            bqb.must(QueryBuilders.matchPhraseQuery("name", name));
        //排序条件
        FieldSortBuilder fsb = SortBuilders.fieldSort("price").order(SortOrder.DESC);
        //聚合条件
        TermsAggregationBuilder builder1 = AggregationBuilders.terms("name").field("name.keyword");
//        TermsAggregationBuilder builder2 = AggregationBuilders.terms("year").field("year.keyword");
//        TermsAggregationBuilder builder = builder1.subAggregation(builder2);
        //构建查询
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(bqb)
                .withSort(fsb)
                .addAggregation(builder1)
                .withPageable(pageable)
                .build();
        Page<EsLibrary> result = esLibraryRepository.search(query);
        return R.ok(result);
    }

}
