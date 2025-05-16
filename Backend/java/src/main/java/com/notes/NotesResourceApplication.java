package com.notes;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
public class NotesResourceApplication {
   public static void main(String[] args) {
      SpringApplication.run(NotesResourceApplication.class, args);
   }
}
