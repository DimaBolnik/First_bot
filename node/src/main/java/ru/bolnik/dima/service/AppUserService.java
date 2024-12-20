package ru.bolnik.dima.service;

import ru.bolnik.dima.entity.AppUser;

public interface AppUserService {

    String registerUser(AppUser appUser);

    String setEmail(AppUser appUser, String email);
}
