package com.epam.esm.repository.impl;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.AbstractMySQLRepository;
import com.epam.esm.repository.interf.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * MySQL repository for tags
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Repository
public class TagMySQLRepository extends AbstractMySQLRepository<TagEntity> implements TagRepository{
    private static final String WHERE_T_NAME = "select object (t) from TagEntity t where t.name = :name";
    public static final String MOST_WIDELY_TAG_OF_USER_WITH_HIGHEST_COST = "select `id`, `name`, `create_date`, `last_update_date`" +
            "from (select `t`.id as `id`, `t`.name as `name`," +
            "             t.create_date as `create_date`, `t`.last_update_date as `last_update_date`," +
            "             rank() over (partition by user_id order by sum(`item`.quantity) desc ) as `rank`" +
            "      from orders_for_gift_certificates `order`" +
            "               join order_item `item` on `order`.id = `item`.order_id" +
            "               join gift_certificate gc on `item`.gift_certificate_id = `gc`.id" +
            "               join gift_certificate_has_tag gcht on `gc`.id = `gcht`.gift_certificate_id" +
            "               join tag `t` on `t`.id = `gcht`.tag_id" +
            "      where user_id in (select user_id" +
            "                        from orders_for_gift_certificates" +
            "                        group by user_id" +
            "                        having SUM(total_amount) >= ALL (select SUM(total_amount) as sum" +
            "                                                         from orders_for_gift_certificates" +
            "                                                         group by user_id))" +
            "      group by user_id, t.id) as `inner`" +
            "where `rank` = 1";

    public TagMySQLRepository() {
        setClazz(TagEntity.class);
    }

    @Override
    public TagEntity readByName(String name){
        TypedQuery<TagEntity> query = entityManager.createQuery(WHERE_T_NAME, TagEntity.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<TagEntity> findMostWidelyTag(){
        Query query = entityManager.createNativeQuery(MOST_WIDELY_TAG_OF_USER_WITH_HIGHEST_COST, TagEntity.class);
        return query.getResultList();
    }
}
