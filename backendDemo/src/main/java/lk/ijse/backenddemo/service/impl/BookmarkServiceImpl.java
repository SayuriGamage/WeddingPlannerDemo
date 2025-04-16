package lk.ijse.backenddemo.service.impl;

import lk.ijse.backenddemo.entity.Bookmark;
import lk.ijse.backenddemo.entity.User;
import lk.ijse.backenddemo.repo.BookmarkRepository;
import lk.ijse.backenddemo.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Override
    public void saveBookmark(Optional<User> user, String serviceId) {

        Bookmark bookmark=new Bookmark();

        bookmark.setUser(user.get());
        bookmark.setServiceId(serviceId);

         bookmarkRepository.save(bookmark);

    }
}
