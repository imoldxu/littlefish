package com.x.commons.mongo.pageHelper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * MongoDB分页查询工具类.
 *
 * @author xujh
 **/
public class MongoPageHelper {

    public static final int FIRST_PAGE_NUM = 1;
    public static final String ID = "_id";
    private final MongoTemplate mongoTemplate;

    public MongoPageHelper(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    /**
     * 分页查询，直接返回集合类型的结果.
     *
     * @see MongoPageHelper#pageQuery(org.springframework.data.mongodb.core.query.Query,
     * java.lang.Class, java.util.function.Function, java.lang.Integer, java.lang.Integer,
     * java.lang.String)
     */
    public <T> PageResult<T> pageQuery(Query query, String collectionName, Class<T> entityClass, Integer pageSize,
        Integer pageNum) {
        return pageQuery(query, collectionName, entityClass, Function.identity(), pageSize, pageNum, null);
    }

    /**
     * 分页查询，不考虑条件分页，直接使用skip-limit来分页.
     *
     * @see MongoPageHelper#pageQuery(org.springframework.data.mongodb.core.query.Query,
     * java.lang.Class, java.util.function.Function, java.lang.Integer, java.lang.Integer,
     * java.lang.String)
     */
    public <T, R> PageResult<R> pageQuery(Query query, String collectionName, Class<T> entityClass, Function<T, R> mapper,
        Integer pageSize, Integer pageNum) {
        return pageQuery(query, collectionName, entityClass, mapper, pageSize, pageNum, null);
    }

    /**
     * 分页查询.
     *
     * @param query Mongo Query对象，构造你自己的查询条件.
     * @param entityClass Mongo collection定义的entity class，用来确定查询哪个集合.
     * @param mapper 映射器，你从db查出来的list的元素类型是entityClass, 如果你想要转换成另一个对象，比如去掉敏感字段等，可以使用mapper来决定如何转换.
     * @param pageSize 分页的大小.
     * @param pageIndex 当前页.
     * @param lastId 条件分页参数, 区别于skip-limit，采用find(_id>lastId).limit分页.
     * 如果不跳页，像朋友圈，微博这样下拉刷新的分页需求，需要传递上一页的最后一条记录的ObjectId。 如果是null，则返回pageNum那一页.
     * @param <T> collection定义的class类型.
     * @param <R> 最终返回时，展现给页面时的一条记录的类型。
     * @return PageResult，一个封装page信息的对象.
     */
    public <T, R> PageResult<R> pageQuery(Query query, String collectionName, Class<T> entityClass, Function<T, R> mapper,
        Integer pageSize, Integer pageIndex, String lastId) {
        //分页逻辑
        long total = mongoTemplate.count(query, collectionName);
        final Integer pages = (int) Math.ceil(total / (double) pageSize);
        
        if (StringUtils.isNotBlank(lastId)) {
            if (pageIndex != FIRST_PAGE_NUM) {
            	final Criteria criteria = new Criteria();
                criteria.and(ID).gt(new ObjectId(lastId));
                query.addCriteria(criteria);
            }
            query.limit(pageSize);
        } else {
//        	if (pageIndex <= 0 || pageIndex > pages) {
//                pageIndex = FIRST_PAGE_NUM;
//            }
            int skip = pageSize * (pageIndex - 1);
            query.skip(skip).limit(pageSize);
        }

        //query.with(new Sort(Lists.newArrayList(new Order(Direction.ASC, ID)))),
        query.with(Sort.by(Order.asc(ID)));
        
        final List<T> entityList = mongoTemplate
            .find(query, entityClass, collectionName);

        final PageResult<R> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setData(entityList.stream().map(mapper).collect(Collectors.toList()));
        return pageResult;
    }

}
