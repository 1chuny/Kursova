/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.mbd.services;
import com.example.mbd.model.User;
/**
 *
 * @author Андрій
 */
public interface UserService {
    User getByLogin(String login);

    boolean checkPassword(User user, String password);
    
    User authenticate(String login, String password);
}
