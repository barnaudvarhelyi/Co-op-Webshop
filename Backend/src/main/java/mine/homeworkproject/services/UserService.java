package mine.homeworkproject.services;

import mine.homeworkproject.models.User;

public interface UserService {
  User findUserByUsername(String username);
}
