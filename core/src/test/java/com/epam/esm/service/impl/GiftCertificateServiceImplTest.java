package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.constant.GiftCertificateSearchParam;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import com.epam.esm.service.interf.TagService;
import com.epam.esm.service.utils.GiftCertificateUtil;
import com.epam.esm.service.utils.ServiceUtil;
import com.epam.esm.service.validator.GiftCertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    GiftCertificateRepository repository;

    @Mock
    TagService tagService;

    @InjectMocks
    GiftCertificateServiceImpl service;

    GiftCertificate gift1;
    GiftCertificate gift2;
    List<GiftCertificate> entityList;
    List<Tag> tagListGift1;
    List<Tag> tagListGift2;
    List<GiftCertificateDto> dtoList;

    @BeforeEach
    void init() {
        gift1 = new GiftCertificate(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), null);
        gift2 = new GiftCertificate(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), null);

        tagListGift1 = new ArrayList<>();
        tagListGift1.add(new Tag(1, "Sport"));
        tagListGift1.add(new Tag(2, "Water"));
        tagListGift1.add(new Tag(7, "Health"));

        tagListGift2 = new ArrayList<>();
        tagListGift2.add(new Tag(5, "Auto"));

        entityList = new ArrayList<>();
        entityList.add(new GiftCertificate(1, "Water skiing", "Water skiing on Minsk sea", 20.0, 50,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift1.get(1)));
        entityList.add(new GiftCertificate(1, "Water skiing", "Water skiing on Minsk sea", 20.0, 50,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift1.get(2)));
        entityList.add(new GiftCertificate(1, "Water skiing", "Water skiing on Minsk sea", 20.0, 50,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift1.get(0)));
        entityList.add(new GiftCertificate(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180,
                LocalDateTime.parse("2022-04-27T04:43:55.000"), LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift2.get(0)));

        dtoList = new ArrayList<>();
        dtoList.add(new GiftCertificateDto(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift1));
        dtoList.add(new GiftCertificateDto(2, "Car wash",
                "Complex for cars with washing and body treatment from KlinArt", 100.0, 180, LocalDateTime.parse("2022-04-27T04:43:55.000"),
                LocalDateTime.parse("2022-04-27T04:43:55.000"), tagListGift2));
    }

    @Test
    void readAllGiftCertificates() throws RepositoryException, ValidateException, ServiceException {
        when(repository.readAllGiftCertificates()).thenReturn(entityList).thenThrow(new RepositoryException());
        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class)) {
            util.when(() -> GiftCertificateUtil.giftCertificateEntityListToDtoConverting(entityList))
                    .thenReturn(dtoList);
            List<GiftCertificateDto> actualGiftList = service.readAllGiftCertificates();
            assertEquals(dtoList, actualGiftList);
        }
        assertThrows(ServiceException.class, () -> service.readAllGiftCertificates());
    }

    @Test
    void testReadGiftCertificateStr() throws RepositoryException, ValidateException, ServiceException {
        when(repository.readGiftCertificate(anyInt())).thenReturn(entityList).thenReturn(new ArrayList<>())
                .thenThrow(new RepositoryException());

        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class);
             MockedStatic<ServiceUtil> servUtil = Mockito.mockStatic(ServiceUtil.class)) {
            servUtil.when(() -> ServiceUtil.parseInt(anyString())).thenReturn(1);
            util.when(() -> GiftCertificateUtil.giftCertificateEntityListToDtoConverting(entityList))
                    .thenReturn(dtoList);
            GiftCertificateDto actualDto = service.readGiftCertificate("1");
            assertEquals(dtoList.get(0), actualDto);
        }
        assertThrows(ServiceException.class, () -> service.readGiftCertificate("2"));
        assertThrows(ServiceException.class, () -> service.readGiftCertificate("2"));
    }

    @Test
    void testReadGiftCertificateInt() throws RepositoryException, ValidateException, ServiceException {
        when(repository.readGiftCertificate(anyInt())).thenReturn(entityList).thenReturn(new ArrayList<>())
                .thenThrow(new RepositoryException());

        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class);
             MockedStatic<GiftCertificateValidator> validator = Mockito.mockStatic(GiftCertificateValidator.class)) {
            validator.when(() -> GiftCertificateValidator.idValidation(1)).thenReturn(true);
            util.when(() -> GiftCertificateUtil.giftCertificateEntityListToDtoConverting(entityList))
                    .thenReturn(dtoList);
            GiftCertificateDto actualDto = service.readGiftCertificate(1);
            assertEquals(dtoList.get(0), actualDto);
        }
        assertThrows(ServiceException.class, () -> service.readGiftCertificate(2));
        assertThrows(ServiceException.class, () -> service.readGiftCertificate(2));
    }

    @Test
    void testReadGiftCertificateIntWrongId(){

        try (MockedStatic<GiftCertificateValidator> validator = Mockito.mockStatic(GiftCertificateValidator.class)) {
            validator.when(() -> GiftCertificateValidator.idValidation(-1)).thenReturn(false);

            assertThrows(ValidateException.class, () -> service.readGiftCertificate(-1));
        }
    }

    @Test
    void testReadGiftCertificateThrowValidateException(){
        try (MockedStatic<ServiceUtil> servUtil = Mockito.mockStatic(ServiceUtil.class)) {
            servUtil.when(() -> ServiceUtil.parseInt(anyString())).thenThrow(new ValidateException());

            assertThrows(ValidateException.class, () -> service.readGiftCertificate("abc"));
        }
    }

    @Test
    void createGiftCertificate() throws RepositoryException, ValidateException, ServiceException {
        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class);
             MockedStatic<GiftCertificateValidator> validator = Mockito.mockStatic(GiftCertificateValidator.class)) {

            util.when(() -> GiftCertificateUtil.giftCertificateDtoToEntityTransfer(dtoList.get(0)))
                    .thenReturn(gift1);

            service.createGiftCertificate(dtoList.get(0));

            verify(repository, times(1)).createGiftCertificate(gift1, tagListGift1);
        }
    }

    @Test
    void createGiftCertificateNoDates() throws RepositoryException, ValidateException, ServiceException {
        GiftCertificateDto dto = new GiftCertificateDto(1, "Water skiing",
                "Water skiing on Minsk sea", 20.0, 50, null,
                null, tagListGift1);

        LocalDateTime timeNow = LocalDateTime.now();
        GiftCertificate entity;
        GiftCertificateUtil util = spy(new GiftCertificateUtil());

        service.createGiftCertificate(dto);
        entity = util.giftCertificateDtoToEntityTransfer(dto);
        assertEquals(timeNow.getMonthValue(), entity.getCreateDate().getMonthValue());
        assertEquals(timeNow.getMonthValue(), entity.getLastUpdateDate().getMonthValue());

        verify(repository, times(1)).createGiftCertificate(entity, tagListGift1);
    }


    @Test
    void updateGiftCertificateAllFieldsNotNull() throws ValidateException, ServiceException, RepositoryException {
        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class);
             MockedStatic<GiftCertificateValidator> validator = Mockito.mockStatic(GiftCertificateValidator.class)) {

            validator.when(() -> GiftCertificateValidator.allNotNullFieldValidation(dtoList.get(0)))
                    .thenReturn(true);

            util.when(() -> GiftCertificateUtil.giftCertificateDtoToEntityTransfer(dtoList.get(0)))
                    .thenReturn(gift1);

            service.updateGiftCertificate(dtoList.get(0));

            verify(repository, times(1)).updateGiftCertificate(gift1, tagListGift1);
        }
    }

    @Test
    void updateGiftCertificateHaveNullFields() throws ValidateException, ServiceException, RepositoryException {
//        try (MockedStatic<GiftCertificateUtil> util = Mockito.mockStatic(GiftCertificateUtil.class);
//             MockedStatic<GiftCertificateValidator> validator = Mockito.mockStatic(GiftCertificateValidator.class)) {
//
//            validator.when(() -> GiftCertificateValidator.allNotNullFieldValidation(dtoList.get(0)))
//                    .thenReturn(false);
//
//            validator.when(() -> GiftCertificateValidator.idValidation(1))
//                    .thenReturn(false);
//
//            when(service.readGiftCertificate(1)).thenReturn(dtoList.get(0));
//
//            util.when(() -> GiftCertificateUtil.giftCertificateDtoToEntityTransfer(dtoList.get(0)))
//                    .thenReturn(gift1);
//
//            for (Tag tag : tagListGift1) {
//                when(tagService.getTagIdOrCreateNewTag(tag)).thenReturn(tag.getId());
//            }
//
//            service.updateGiftCertificate(dtoList.get(0));
//
//            verify(repository, times(1)).updateGiftCertificate(gift1, tagListGift1);
//        }
    }

    @Test
    void deleteGiftCertificate() throws RepositoryException, ValidateException, ServiceException {
        try (MockedStatic<ServiceUtil> util = Mockito.mockStatic(ServiceUtil.class)){
            util.when(() -> ServiceUtil.parseInt("1")).thenReturn(1);
            service.deleteGiftCertificate("1");
            verify(repository, times(1)).deleteGiftCertificate(1);
        }
    }

    @Test
    void findGiftCertificatesAllOk() throws RepositoryException, ValidateException, ServiceException {
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put(GiftCertificateSearchParam.SEARCH_TAG_NAME, "tag");
        criteriaMap.put(GiftCertificateSearchParam.SEARCH_NAME, "name");
        criteriaMap.put(GiftCertificateSearchParam.SEARCH_DESCRIPTION, "description");
        String sorting = "name_desc";

        when(repository.findGiftCertificate(criteriaMap, sorting)).thenReturn(entityList);
        service.findGiftCertificates(criteriaMap, sorting);
        verify(repository, times(1)).findGiftCertificate(criteriaMap, sorting);
    }

    @Test
    void findGiftCertificatesIncorrectParam() throws RepositoryException, ValidateException, ServiceException {
        Map<String, String> criteriaMap = new HashMap<>();
        criteriaMap.put(GiftCertificateSearchParam.SEARCH_TAG_NAME, "tag");
        criteriaMap.put(GiftCertificateSearchParam.SEARCH_NAME, "name");
        criteriaMap.put("qqeqe", "description");

        try (MockedStatic<GiftCertificateValidator> validator = Mockito.mockStatic(GiftCertificateValidator.class)) {
            validator.when(() -> GiftCertificateValidator.giftCertificateCriteriaValidation(criteriaMap, null))
                    .thenThrow(ValidateException.class);
        }

        verify(repository, never()).findGiftCertificate(criteriaMap, null);
        assertThrows(ValidateException.class, () -> service.findGiftCertificates(criteriaMap, null));
    }
}