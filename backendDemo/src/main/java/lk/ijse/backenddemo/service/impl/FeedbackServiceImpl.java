package lk.ijse.backenddemo.service.impl;


import lk.ijse.backenddemo.dto.FeedbackDTO;
import lk.ijse.backenddemo.dto.UserDTO;
import lk.ijse.backenddemo.entity.Feedback;
import lk.ijse.backenddemo.entity.Services;
import lk.ijse.backenddemo.entity.User;
import lk.ijse.backenddemo.repo.FeedbackRepository;
import lk.ijse.backenddemo.service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Optional<Services> getService(String serviceId) {
        return feedbackRepository.findByServiceId(UUID.fromString(serviceId));
    }

    @Override
    public FeedbackDTO saveFeedback(FeedbackDTO feedbackDTO, Services services1) {
        Feedback feedback=new Feedback();
        feedback.setServices(services1);
        feedback.setTitle(feedbackDTO.getTitle());
        feedback.setName(feedbackDTO.getName());
        feedback.setEmail(feedbackDTO.getEmail());
        feedback.setReview(feedbackDTO.getReview());
        feedback.setRating(feedbackDTO.getRating());
        feedback=feedbackRepository.save(feedback);
        return modelMapper.map(feedback,FeedbackDTO.class);
    }

    @Override
    public List<FeedbackDTO> getFeedbackByServiceId(String serviceId) {

        Optional<Services> services=feedbackRepository.findByServiceId(UUID.fromString(serviceId));

        List<Feedback> feedbacks = feedbackRepository.findFeedbacksByServiceId(services.get());
        return feedbacks.stream()
                .map(f -> modelMapper.map(f, FeedbackDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = feedbackRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks.stream()
                .map(f -> modelMapper.map(f, Feedback.class))
                .collect(Collectors.toList());
    }
}
