package app;

import biblioteca.Biblioteca;

public class App {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        MenuController menu = new MenuController(biblioteca);
        menu.executar();
    }
}