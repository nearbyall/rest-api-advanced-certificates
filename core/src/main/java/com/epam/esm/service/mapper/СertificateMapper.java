package com.epam.esm.service.mapper;

import com.epam.esm.persistence.repository.entity.CertificateEntity;
import com.epam.esm.service.dto.certificate.CertificateDTO;
import com.epam.esm.service.dto.certificate.CertificateNewDTO;
import com.epam.esm.service.dto.tag.TagDTO;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class СertificateMapper {

    protected static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    protected static final NumberFormat NUMBER_FORMATTER =
            new DecimalFormat("#0.00", DecimalFormatSymbols.getInstance(Locale.US));

    public CertificateDTO toDto(CertificateEntity certificate) {
        return new CertificateDTO()
                .setId(certificate.getId())
                .setName(certificate.getName())
                .setDescription(certificate.getDescription())
                .setPrice(NUMBER_FORMATTER.format(certificate.getPrice()))
                .setDuration(certificate.getDuration())
                .setCreateDate(DATE_FORMAT.format(certificate.getCreateDate()))
                .setLastUpdateDate(DATE_FORMAT.format(certificate.getLastUpdateDate()))
                .setTags(certificate.getTags()
                        .stream()
                        .map(tag -> new TagDTO().setName(tag.getName()))
                        .collect(Collectors.toList()));
    }

    public CertificateDTO toDto(CertificateNewDTO certificateUpdateDTO) {
        return new CertificateDTO()
                .setId(certificateUpdateDTO.getId())
                .setName(certificateUpdateDTO.getName())
                .setDescription(certificateUpdateDTO.getDescription())
                .setPrice(certificateUpdateDTO.getPrice())
                .setDuration(certificateUpdateDTO.getDuration())
                .setCreateDate(certificateUpdateDTO.getCreateDate())
                .setLastUpdateDate(certificateUpdateDTO.getLastUpdateDate())
                .setTags(certificateUpdateDTO.getTags());
    }

    public CertificateNewDTO toNewDto(CertificateEntity certificate) {
        return new CertificateNewDTO()
                .setId(certificate.getId())
                .setName(certificate.getName())
                .setDescription(certificate.getDescription())
                .setPrice(NUMBER_FORMATTER.format(certificate.getPrice()))
                .setDuration(certificate.getDuration())
                .setCreateDate(DATE_FORMAT.format(certificate.getCreateDate()))
                .setLastUpdateDate(DATE_FORMAT.format(certificate.getLastUpdateDate()))
                .setTags(certificate.getTags()
                        .stream()
                        .map(tag -> new TagDTO().setName(tag.getName()))
                        .collect(Collectors.toList()));
    }

    public CertificateEntity toCertificateEntity(CertificateDTO certificateDTO) {
        return new CertificateEntity()
                .setId(certificateDTO.getId())
                .setName(certificateDTO.getName())
                .setDescription(certificateDTO.getDescription())
                .setPrice(Double.parseDouble(certificateDTO.getPrice()))
                .setDuration(certificateDTO.getDuration());
    }

    public CertificateEntity copyCertificateEntity(CertificateEntity certificate) {
        return new CertificateEntity()
                .setId(certificate.getId())
                .setName(certificate.getName())
                .setDescription(certificate.getDescription())
                .setPrice(certificate.getPrice())
                .setDuration(certificate.getDuration())
                .setCreateDate(certificate.getCreateDate())
                .setLastUpdateDate(certificate.getLastUpdateDate())
                .setTags(certificate.getTags());
    }

    public CertificateEntity toCertificateEntity(CertificateNewDTO certificateDTO) {
        return new CertificateEntity()
                .setId(certificateDTO.getId())
                .setName(certificateDTO.getName())
                .setDescription(certificateDTO.getDescription())
                .setPrice(Double.parseDouble(certificateDTO.getPrice()))
                .setDuration(certificateDTO.getDuration());
    }

}
