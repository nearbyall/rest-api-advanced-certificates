package com.epam.esm.service;

import com.epam.esm.persistence.repository.CertificateRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.repository.entity.CertificateEntity;
import com.epam.esm.persistence.repository.entity.TagEntity;
import com.epam.esm.persistence.repository.sort.SortColumn;
import com.epam.esm.persistence.repository.sort.SortOrder;
import com.epam.esm.service.dto.ObjectListDTO;
import com.epam.esm.service.dto.PageDTO;
import com.epam.esm.service.dto.certificate.CertificateDTO;
import com.epam.esm.service.dto.certificate.CertificateNewDTO;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.service.mapper.СertificateMapper;
import com.epam.esm.service.mapper.TagMapper;
import com.epam.esm.service.pagination.Paginator;
import com.epam.esm.service.utils.CertificateServiceTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.IntStream;

import static com.epam.esm.service.utils.CertificateServiceTestUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
/*
@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {

    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private TagRepository tagRepository;
    @Spy
    private СertificateMapper certificateMapper;
    @Spy
    private TagMapper tagMapper;
    @Spy
    private Paginator paginator;

    private CertificateService certificateService;

    private final static List<CertificateDTO> testGcDTOs = getCertificatesDTOs();
    private final static List<CertificateEntity> testGcs = getCertificates();
    private final static CertificateNewDTO testGcCreationNewDTO = getCertificateCreationTestNewDTO();
    private final static CertificateEntity testGcCreation = getCertificateCreationTest();

    @BeforeEach
    public void setUp() {
        certificateService = new CertificateServiceImpl
                (certificateRepository, tagRepository, certificateMapper, tagMapper, paginator);
    }

    @Test
    public void testGetAll_shouldDelegateGettingToRepositoryIfCertificatesExist() {
        PageDTO expectedPage = new PageDTO().setSize(3).setTotalElements(3).setTotalPages(1).setNumber(1);
        when(certificateRepository.getAll(3, 0)).thenReturn(testGcs);
        when(certificateRepository.getCount()).thenReturn(3);

        ObjectListDTO<CertificateDTO> certificates = certificateService.getAll(1, 3);

        verify(certificateRepository).getAll(3, 0);
        verify(certificateMapper, times(3)).toDto(any(CertificateEntity.class));
        IntStream.range(0, 3).forEach(i -> verify(certificateMapper).toDto(testGcs.get(i)));
        verify(paginator).getPage(3, 3, 1);

        verifyNoMoreInteractions(certificateRepository);
        verifyNoMoreInteractions(tagRepository);
        verifyNoMoreInteractions(certificateMapper);

        assertThat(certificates.getObjects(), is(equalTo(testGcDTOs)));
        assertThat(certificates.getPage(), is(equalTo(expectedPage)));
    }

    @Test
    public void testGetById_shouldDelegateGettingToRepositoryIfCertificateFoundById() {
        int id = 1;
        CertificateDTO expectedCertificate = testGcDTOs.get(0);

        when(certificateRepository.getById(id)).thenReturn(Optional.of(testGcs.get(0)));

        Optional<CertificateDTO> certificate = certificateService.getById(id);

        verify(certificateRepository).getById(id);
        verify(certificateMapper).toDto(testGcs.get(0));

        verifyNoMoreInteractions(certificateRepository);
        verifyNoMoreInteractions(certificateMapper);

        assertTrue(certificate.isPresent());
        assertThat(certificate.get().getName(), is("Game Store"));
        assertThat(certificate.get(), is(equalTo(expectedCertificate)));
    }

    @Test
    public void testGetByName_shouldDelegateGettingToRepositoryIfCertificateFoundByName() {
        String name = "Game Store";
        PageDTO expectedPage = new PageDTO().setSize(5).setTotalElements(1).setTotalPages(1).setNumber(1);
        CertificateDTO expectedCertificate = testGcDTOs.get(0);

        List<CertificateEntity> certificates = new ArrayList<>();
        certificates.add(testGcs.get(0));

        when(certificateRepository.getByName(name, 5, 0)).thenReturn(certificates);
        when(certificateRepository.getByNameCount(name)).thenReturn(1);

        ObjectListDTO<CertificateDTO> certificate = certificateService.getByName(name, 1, 5);

        verify(certificateRepository).getByName(name, 5, 0);
        verify(certificateMapper).toDto(testGcs.get(0));
        verify(paginator).getPage(5, 1, 1);

        verifyNoMoreInteractions(certificateRepository);
        verifyNoMoreInteractions(certificateMapper);

        assertThat(certificate.getObjects().size(), is(1));
        assertThat(certificate.getObjects().get(0), is(equalTo(expectedCertificate)));
        assertThat(certificate.getPage(), is(equalTo(expectedPage)));
    }

    @Test
    public void testGetAllSorted_shouldDelegateGettingToRepositoryIfCertificatesExist() {
        SortColumn sort = SortColumn.NAME;
        SortOrder order = SortOrder.ASC;
        PageDTO expectedPage = new PageDTO().setSize(3).setTotalElements(3).setTotalPages(1).setNumber(1);

        List<CertificateEntity> sortedCertificates = new ArrayList<>();
        sortedCertificates.add(testGcs.get(2));
        sortedCertificates.add(testGcs.get(0));
        sortedCertificates.add(testGcs.get(1));

        when(certificateRepository.getAllSorted(sort, order, 3, 0))
                .thenReturn(sortedCertificates);
        when(certificateRepository.getCount()).thenReturn(3);

        ObjectListDTO<CertificateDTO> certificates = certificateService
                .getAllSorted(sort, order, 1, 3);

        verify(certificateRepository).getAllSorted(sort, order, 3, 0);
        IntStream.range(0, 3).forEach(i -> verify(certificateMapper).toDto(sortedCertificates.get(i)));
        verify(paginator).getPage(3, 3, 1);

        verifyNoMoreInteractions(certificateRepository);
        verifyNoMoreInteractions(certificateMapper);

        assertThat(certificates.getObjects().get(0), is(equalTo(testGcDTOs.get(2))));
        assertThat(certificates.getObjects().get(1), is(equalTo(testGcDTOs.get(0))));
        assertThat(certificates.getObjects().get(2), is(equalTo(testGcDTOs.get(1))));
        assertThat(certificates.getPage(), is(equalTo(expectedPage)));
    }

    @Test
    public void testGetBySingleTagName_shouldDelegateGettingToRepositoryIfCertificateFoundByTagName() {
        List<String> tags = Collections.singletonList("clothes");
        PageDTO expectedPage = new PageDTO().setSize(3).setTotalElements(1).setTotalPages(1).setNumber(1);
        CertificateDTO expectedCertificate = testGcDTOs.get(1);

        List<CertificateEntity> certificatesList = new ArrayList<>();
        certificatesList.add(testGcs.get(1));

        when(certificateRepository.getByTagName(tags.get(0), 3, 0)).thenReturn(certificatesList);
        when(certificateRepository.getByTagCount(tags.get(0))).thenReturn(1);

        ObjectListDTO<CertificateDTO> certificates = certificateService.getByTagNames(tags, 1, 3);

        verify(certificateRepository).getByTagName(tags.get(0), 3, 0);
        verify(certificateMapper).toDto(certificatesList.get(0));
        verify(paginator).getPage(3, 1, 1);

        verifyNoMoreInteractions(certificateRepository);
        verifyNoMoreInteractions(certificateMapper);

        assertThat(certificates.getObjects().size(), is(1));
        assertThat(certificates.getObjects().get(0), is(equalTo(expectedCertificate)));
        assertThat(certificates.getPage(), is(equalTo(expectedPage)));
    }

    @Test
    public void testCreate_shouldDelegateCreationToRepositoryIfCreationIsSuccessful() {
        int createdId = 4;
        List<TagEntity> testTags = Arrays.asList(
                new TagEntity().setId(1).setName("game"),
                new TagEntity().setName("test")
        );

        CertificateDTO expectedCertificate = CertificateServiceTestUtils.getCertificateCreationTestDTO();
        CertificateEntity createCertificate = CertificateServiceTestUtils.getCertificateCreationTest()
                .setCreateDate(null).setLastUpdateDate(null).setTags(null);

        when(certificateRepository.create(createCertificate.setId(0))).thenReturn(Optional.of(createdId));
        when(tagRepository.getByName(testTags.get(0).getName())).thenReturn(Optional.of(testTags.get(0)));
        when(tagRepository.getByName((testTags.get(1).getName()))).thenReturn(Optional.empty())
                .thenReturn(Optional.of(testTags.get(1).setId(13)));
        when(certificateRepository.getById(createCertificate)).thenReturn(Optional.of(testGcCreation));

        Optional<CertificateDTO> actualCertificate = certificateService.create(testGcCreationNewDTO);

        verify(tagRepository).create(testTags.get(1).setId(0));
        verify(tagRepository).addCertificateTag(createdId, 1);
        verify(tagRepository).addCertificateTag(createdId, 13);
        verify(certificateMapper).toCertificateEntity(testGcCreationNewDTO);
        verify(certificateMapper).toDto(testGcCreation);
        verify(certificateRepository).create(createCertificate);
        verify(certificateRepository).getById(createCertificate);
        verify(tagRepository, times(4)).getByName(anyString());
        verify(tagRepository, times(2)).addCertificateTag(anyInt(), anyInt());

        verifyNoMoreInteractions(certificateRepository);
        verifyNoMoreInteractions(tagRepository);
        verifyNoMoreInteractions(certificateMapper);

        assertTrue(actualCertificate.isPresent());
        assertThat(actualCertificate.get(), is(equalTo(expectedCertificate)));
    }

    @Test
    public void testDelete_shouldDelegateDeletionToRepositoryIfCertificateFoundById() {
        int id = 1;

        CertificateDTO expectedCertificate = testGcDTOs.get(0);

        when(certificateRepository.getById(id)).thenReturn(Optional.of(testGcs.get(0)));
        when(certificateRepository.delete(id)).thenReturn(1);

        Optional<CertificateDTO> certificate = certificateService.delete(id);

        verify(certificateRepository).getById(id);
        verify(certificateRepository).delete(id);
        verify(certificateMapper).toDto(testGcs.get(0));

        verifyNoMoreInteractions(certificateRepository);
        verifyNoMoreInteractions(certificateMapper);

        assertTrue(certificate.isPresent());
        assertThat(certificate.get(), is(equalTo(expectedCertificate)));
    }

    @Test
    public void testSearchByNameOrDescription_shouldDelegateSearchToRepositoryIfCertificatesFoundBySearch() {
        String search = "store";
        PageDTO expectedPage = new PageDTO().setSize(5).setTotalElements(2).setTotalPages(1).setNumber(1);

        List<CertificateEntity> certificatesList = new ArrayList<>();
        certificatesList.add(testGcs.get(0));
        certificatesList.add(testGcs.get(2));

        when(certificateRepository.searchByNameOrDescription(search, 5, 0))
                .thenReturn(certificatesList);
        when(certificateRepository.getSearchCount(search)).thenReturn(2);

        ObjectListDTO<CertificateDTO> certificates = certificateService
                .searchByNameOrDescription(search, 1, 5);

        verify(certificateMapper).toDto(testGcs.get(0));
        verify(certificateMapper).toDto(testGcs.get(2));
        verify(certificateRepository).searchByNameOrDescription(search, 5, 0);
        verify(paginator).getPage(5, 2, 1);

        verifyNoMoreInteractions(certificateRepository);
        verifyNoMoreInteractions(certificateMapper);

        assertThat(certificates.getObjects().size(), is(2));
        assertThat(certificates.getObjects().get(0), is(equalTo(testGcDTOs.get(0))));
        assertThat(certificates.getObjects().get(1), is(equalTo(testGcDTOs.get(2))));
        assertThat(certificates.getPage(), is(equalTo(expectedPage)));
    }

}
*/