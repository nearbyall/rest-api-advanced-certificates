package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.repository.entity.CertificateTagEntity;
import com.epam.esm.persistence.repository.entity.TagEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TagRepositoryJpaImpl implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Integer> create(TagEntity tag) {
        entityManager.persist(tag);

        Integer id = tag.getId();
        return id != 0
                ? Optional.of(id) :
                Optional.empty();
    }

    @Override
    public List<TagEntity> getAll(int limit, int offset) {
        TypedQuery<TagEntity> tags = entityManager
                .createQuery("SELECT t FROM TagEntity t", TagEntity.class)
                .setMaxResults(limit)
                .setFirstResult(offset);

        return tags.getResultList();
    }

    @Override
    public Optional<TagEntity> getById(Integer id) {
        TypedQuery<TagEntity> query = entityManager
                .createQuery("SELECT t FROM TagEntity t WHERE t.id = :id", TagEntity.class)
                .setParameter("id", id);

        List<TagEntity> tags = query.getResultList();

        return tags.isEmpty()
                ? Optional.empty()
                : Optional.of(tags.get(0));
    }

    @Deprecated
    @Override
    public Integer update(TagEntity tag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer delete(Integer id) {
        return entityManager
                .createQuery("DELETE FROM TagEntity WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }


    @Override
    public Optional<TagEntity> getByName(String name) {
        List<TagEntity> tags = entityManager
                .createQuery("SELECT t FROM TagEntity t WHERE t.name = :name", TagEntity.class)
                .setParameter("name", name)
                .getResultList();

        return tags.isEmpty()
                ? Optional.empty()
                : Optional.of(tags.get(0));
    }

    @Override
    public List<TagEntity> getCertificateTagsById(Integer certificateId, int limit, int offset) {
        return entityManager
                .createQuery("SELECT t " +
                        "FROM CertificateEntity c " +
                        "JOIN c.tags t  " +
                        "WHERE c.id = :id", TagEntity.class)
                .setParameter("id", certificateId)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Integer addCertificateTag(Integer certificateId, Integer tagId) {
        CertificateTagEntity certificateTag = new CertificateTagEntity()
                .setCertificateId(certificateId)
                .setTagId(tagId);

        entityManager.persist(certificateTag);
        return 1;
    }

    @Override
    public Integer deleteCertificateTag(Integer certificateId, Integer tagId) {
        CertificateTagEntity certificateTag = new CertificateTagEntity()
                .setCertificateId(certificateId)
                .setTagId(tagId);

        entityManager.remove(entityManager.contains(certificateTag)
                ? certificateTag
                : entityManager.merge(certificateTag));

        return 1;
    }

    @Override
    public Optional<TagEntity> getCertificateTagByTagName(Integer certificateId, String tagName) {
        List<TagEntity> tags = entityManager
                .createQuery("SELECT t " +
                        "FROM CertificateEntity c " +
                        "JOIN c.tags t " +
                        "WHERE c.id = :cId AND t.name = :tagName", TagEntity.class)
                .setParameter("cId", certificateId)
                .setParameter("tagName", tagName)
                .getResultList();

        return tags.isEmpty()
                ? Optional.empty()
                : Optional.of(tags.get(0));
    }

    @Override
    public Optional<TagEntity> getMostWidelyUsedTagWithHighestCostOfAllUserOrders(Integer id) {
        List<TagEntity> tags = entityManager
                .createQuery("SELECT t FROM UserEntity u " +
                        "JOIN u.orders o " +
                        "JOIN o.certificate.tags t " +
                        "WHERE u.id = :id " +
                        "GROUP BY t.id, t.name " +
                        "ORDER BY SUM(o.cost) DESC, COUNT(t) DESC", TagEntity.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();

        return tags.isEmpty()
                ? Optional.empty()
                : Optional.of(tags.get(0));
    }

    @Override
    public Integer delete(String name) {
        return entityManager
                .createQuery("DELETE FROM TagEntity WHERE name = :name")
                .setParameter("name", name)
                .executeUpdate();
    }

    @Override
    public Integer getCount() {
        return entityManager
                .createQuery("SELECT COUNT(t) FROM TagEntity t", Long.class)
                .getSingleResult()
                .intValue();
    }

    @Override
    public Integer getCountByCertificateId(Integer id) {
        return entityManager
                .createQuery("SELECT COUNT(t) " +
                        "FROM CertificateEntity c " +
                        "JOIN c.tags t " +
                        "WHERE c.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult()
                .intValue();
    }

}
