package lk.ijse.backenddemo.service;

import lk.ijse.backenddemo.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface BookmarkService {
    void saveBookmark(Optional<User> user, String serviceId);
}
