package it.sella.pfm.movements.commonlib.mongoservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class BackofficeMongoService<T> {

    private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public BackofficeMongoService(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Mono<Long> nextVal(String sequenceName) {
        return mongoTemplate
                .findAndModify(
                        query(where("name").is(sequenceName)),
                        new Update().inc("value", 1),
                        new FindAndModifyOptions().returnNew(true),
                        BackofficeSequence.class
                )
                .switchIfEmpty(mongoTemplate.insert(new BackofficeSequence(sequenceName, 1)))
                .map(BackofficeSequence::getValue);
    }

    public T save(T save) {
        return mongoTemplate.save(save).block();
    }

    public T findById(Object id, Class<T> entityClass) {
        return mongoTemplate.findById(id, entityClass).block();
    }

    public T deleteById(Object id, Class<T> entityClass) {
        return mongoTemplate.findAndRemove(Query.query(Criteria.where("_id").is(id)), entityClass).block();
    }

    public T findByCausaleAbbreviationAndCausaleHostAndCausaleInternaCodeIsNull(String causaleAbbreviation, String causaleHost, Class<T> className) {
        return mongoTemplate.find(Query.query(Criteria.where("causaleAbbreviation").is(causaleAbbreviation)
                .and("causaleHost").is(causaleHost)
                .and("causaleInternaCode").is(null)), className).blockFirst();
    }

    public T findByCausaleInternaCode(String causaleInternaCode, Class<T> className) {
        return mongoTemplate.find(Query.query(Criteria.where("causaleInternaCode").is(causaleInternaCode)), className).blockFirst();
    }

    public T findByDirectionAndOperationTypeAndOperationCodeIsNull(String direction, String operationType, Class<T> className) {
        return mongoTemplate.find(Query.query(Criteria.where("direction").is(direction)
                .and("operationType").is(operationType)
                .and("operationCode").is(null)), className).blockFirst();
    }

    public List<T> findAll(Class<T> className) {
        return mongoTemplate.findAll(className).collectList().block();
    }

    public <T> Page<T> getPage(Criteria criteria, Pageable page, Class<T> type, String collection) {
        List<T> data = getList(criteria, page, type, collection);
        Query countQuery = getQuery(criteria);
        long count = mongoTemplate.count(countQuery, collection).block();
        return new PageImpl<>(data, page, count);
    }

    protected Query getQuery(Criteria criteria) {
        Query query = new Query();
        query.addCriteria(criteria);
        return query;
    }

    public <T> List<T> getList(Criteria criteria, Pageable page, Class<T> type, String collection) {
        Query query = getQuery(criteria);
        if (page != null) {
            query.with(page);
        }
        return mongoTemplate.find(query, type, collection).collectList().block();
    }
}
