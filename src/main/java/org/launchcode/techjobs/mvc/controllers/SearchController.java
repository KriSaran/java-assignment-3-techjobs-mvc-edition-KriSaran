package org.launchcode.techjobs.mvc.controllers;

import org.launchcode.techjobs.mvc.models.Job;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController {
    static HashMap<String, String> columnChoices = new HashMap<>();
    public SearchController() {
        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("positionType", "Position Type");
        columnChoices.put("coreCompetency", "Skill");
    }

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.

   // @PostMapping(value = "results")
    @RequestMapping(value = "/results", method = RequestMethod.POST)

    public String displaySearchResults(Model model, @RequestParam("searchType")  String searchType,
                                       @RequestParam("searchTerm") String searchTerm) {
        ArrayList<Job> jobs = null;
        System.out.println(searchTerm+" ,"+searchType);

        if(!searchTerm.equals("all")&&searchType.equals("all")){
            jobs=JobData.findByValue(searchTerm);
        }else if(searchType.equals("all")&&searchTerm.equals("all")){
            jobs=JobData.findAll();
            model.addAttribute("title","All Jobs");
        }else{
            jobs=JobData.findByColumnAndValue(searchType,searchTerm);
            model.addAttribute("title", "Jobs with " + searchTerm + ": " + searchType);
        }
        model.addAttribute("jobs", jobs);
        model.addAttribute("columns", columnChoices);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("searchType", searchType);

        return "search";
    }

//    @GetMapping(value = "/jobs")
//    public String listJobsByColumnAndValue(Model model, @RequestParam(required = false) String column, @RequestParam(required = false) String value) {
//        ArrayList<Job> jobs;
//        System.out.println(column + value);
//        if (column.equals("all")) {
//            jobs = JobData.findAll();
//            model.addAttribute("title", "All Jobs");
//        } else if(!column.isEmpty()&&!value.isEmpty()) {
//            jobs = JobData.findByColumnAndValue(column, value);
//            System.out.println(jobs.toString());
//            model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
//        }else{
//            jobs=JobData.findByValue(value);
//            model.addAttribute("title", "Jobs with " + ": " + value);
//        }
//        model.addAttribute("jobs", jobs);
//
//        return "search-jobs";
//    }
}

