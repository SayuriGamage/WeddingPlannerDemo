package lk.ijse.backenddemo.controller;

import lk.ijse.backenddemo.entity.Bookmark;
import lk.ijse.backenddemo.entity.User;
import lk.ijse.backenddemo.repo.UserRepository;
import lk.ijse.backenddemo.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;



@RestController
@RequestMapping("/api/v1/bookmarks")
@CrossOrigin
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private UserRepository userRepo;
/*

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('bride')")
    public ResponseEntity<String> saveBookmark(
            @RequestPart("serviceId") String serviceId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = authentication.getName();
        System.out.println("bookmark ekata enawa"+loggedInEmail);
        Optional<User> user = userRepo.findByIduser(loggedInEmail);
        System.out.println(user.get().getUid());
        if (user.isEmpty()) return ResponseEntity.badRequest().body("User not found!");

        bookmarkService.saveBookmark(user, serviceId);
        return ResponseEntity.ok("Bookmark saved successfully!");
    }
*/
@PostMapping(value = "/save")
@PreAuthorize("hasAuthority('bride')")
public ResponseEntity<?> saveBookmark(
        @RequestPart("serviceId") String serviceId
) {
    try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = authentication.getName();
        System.out.println("Received bookmark request for email: " + loggedInEmail);

        Optional<User> user = userRepo.findByIduser(loggedInEmail);
        if (user.isEmpty()) {
            System.out.println("User not found for email: " + loggedInEmail);
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "User not found!"
            ));
        }

        System.out.println("Found user ID: " + user.get().getUid());
        bookmarkService.saveBookmark(user, serviceId);

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Bookmark saved successfully!"
        ));
    } catch (Exception e) {
        System.err.println("Error saving bookmark: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "status", "error",
                "message", "Failed to save bookmark: " + e.getMessage()
        ));
    }
}


}
