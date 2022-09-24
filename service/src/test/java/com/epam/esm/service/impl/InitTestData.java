package com.epam.esm.service.impl;

import com.epam.esm.dto.CriteriaDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderItemDto;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import com.epam.esm.repository.interf.OrderForGiftCertificateRepository;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ValidateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
@EnableJpaAuditing
public class InitTestData {
    @Autowired
    OrderForGiftCertificateServiceImpl orderService;
    @Autowired
    private OrderForGiftCertificateRepository orderRepository;
    @Autowired
    private GiftCertificateRepository giftCertificateRepository;
    @PersistenceContext
    EntityManager entityManager;

//
//    @Test
//    @Rollback(value = false)
//    void addThousandOrders() {
//        int i = 1;
//        int upsCounter = 0;
//        OrderItemDto orderItem;
//        GiftCertificateDto giftCertificateDto;
//        List<OrderItemDto> orderItemDtoList;
//
//        while (i <= 10000){
//            try {
//                orderItemDtoList = new ArrayList<>();
//                int userId = ThreadLocalRandom.current().nextInt(1, 1006);
//                int itemQuantity = ThreadLocalRandom.current().nextInt(1, 8);
//                for (int j = 0; j < itemQuantity; j++) {
//                    int giftId = ThreadLocalRandom.current().nextInt(1, 35503);
//                    int quantity = ThreadLocalRandom.current().nextInt(1, 8);
//                    giftCertificateDto = new GiftCertificateDto();
//                    giftCertificateDto.setId(giftId);
//                    orderItem = new OrderItemDto(0, giftCertificateDto, quantity);
//                    orderItemDtoList.add(orderItem);
//                }
//                orderService.createOrderForGiftCertificate(userId, orderItemDtoList);
//                i ++;
//                System.out.println(i);
//            } catch (ServiceException e){
//                upsCounter++;
//            }
//        }
//        System.out.println(i);
//        System.out.println("UPS!!!" + upsCounter);
//    }


//        @Test
//    void put10000Gifts() throws RepositoryException {
//        int maxLinkedTags = 7;
//        Random tagSizeRand = new Random();
//        Random r = new Random();
//        Random randomTag = new Random();
//
//        for (int i = 0; i < 10000; i++) {
//            try {
//                List<TagEntity> tagEntities = linkGiftsAndTags(tagSizeRand.nextInt(maxLinkedTags) + 1, randomTag);
//                giftCertificateRepository.create(new GiftCertificateEntity(0, "name" + i, "description" + i, (double)r.nextInt(600),
//                        r.nextInt(300), LocalDateTime.now(), LocalDateTime.now(), tagEntities));
//            } catch (Exception ignored) {
//            }
//        }
//    }
//
//    private List<TagEntity> linkGiftsAndTags(int size, Random randomTag) throws RepositoryException {
//        int totalSize = 1075;
//        List<TagEntity> tagEntities = new ArrayList<>(size);
//
//        for (int i = 0; i < size; i++) {
//            try {
//                long id = randomTag.nextInt(totalSize);
//                tagEntities.add(tagMySQLRepository.readOne(id));
//            }catch (Exception ignored){
//            }
//        }
//        return tagEntities;
//    }
//
//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    void changePrice() {
//        List<GiftCertificateEntity> all = giftCertificateRepository.readAll();
//        double rangeMin = 1;
//        double rangeMax = 100;
//        Random r = new Random();
////        all.stream().filter(gift -> gift.getPrice() == 0).forEach(gift -> gift.setPrice(bigDecimal.doubleValue()));
//
//        List<GiftCertificateEntity> zeroPrice =  all.stream().filter(gift -> gift.getPrice() == 0).collect(Collectors.toList());
//        zeroPrice.forEach(gift -> {
//            double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
//            BigDecimal bigDecimal = BigDecimal.valueOf(randomValue).setScale(2, RoundingMode.HALF_UP);
//            gift.setPrice(bigDecimal.doubleValue());
//        });
//
//        System.out.println(zeroPrice.size());
//        zeroPrice.forEach(gift -> System.out.println(gift.getId() + " " + gift.getPrice()));
//        zeroPrice.forEach(gift -> giftCertificateRepository.merge(gift));
//
//    }

//    @Test
//    void put10000Gifts() throws RepositoryException {
//        int maxLinkedTags = 7;
//        Random tagSizeRand = new Random();
//        Random r = new Random();
//        Random randomTag = new Random();
//
//        for (int i = 0; i < 10000; i++) {
//            try {
//                List<TagEntity> tagEntities = linkGiftsAndTags(tagSizeRand.nextInt(maxLinkedTags) + 1, randomTag);
//                giftCertificateRepository.create(new GiftCertificateEntity(0, "name" + i, "description" + i, (double)r.nextInt(600),
//                        r.nextInt(300), LocalDateTime.now(), LocalDateTime.now(), tagEntities));
//            } catch (Exception ignored) {
//            }
//        }
//    }
//
//    private List<TagEntity> linkGiftsAndTags(int size, Random randomTag) throws RepositoryException {
//        int totalSize = 1075;
//        List<TagEntity> tagEntities = new ArrayList<>(size);
//
//        for (int i = 0; i < size; i++) {
//            try {
//                long id = randomTag.nextInt(totalSize);
//                tagEntities.add(tagMySQLRepository.readOne(id));
//            }catch (Exception ignored){
//            }
//        }
//        return tagEntities;
//    }
//
//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    void changePrice() {
//        List<GiftCertificateEntity> all = giftCertificateRepository.readAll();
//        double rangeMin = 1;
//        double rangeMax = 100;
//        Random r = new Random();
////        all.stream().filter(gift -> gift.getPrice() == 0).forEach(gift -> gift.setPrice(bigDecimal.doubleValue()));
//
//        List<GiftCertificateEntity> zeroPrice =  all.stream().filter(gift -> gift.getPrice() == 0).collect(Collectors.toList());
//        zeroPrice.forEach(gift -> {
//            double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
//            BigDecimal bigDecimal = BigDecimal.valueOf(randomValue).setScale(2, RoundingMode.HALF_UP);
//            gift.setPrice(bigDecimal.doubleValue());
//        });
//
//        System.out.println(zeroPrice.size());
//        zeroPrice.forEach(gift -> System.out.println(gift.getId() + " " + gift.getPrice()));
//        zeroPrice.forEach(gift -> giftCertificateRepository.merge(gift));
//
//    }
    //    @Test
//    @Rollback(value = false)
//    void put10000Gifts() throws RepositoryException {
//        int maxLinkedTags = 7;
//        Random tagSizeRand = new Random();
//        Random r = new Random();
//        Random randomTag = new Random();
//
//        for (int i = 0; i < 10000; i++) {
//            try {
//                List<TagEntity> tagEntities = linkGiftsAndTags(tagSizeRand.nextInt(maxLinkedTags) + 1, randomTag);
//                giftCertificateRepository.create(new GiftCertificateEntity(0, "name" + i, "description" + i, (double)r.nextInt(600),
//                        r.nextInt(300), LocalDateTime.now(), LocalDateTime.now(), tagEntities));
//            } catch (Exception ignored) {
//            }
//        }
//    }
//
//    private List<TagEntity> linkGiftsAndTags(int size, Random randomTag) throws RepositoryException {
//        int totalSize = 1075;
//        List<TagEntity> tagEntities = new ArrayList<>(size);
//
//        for (int i = 0; i < size; i++) {
//            try {
//                long id = randomTag.nextInt(totalSize);
//                tagEntities.add(tagMySQLRepository.readOne(id));
//            }catch (Exception ignored){
//            }
//        }
//        return tagEntities;
//    }

//    @Test
//        @Rollback(value = false)
//    void changePrice() {
//        List<GiftCertificateEntity> all = giftCertificateRepository.readAll();
//        double rangeMin = 1;
//        double rangeMax = 100;
//        Random r = new Random();
////        all.stream().filter(gift -> gift.getPrice() == 0).forEach(gift -> gift.setPrice(bigDecimal.doubleValue()));
//
//        List<GiftCertificateEntity> zeroPrice =  all.stream().filter(gift -> gift.getPrice() == 0).collect(Collectors.toList());
//        zeroPrice.forEach(gift -> {
//            double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
//            BigDecimal bigDecimal = BigDecimal.valueOf(randomValue).setScale(2, RoundingMode.HALF_UP);
//            gift.setPrice(bigDecimal.doubleValue());
//        });
//
//        System.out.println(zeroPrice.size());
//        zeroPrice.forEach(gift -> System.out.println(gift.getId() + " " + gift.getPrice()));
//        zeroPrice.forEach(gift -> giftCertificateRepository.merge(gift));
//
//    }

//    @Test
//    @Rollback(value = false)
//    void updateLinkedTags() {
//        int i = 5367;
//        int upsCounter = 0;
//        int sovp = 0;
//
//        GiftCertificateEntity gift;
//        List<TagEntity> tags;
//
//        while (i < 35477) {
//            try {
//                gift = giftCertificateRepository.readById(i);
//                int tagsSize = ThreadLocalRandom.current().nextInt(1, 8);
//                tags = new ArrayList<>();
//                for (int j = 0; j < tagsSize; ) {
//                    int tagId = ThreadLocalRandom.current().nextInt(1, 1410);
//                    try {
//                        TagEntity tag = tagRepository.readById(tagId);
//                        if(!tags.contains(tag)){
//                            tags.add(tagRepository.readById(tagId));
//                            j++;
//                        } else {
//                            sovp++;
//                        }
//                    } catch (RepositoryException ignored) {
//                        upsCounter++;
//                    }
//                }
//                gift.setTags(tags);
//                giftCertificateRepository.merge(gift);
//                i++;
//                System.out.println(i);
//            } catch (RepositoryException e) {
//                upsCounter++;
//                i++;
//            }
//        }
//        System.out.println("ups" + upsCounter);
//        System.out.println("sovp" + sovp);
//    }
}
