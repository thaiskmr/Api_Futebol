//package org.fut.futebol;
//
//import jakarta.persistence.EntityManager;
//import org.fut.futebol.model.Clube;
//import org.fut.futebol.repository.ClubeRepository;
//import org.fut.futebol.service.ClubeService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.swing.text.html.parser.Entity;
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ClubeServiceTest {
//    @Autowired
//    EntityManager entityManager;
//    //melhor ousar o autowired ao inves do new?
////    public ClubeService clubeService = new ClubeService();
//
//    @Autowired
//    ClubeRepository clubeRepository;
//
//    @Test
//    public void testeCriarClube() {
//        Clube clube = new Clube();
//        clube.setNomeClube("Fortaleza");
//        clube.setSiglaEstadoClube("CE");
//        clube.setDataCriacaoClube(LocalDate.parse("1920-01-01"));
//        clube.setAtivo(true);
//
//
////        assertEquals();
//
//
//    }
//}
