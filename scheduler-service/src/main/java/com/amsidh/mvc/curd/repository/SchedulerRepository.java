package com.amsidh.mvc.curd.repository;

import com.amsidh.mvc.curd.entity.SchedulerJobInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerRepository extends JpaRepository<SchedulerJobInfo, String> {
    SchedulerJobInfo findByJobName(String jobName);
}
