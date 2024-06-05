/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.mbd.dao;

import com.example.mbd.model.User;

public interface UserDao {
    
    User getByLogin(String login);
}
