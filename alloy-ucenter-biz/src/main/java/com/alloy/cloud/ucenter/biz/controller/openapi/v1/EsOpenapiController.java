package com.alloy.cloud.ucenter.biz.controller.openapi.v1;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alloy.cloud.common.core.base.R;
import com.alloy.cloud.ucenter.biz.entity.EsLibrary;
import com.alloy.cloud.ucenter.biz.repository.EsLibraryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    private final RestHighLevelClient restHighLevelClient;

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

    /**
     * 浅分页
     * @param name
     * @param price
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/page")
    public R<Page<EsLibrary>> page(String name,Integer price,Integer page,Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        //组合检索条件
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        if(StringUtils.isNotEmpty(name)){
            bqb.must(QueryBuilders.matchPhraseQuery("name", name));
        }
        if(ObjectUtil.isNotNull(price)){
            bqb.must(QueryBuilders.rangeQuery("price").gt(price));
        }
        //高亮
        HighlightBuilder nameHighlight = new HighlightBuilder();
        nameHighlight.field("name");
        nameHighlight.preTags("<strong>");
        nameHighlight.postTags("</strong>");
        //排序条件
        FieldSortBuilder fsb = SortBuilders.fieldSort("price").order(SortOrder.DESC);
        //聚合条件
        TermsAggregationBuilder builder1 = AggregationBuilders.terms("name").field("name.keyword");
        TermsAggregationBuilder builder2 = AggregationBuilders.terms("price").field("price");
        TermsAggregationBuilder builder = builder1.subAggregation(builder2);
        //构建查询
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(bqb)
                .withSort(fsb)
                .addAggregation(builder)
                .withPageable(pageable).withHighlightBuilder(nameHighlight)
                .build();
        Page<EsLibrary> result = esLibraryRepository.search(query);
        return R.ok(result);
    }

    /**
     * 深分页  searchAfter
     *   例如你现在需要按照 id 和 price 进行排序；
     *   你获取了第一页的结果后，现在需要获取第二页内容
     *   你需要使用第一页最后一条的 id 和 price，作为 search_after 的参数传递到查询请求中。
     * @param name
     * @param price
     * @param size
     * @return
     */
    @PostMapping("/page1")
    public R<List<EsLibrary>> page1(Long id,Integer price,String name,Integer size) throws Exception{

        //组合查询条件
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        if(StringUtils.isNotEmpty(name)){
            bqb.must(QueryBuilders.matchPhraseQuery("name", name));
        }
        if(ObjectUtil.isNotNull(price)){
            bqb.must(QueryBuilders.rangeQuery("price").gt(price));
        }
        //排序
        FieldSortBuilder priceSort = SortBuilders.fieldSort("price").order(SortOrder.ASC);
        FieldSortBuilder idSort = SortBuilders.fieldSort("_id").order(SortOrder.DESC);

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(bqb);
        builder.sort(priceSort);
        builder.sort(idSort);
        builder.size(size);
        if(ObjectUtil.isNotNull(id) && ObjectUtil.isNotNull(price)){
            builder.searchAfter(new String[]{id.toString(),price.toString()});
        }else{
            builder.from(0);
        }

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("library");
        searchRequest.source(builder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        SearchHit[] hitsHits = hits.getHits();

        List<Map<String, Object>> data = new ArrayList<>();
        for (SearchHit hitsHit : hitsHits) {
            data.add(hitsHit.getSourceAsMap());
        }
        List<EsLibrary> results = new ArrayList<>();
        Iterator<Map<String, Object>> iterator = data.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> next = iterator.next();
            EsLibrary esLibrary = new EsLibrary();
            esLibrary.setAuthor(MapUtil.getStr(next,"author"));
            esLibrary.setPrice(new BigDecimal(MapUtil.getDouble(next,"price")));
            esLibrary.setPublish(MapUtil.getStr(next,"publish"));
            esLibrary.setName(MapUtil.getStr(next,"name"));
            esLibrary.setType(MapUtil.getStr(next,"type"));
            esLibrary.setInfo(MapUtil.getStr(next,"info"));
            results.add(esLibrary);
        }
        return R.ok(results);
    }
}
