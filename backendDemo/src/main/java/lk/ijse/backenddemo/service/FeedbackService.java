package lk.ijse.backenddemo.service;

import lk.ijse.backenddemo.dto.FeedbackDTO;
import lk.ijse.backenddemo.dto.UserDTO;
import lk.ijse.backenddemo.entity.Feedback;
import lk.ijse.backenddemo.entity.Services;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    Optional<Services> getService(String serviceId);

    FeedbackDTO saveFeedback(FeedbackDTO feedbackDTO, Services services1);

    List<FeedbackDTO> getFeedbackByServiceId(String serviceId);

    UserDTO getUserByEmail(String email);

    List<Feedback> getAllFeedbacks();


}
