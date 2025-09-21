/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.gszabi15.konyvtar_kezelo;

import com.gszabi15.konyvtar_kezelo.Controller.Controller;

/**
 *
 * @author INTEC PC
 */
public class Main {

    public static void main(String[] args) {
        Model model = new Model();
        View view = new View();
        
        Controller controller;  
        controller = new Controller(model, view);
        controller.ViewController();
    }
}
