package com.epam.esm.repository.impl;

import com.epam.esm.entity.CriteriaEntity;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.repository.AbstractMySQLRepository;
import com.epam.esm.repository.constant.SearchParam;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.epam.esm.repository.constant.SearchParam.GIFT_CERTIFICATE_SEARCH_PARAM;
import static com.epam.esm.repository.constant.SearchParam.GIFT_CERTIFICATE_SORT_PARAM;
import static com.epam.esm.repository.constant.SearchParam.GIFT_SEARCH_BY_TAG_NAME;

/**
 * MySQL repository for gift certificates
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Repository
public class GiftCertificateMySQLRepository extends AbstractMySQLRepository<GiftCertificateEntity> implements GiftCertificateRepository {
    private static final String SELECT_FROM_GIFT = "select distinct g from GiftCertificateEntity g left join g.tags t";
    public static final String SELECT_COUNT_FROM_GIFT = "select count (gc) from GiftCertificateEntity gc where gc in (";

    public GiftCertificateMySQLRepository() {
        setClazz(GiftCertificateEntity.class);
    }

    @Override
    public List<GiftCertificateEntity> findByCriteria(CriteriaEntity cr) throws RepositoryException {
        StringBuilder findQuery = new StringBuilder(SELECT_FROM_GIFT);

        appendCriteriaToFindQuery(findQuery, cr);
        appendSortingToFindQuery(findQuery, cr);

        TypedQuery<GiftCertificateEntity> query = entityManager.createQuery(findQuery.toString(),
                GiftCertificateEntity.class);

        setPagination(query, cr);

        cr.setTotalSize((long) entityManager
                .createQuery(findQuery.insert(0, SELECT_COUNT_FROM_GIFT).append(")").toString())
                .getSingleResult());

        pageValidation(cr);

        return query.getResultList();
    }

    private void appendSortingToFindQuery(StringBuilder findQuery, CriteriaEntity cr) throws RepositoryException {
        String sorting = cr.getSorting();

        if (sorting != null) {
            boolean desc = false;
            if (sorting.startsWith("-")) {
                desc = true;
                sorting = sorting.substring(1);
            }
            if (sorting.startsWith("+") || sorting.startsWith(" ")) {
                sorting = sorting.substring(1);
            }

            if (GIFT_CERTIFICATE_SORT_PARAM.contains(sorting)) {
                findQuery.append(" order by g.");
                findQuery.append(sorting);
                if (desc) {
                    findQuery.append(" desc");
                }
            }else {
                throw new RepositoryException("Incorrect sorting param");
            }
        }
    }

    private void appendCriteriaToFindQuery(StringBuilder findQuery, CriteriaEntity cr) {
        Map<String, String> criteriaMap = cr.getSearchParam();
        String[] tags = null;

        if (criteriaMap != null && !criteriaMap.isEmpty()) {
            findQuery.append(" where ");

            Iterator<String> iter = criteriaMap.keySet().iterator();
            while (iter.hasNext()) {
                String s = iter.next();

                if (GIFT_SEARCH_BY_TAG_NAME.equals(s)) {
                    tags = tagsParsingAndAppendToFindQuery(findQuery, criteriaMap.get(s));
                } else {
                    findQuery.append("g.").append(s).append(" like '%")
                            .append(criteriaMap.get(s)).append("%'");
                }
                if (iter.hasNext()) {
                    findQuery.append(" and ");
                }
            }
        }

        if (tags != null) {
            findQuery.append(" group by g.id");
            findQuery.append(" having count(t.name) = ").append(tags.length);
        }
    }
    
    private String[] tagsParsingAndAppendToFindQuery(StringBuilder findQuery, String tagNamesStr){
        String[] tags = null;
        if (tagNamesStr.contains(",")) {
            tags = tagNamesStr.split(",");
            Iterator<String> tagsIt = Arrays.stream(tags).iterator();
            while (tagsIt.hasNext()) {
                findQuery.append(GIFT_SEARCH_BY_TAG_NAME).append(" ='");
                String tag = tagsIt.next();
                findQuery.append(tag);
                findQuery.append("'");
                if (tagsIt.hasNext()) {
                    findQuery.append(" or ");
                }
            }
        } else {
            findQuery.append(GIFT_SEARCH_BY_TAG_NAME).append(" ='").append(tagNamesStr).append("'");
        }
        return tags;
    }
}
