package com.hardy.JobApp;

import com.hardy.JobApp.model.JobPost;
import com.hardy.JobApp.repo.JobRepo;
import com.hardy.JobApp.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping({"/", "home"})
    public String home(){

        System.out.println("home method is calling");
//        return "Hello World"; this wont work cuz
        /*
        *JobController returns "Hello World", but in a Spring MVC controller (@Controller), the return value is interpreted as a view name, not literal text.

So Spring is looking for a JSP file named Hello World.jsp (which obviously doesn’t exist), causing the 404 “Whitelabel Error Page”.
        */
        return "home";
    }

    @GetMapping("addjob")
    public String addjob(){
        return "addjob";
    }

//    @RequestMapping(value = "handleForm", method = RequestMethod.POST)//in success.jsp post method form action was "handleForm" so
    @PostMapping("handleForm")
    public String handleForm(JobPost jobpost){
        jobService.addJob(jobpost);
        return "success";
    }

    @GetMapping("viewalljobs")
    public String viewjobs(Model m){
        List<JobPost> jobs = jobService.getAllJobs();
        m.addAttribute("jobPosts",jobs);

        return "viewalljobs";
    }

}
