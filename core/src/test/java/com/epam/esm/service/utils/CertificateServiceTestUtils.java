package com.epam.esm.service.utils;

import com.epam.esm.persistence.repository.entity.CertificateEntity;
import com.epam.esm.persistence.repository.entity.TagEntity;
import com.epam.esm.service.dto.certificate.CertificateDTO;
import com.epam.esm.service.dto.certificate.CertificateNewDTO;
import com.epam.esm.service.dto.tag.TagDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CertificateServiceTestUtils {
    public static List<CertificateDTO> getCertificatesDTOs() {
        return Arrays.asList(
                new CertificateDTO()
                        .setId(1)
                        .setName("Game Store")
                        .setDescription("Gift certificate for purchase in the game store")
                        .setPrice("50.00")
                        .setDuration(30)
                        .setCreateDate("2019-01-26T10:00:00")
                        .setLastUpdateDate("2019-01-26T10:00:00")
                        .setTags(TagServiceTestUtils.getTagDTOs().subList(0, 4)),
                new CertificateDTO()
                        .setId(2)
                        .setName("Kids Clothes")
                        .setDescription("Gift certificate for kids clothing purchasing")
                        .setPrice("40.00")
                        .setDuration(45)
                        .setCreateDate("2020-01-26T10:00:00")
                        .setLastUpdateDate("2020-01-26T10:00:00")
                        .setTags(TagServiceTestUtils.getTagDTOs().subList(4, 8)),
                new CertificateDTO()
                        .setId(3)
                        .setName("Beauty Store")
                        .setDescription("Gift certificate for purchase in the beauty store")
                        .setPrice("60.00")
                        .setDuration(60)
                        .setCreateDate("2022-01-26T10:00:00")
                        .setLastUpdateDate("2022-01-26T10:00:00")
                        .setTags(TagServiceTestUtils.getTagDTOs().subList(8, 12))
        );
    }

    public static CertificateNewDTO getCertificateNewDto() {
        return new CertificateNewDTO()
                .setId(1)
                .setName("Game Store")
                .setDescription("Gift certificate for purchase in the game store")
                .setPrice("50.00")
                .setDuration(30)
                .setCreateDate("2019-01-26T10:00:00")
                .setLastUpdateDate("2019-01-26T10:00:00")
                .setTags(TagServiceTestUtils.getTagDTOs().subList(0, 4));
    }

    public static List<CertificateEntity> getCertificates() {
        return Arrays.asList(
                new CertificateEntity()
                        .setId(1)
                        .setName("Game Store")
                        .setDescription("Gift certificate for purchase in the game store")
                        .setPrice(50.0)
                        .setDuration(30)
                        .setCreateDate(LocalDateTime.parse("2019-01-26T10:00:00"))
                        .setLastUpdateDate(LocalDateTime.parse("2019-01-26T10:00:00"))
                        .setTags(TagServiceTestUtils.getTags().subList(0, 4)),
                new CertificateEntity()
                        .setId(2)
                        .setName("Kids Clothes")
                        .setDescription("Gift certificate for kids clothing purchasing")
                        .setPrice(40.0)
                        .setDuration(45)
                        .setCreateDate(LocalDateTime.parse("2020-01-26T10:00:00"))
                        .setLastUpdateDate(LocalDateTime.parse("2020-01-26T10:00:00"))
                        .setTags(TagServiceTestUtils.getTags().subList(4, 8)),
                new CertificateEntity()
                        .setId(3)
                        .setName("Beauty Store")
                        .setDescription("Gift certificate for purchase in the beauty store")
                        .setPrice(60.0)
                        .setDuration(60)
                        .setCreateDate(LocalDateTime.parse("2022-01-26T10:00:00"))
                        .setLastUpdateDate(LocalDateTime.parse("2022-01-26T10:00:00"))
                        .setTags(TagServiceTestUtils.getTags().subList(8, 12))
        );
    }

    public static CertificateNewDTO getCertificateCreationTestNewDTO() {
        return new CertificateNewDTO()
                .setId(4)
                .setName("Test Gift")
                .setDescription("Test Gift certificate for purchasing")
                .setPrice("52.30")
                .setDuration(30)
                .setCreateDate("2021-01-26T10:00:00")
                .setLastUpdateDate("2021-01-26T10:00:00")
                .setTags(new ArrayList<>(Arrays.asList(
                        new TagDTO().setName("game"),
                        new TagDTO().setName("test")))
                );
    }

    public static CertificateDTO getCertificateCreationTestDTO() {
        return new CertificateDTO()
                .setId(4)
                .setName("Test Gift")
                .setDescription("Test Gift certificate for purchasing")
                .setPrice("52.30")
                .setDuration(30)
                .setCreateDate("2021-01-26T10:00:00")
                .setLastUpdateDate("2021-01-26T10:00:00")
                .setTags(new ArrayList<>(Arrays.asList(
                        new TagDTO().setName("game"),
                        new TagDTO().setName("test")))
                );
    }

    public static CertificateEntity getCertificateCreationTest() {
        return new CertificateEntity()
                .setId(4)
                .setName("Test Gift")
                .setDescription("Test Gift certificate for purchasing")
                .setPrice(52.3)
                .setDuration(30)
                .setCreateDate(LocalDateTime.parse("2021-01-26T10:00:00"))
                .setLastUpdateDate(LocalDateTime.parse("2021-01-26T10:00:00"))
                .setTags(new ArrayList<>(Arrays.asList(
                        new TagEntity().setId(1).setName("game"),
                        new TagEntity().setId(13).setName("test")))
                );
    }

    public static CertificateDTO getUpdateCertificateDTO() {
        return new CertificateDTO()
                .setId(1)
                .setName("Test name")
                .setDescription("Test description")
                .setPrice("50.00")
                .setDuration(30)
                .setCreateDate("2019-01-26T10:00:00")
                .setLastUpdateDate("2020-01-26T10:00:00");
    }

    public static CertificateEntity getUpdateCertificate() {
        return new CertificateEntity()
                .setId(1)
                .setName("Test name")
                .setDescription("Test description")
                .setPrice(50.0)
                .setDuration(30)
                .setCreateDate(LocalDateTime.parse("2019-01-26T10:00:00"))
                .setLastUpdateDate(LocalDateTime.parse("2019-01-26T10:00:00"))
                .setTags(TagServiceTestUtils.getTags().subList(0, 4));
    }

    public static CertificateNewDTO getUpdateNewCertificateDTO() {
        return new CertificateNewDTO()
                .setId(1)
                .setName("Test name")
                .setDescription("Test description")
                .setPrice("50.00")
                .setDuration(30)
                .setCreateDate("2019-01-26T10:00:00")
                .setLastUpdateDate(null);
    }
}