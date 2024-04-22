package com.jasir.springboot.onlineclassmanagment.domain.course_category.batch;

import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/batch",produces = MediaType.APPLICATION_JSON_VALUE)
public class BatchResource {
    private final BatchService batchService;

    @PostMapping("/add-batch")
    public ResponseEntity<ApplicationResponseDTO> addBatch(@Valid @RequestBody BatchRequestDTO batchRequestDTO){
        return ResponseEntity.ok(batchService.addBatch(batchRequestDTO));
    }

    @PostMapping("/update-batch/{batch-code}")
    public ResponseEntity<ApplicationResponseDTO> updateBatch(@PathVariable(name = "batch-code") String batchCode, @Valid @RequestBody BatchUpdateRequestDTO batchUpdateRequestDTO){
        return ResponseEntity.ok(batchService.updateBatch(batchCode,batchUpdateRequestDTO));
    }

//    @DeleteMapping("/delete-batch/{batch-code}")
//    public ResponseEntity<ApplicationResponseDTO> deleteBatch(@PathVariable(name = "batch-code") String batchCode){
//        return ResponseEntity.ok(batchService.deleteBatch(batchCode));
//    }

    @GetMapping("/all-batches")
    public ResponseEntity<List<BatchWithTeacherDetailsDTO>> getAllBatches(){
        return ResponseEntity.ok(batchService.getAllBatches());
    }

    @GetMapping("/all-batches-by/{name}")
    public ResponseEntity<List<BatchWithTeacherDetailsDTO>>getAllBatchesByName(@PathVariable("name") String name){
        return ResponseEntity.ok(batchService.getAllBatchesByName(name));
    }
}
