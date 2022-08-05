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
    public List<TagEntity> findMostWidelyTag(){
        List<TagEntity> tags;
        Query query = entityManager.createNativeQuery("select `id`, `name`, `user`" +
                "from (select `t`.id as `id`, user_id as `user`, t.name as `name`,\n" +
                "             dense_rank() over (partition by user_id order by sum(`item`.quantity) desc ) as `rank`\n" +
                "      from orders_for_gift_certificates `order`\n" +
                "               join order_item `item` on `order`.id = `item`.order_id\n" +
                "               join gift_certificate gc on `item`.gift_certificate_id = `gc`.id\n" +
                "               join gift_certificate_has_tag gcht on `gc`.id = `gcht`.gift_certificate_id\n" +
                "               join tag `t` on `t`.id = `gcht`.tag_id\n" +
                "      where user_id in (select user_id\n" +
                "                        from orders_for_gift_certificates\n" +
                "                        group by user_id\n" +
                "                        having SUM(total_amount) >= ALL (select SUM(total_amount) as sum\n" +
                "                                                         from orders_for_gift_certificates\n" +
                "                                                         group by user_id))\n" +
                "      group by user_id, t.id) as `inner`\n" +
                "where `rank` = 1;", TagEntity.class);


//        Query query = entityManager.createNativeQuery("select `id`, `name`\n" +
//                "from (select `t`.id as `id`, `t`.name as `name`, rank() over (order by sum(`item`.quantity) desc) as `rank`\n" +
//                "            from orders_for_gift_certificates `order`\n" +
//                "                     left join order_item `item` on `order`.id = `item`.order_id\n" +
//                "                     left join gift_certificate `gc` on `item`.gift_certificate_id = `gc`.id\n" +
//                "                     left join gift_certificate_has_tag gcht on `gc`.id = `gcht`.gift_certificate_id\n" +
//                "                     left join tag `t` on `t`.id = `gcht`.tag_id\n" +
//                "            where user_id = 2\n" +
//                "            group by t.id) as `temp`\n" +
//                "where `rank` = 1", TagEntity.class);
//        Query query = entityManager.createQuery("select tag from OrderForGiftCertificateEntity o left join o.orderItems item left join item.giftCertificate gc left join gc.tags tag " +
//                "where o.user.id = 3 and sum (item.quantity) > 2 group by tag.id");

        return query.getResultList();
    }
}
