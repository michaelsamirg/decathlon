package com.kuehne.task.decathlon.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kuehne.task.decathlon.model.DecathlonModel;

@Repository
@Transactional
public interface DecathlonDao extends JpaRepository<DecathlonModel, Long>{

}
