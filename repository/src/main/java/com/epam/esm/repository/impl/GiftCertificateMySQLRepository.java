package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.AbstractMySQLRepository;
import com.epam.esm.repository.constant.GiftCertificateSearchParam;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import com.epam.esm.repository.mapper.GiftCertificateResultSetExtractor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.sound.midi.MetaMessage;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.*;

import static com.epam.esm.repository.constant.GiftCertificateSearchParam.SEARCH_CERTIFICATE_PARAM;
import static com.epam.esm.repository.constant.GiftCertificateSearchParam.SEARCH_TAG_NAME;

/**
 * MySQL repository for gift certificates
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Repository
public class GiftCertificateMySQLRepository extends AbstractMySQLRepository<GiftCertificateEntity> implements GiftCertificateRepository {

    public GiftCertificateMySQLRepository() {
        setClazz(GiftCertificateEntity.class);
    }

    @Override
    @Transactional
    public List<GiftCertificateEntity> findByCriteria(Map<String, String> criteriaMap, String sorting) throws RepositoryException {
        List<GiftCertificateEntity> giftCertificates;
        StringBuilder findQuery = new StringBuilder("select g from GiftCertificateEntity g join g.tags t");

        if (criteriaMap != null) {
            if (!criteriaMap.isEmpty()) {
                findQuery.append(" where ");
            }
            Iterator<String> iter = criteriaMap.keySet().iterator();
            while (iter.hasNext()) {
                String s = iter.next();
                if (SEARCH_CERTIFICATE_PARAM.contains(s)) {
                    findQuery.append("g.");
                }

                if (SEARCH_TAG_NAME.equals(s)) {
                    String cr = criteriaMap.get(s);
                    if (cr.contains(",")) {
                        String[] tags = cr.split(",");

                        Iterator<String> tagsIt = Arrays.stream(tags).iterator();
                        while (tagsIt.hasNext()) {
                            findQuery.append(s).append(" ='");
                            String tag = tagsIt.next();
                            findQuery.append(tag);
                            findQuery.append("'");

                            if (tagsIt.hasNext()) {
                                findQuery.append(" or ");
                            }
                        }
                    } else {
                        findQuery.append(s).append(" ='").append(cr).append("'");
                    }
                } else {
                    findQuery.append(s).append(" like '%")
                            .append(criteriaMap.get(s)).append("%'");
                }

                if (iter.hasNext()) {
                    findQuery.append(" and ");
                }
            }
            findQuery.append(" group by g.id");
        }

        if (sorting != null) {
            boolean desc = false;
            if (sorting.startsWith("-")) {
                desc = true;
                sorting = sorting.substring(1);
            }
            if (sorting.startsWith("+") || sorting.startsWith(" ")) {
                sorting = sorting.substring(1);
            }
            if (GiftCertificateSearchParam.SORT_PARAM.contains(sorting)) {
                findQuery.append(" order by ");
                if (SEARCH_CERTIFICATE_PARAM.contains(sorting)) {
                    findQuery.append("g.");
                }
                findQuery.append(sorting);
                if (desc) {
                    findQuery.append(" desc");
                }
            }
        }
//        System.out.println(findQuery);
//        findQuery = new StringBuilder("select g from GiftCertificateEntity g join g.tags t where t.name like 'Автоспорт' or t.name like 'Автолюбителям' group by g.id");
//        findQuery = new StringBuilder("select g from GiftCertificateEntity g join g.tags t where g.name like '%new%' or g.price<11");
        giftCertificates = entityManager.createQuery(findQuery.toString()).getResultList();
//        EntityType<GiftCertificateEntity> giftCertificateEntity_ = m.entity(GiftCertificateEntity.class);

//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<GiftCertificateEntity> cq = cb.createQuery(GiftCertificateEntity.class);
//        Root<GiftCertificateEntity> root = query.from(GiftCertificateEntity.class);

//        Metamodel m = entityManager.getMetamodel();
//        EntityType<GiftCertificateEntity> gift_ = m.entity(GiftCertificateEntity.class);
////        EntityType<TagEntity> tag_ = m.entity(TagEntity.class);
//        Root<GiftCertificateEntity> gift = cq.from(gift_);
//        Join<GiftCertificateEntity, TagEntity> tag = gift.join(gift_.getList("tags", TagEntity.class));

//        Subquery<TagEntity> subquery = query.subquery(TagEntity.class);
//        Root<TagEntity> tagEntityRoot = subquery.from(TagEntity.class);
//        subquery.select(tagEntityRoot).where(cb.like(tagEntityRoot.get("name"), "%Автолюбителям%"));


//        Path<String> path = root.get("name");
//        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(cb.like(path, "Ночной"));
//        query.select(root).where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

//        Predicate greaterThanPrice = cb.lt(root.get("price"), 11);
//        Predicate chairItems = cb.like(root.get("Tag.name"), "%Автолюбителям%");
//        query.select(root).where(cb.or(greaterThanPrice, chairItems));

//        query.select(root).where(cb.in()).;

//        Predicate predicate = cb.in(tag.get("name")).value("Автоспорт");
//        Predicate predicate1 = cb.in(tag.get("name")).value("Автолюбителям");
//        cq.where(cb.and(predicate, predicate1));

//        Predicate predicate = cb.in(tag.get("name")).value("Автоспорт").value("Автолюбителям");
//
//        cq.where(predicate);
//        cq.groupBy(gift.get("id"));
//        cq.having(predicate);

//        cq.having(cb.in(tag.get("name")).value("Автоспорт").value("Автолюбителям"));


//        cq.where(cb.like(tag.get("name"), "%Автоспорт%"));

//        giftCertificates = entityManager.createQuery(cq).getResultList();

        return giftCertificates;
    }


//    @Override
//    public List<GiftCertificateEntity> findByCriteria(Map<String, String> criteriaMap, String sorting) throws RepositoryException {
//        List giftCertificates;
//
//        StringBuilder findQuery = new StringBuilder(READ_ALL);
//
//        if (criteriaMap != null) {
//            if (!criteriaMap.isEmpty()) {
//                findQuery.append(" WHERE ");
//            }
//            Iterator<String> iter = criteriaMap.keySet().iterator();
//            while (iter.hasNext()) {
//                String s = iter.next();
//                if (GiftCertificateSearchParam.SEARCH_CERTIFICATE_PARAM.contains(s)) {
//                    findQuery.append("gc.");
//                }
//                findQuery.append(s).append(" LIKE '%")
//                        .append(criteriaMap.get(s)).append("%'");
//                if (iter.hasNext()) {
//                    findQuery.append(" AND ");
//                }
//            }
//        }
//
//        if (sorting != null) {
//            boolean desc = false;
//            if (sorting.startsWith("-")) {
//                desc = true;
//                sorting = sorting.substring(1);
//            }
//            if (sorting.startsWith("+") || sorting.startsWith(" ")) {
//                sorting = sorting.substring(1);
//            }
//            if (GiftCertificateSearchParam.SORT_PARAM.contains(sorting)) {
//                findQuery.append(" ORDER BY ");
//                if (GiftCertificateSearchParam.SEARCH_CERTIFICATE_PARAM.contains(sorting)) {
//                    findQuery.append("gc.");
//                }
//                findQuery.append(sorting);
//                if (desc) {
//                    findQuery.append(" DESC");
//                }
//            }
//        }
//
}
