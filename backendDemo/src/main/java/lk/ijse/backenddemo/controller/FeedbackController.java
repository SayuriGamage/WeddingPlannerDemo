package lk.ijse.backenddemo.controller;

import lk.ijse.backenddemo.dto.FeedbackDTO;
import lk.ijse.backenddemo.dto.UserDTO;
import lk.ijse.backenddemo.entity.Services;
import lk.ijse.backenddemo.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/feedback")
@CrossOrigin
public class FeedbackController  {

    @Autowired
  private   FeedbackService feedbackService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('bride')")
    public ResponseEntity<FeedbackDTO> saveReviewWithImage(
            @RequestParam("serviceId") String serviceId,
            @RequestParam("title") String title,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("review") String review,
            @RequestParam("rating") int rating

    ) {

        FeedbackDTO feedbackDTO=new FeedbackDTO();
        Optional<Services> services= feedbackService.getService(serviceId);
        Services services1=services.get();
        System.out.println("feed back eke service eka enawa "+services.get().getEmail());

        feedbackDTO.setTitle(title);
        feedbackDTO.setName(name);
        feedbackDTO.setEmail(email);
        feedbackDTO.setReview(review);
        feedbackDTO.setRating(rating);

        FeedbackDTO feedbackDTO1=feedbackService.saveFeedback(feedbackDTO,services1);
       return ResponseEntity.ok(feedbackDTO1);
    }


    @GetMapping("/getbyId/{serviceId}")
    public ResponseEntity<List<FeedbackDTO>> getReviewsByServiceId(@PathVariable String serviceId) {
        List<FeedbackDTO> feedbacks = feedbackService.getFeedbackByServiceId(serviceId);
        return ResponseEntity.ok(feedbacks);
    }


    @GetMapping("/search/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO userDTO = feedbackService.getUserByEmail(email);
        return ResponseEntity.ok(userDTO);
    }

}
