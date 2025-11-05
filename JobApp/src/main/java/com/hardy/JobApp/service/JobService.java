package com.hardy.JobApp.service;

import com.hardy.JobApp.model.JobPost;
import com.hardy.JobApp.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepo jobRepo;

    public void addJob(JobPost job){
        jobRepo.addJobs(job);
    }
    public List<JobPost> getAllJobs(){
        return jobRepo.getAllJobs();
    }
}
