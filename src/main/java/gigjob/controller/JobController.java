package gigjob.controller;

import gigjob.common.util.JobSpecificationBuilder;
import gigjob.entity.Job;
import gigjob.model.domain.JobSearchRequest;
import gigjob.model.domain.SearchCriteria;
import gigjob.model.request.JobRequest;
import gigjob.model.response.JobDetailResponse;
import gigjob.model.response.JobResponse;
import gigjob.repository.specification.JobSpecification;
import gigjob.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    private final ModelMapper modelMapper;

    @GetMapping("/v1/job")
    public ResponseEntity<List<JobDetailResponse>> getJobList() {
        List<JobDetailResponse> jobResponseList = jobService.getJob();
        return ResponseEntity.status(HttpStatus.OK).body(jobResponseList);
    }

    @PostMapping("/v1/job/search")
    @Operation(description = """
            operation: "cn" -> CONTAINS;
                         "nc" -> DOES_NOT_CONTAIN;
                         "eq" -> EQUAL;
                         "ne" -> NOT_EQUAL;
                         "bw" -> BEGINS_WITH;
                         "bn" -> DOES_NOT_BEGIN_WITH;
                         "ew" -> ENDS_WITH;
                         "en" -> DOES_NOT_END_WITH;
                         "nu" -> NULL;
                         "nn" -> NOT_NULL;
                         "gt" -> GREATER_THAN;
                         "ge" -> GREATER_THAN_EQUAL;
                         "lt" -> LESS_THAN;
                         "le" -> LESS_THAN_EQUAL;
                        default -> ALL;
             dataOption: "AND" is "ALL", "OR" is "ANY\"""")
    public ResponseEntity<List<JobDetailResponse>> getJobListFilter(
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "") String searchValue,
            @RequestBody JobSearchRequest jobSearchRequest
    ) {
        JobSpecificationBuilder builder = new JobSpecificationBuilder();
        List<SearchCriteria> criteriaList = jobSearchRequest.getSearchCriteriaList();
        if (criteriaList != null) {
            criteriaList.forEach(x -> {
                x.setDataOption(jobSearchRequest.getDataOption());
                builder.with(x);
            });
        }
        // get from direction
        Pageable page;
        if (jobSearchRequest.getSortCriteria().getDirection().equalsIgnoreCase("asc")) {
            page = PageRequest.of(pageIndex, pageSize, Sort.by("id").ascending());
        } else {
            page = PageRequest.of(pageIndex, pageSize, Sort.by("id").descending());
        }

        Page<Job> jobs = jobService.findBySearchCriteria(
                builder.build().and(JobSpecification.getJobsByTitleSpec(searchValue)),
                page);
        List<JobDetailResponse> jobDetailResponses = jobs.stream()
                .map(job -> modelMapper.map(job, JobDetailResponse.class))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(jobDetailResponses);
    }

    @GetMapping("/v1/job/{id}")
    public ResponseEntity<JobDetailResponse> getJobById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.getJobById(id));
    }

    @PostMapping("/v1/job")
    public ResponseEntity<JobResponse> createJob(@RequestBody JobRequest jobRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.addJob(jobRequest));
    }

    @GetMapping("/v1/job/shop/{id}")
    public ResponseEntity<Object> getJobsByShopId(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                jobService.findJobsByShopId(UUID.fromString(id))
        );
    }

    @PatchMapping("/v1/job")
    public ResponseEntity<JobResponse> updateJob(@RequestBody JobRequest jobRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.updateJob(jobRequest));
    }

    @DeleteMapping("/v1/job")
    public ResponseEntity<String> deleteJob(@RequestBody Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.deleteJob(id));
    }
}
