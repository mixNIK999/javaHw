package me.nikolyukin.homework5;

import java.sql.DriverManager;
import java.util.Scanner;

/**
 * Консольный телефонный справочник.
 * Он должен уметь хранить имена и номера телефонов,
 * при этом у одного человека может быть несколько телефонов
 * и у одного телефона может быть несколько хозяев.
 */
public class ConsolePhoneBook {
    public static void main(String [] args) {
        try (var phoneBook = new PhoneBook(DriverManager.getConnection("jdbc:sqlite:phoneBook.db"))){
            label:
            while (true) {
                Scanner in = new Scanner(System.in);
                System.out.println("Выбирите опцию:\n"
                    + "    0 - выйти\n"
                    + "    1 - добавить запись (имя и телефон)\n"
                    + "   7 2 - найти телефоны по имени\n"
                    + "    3 - найти имена по телефону\n"
                    + "    4 - удалить заданную пару имя-телефон\n"
                    + "    5 - у указанной пары \"имя-телефон\" поменять имя\n"
                    + "    6 - у указанной пары \"имя-телефон\" поменять телефон\n"
                    + "    7 - распечатать все пары имя-телефон в справочнике");
                int option = in.nextInt();
                switch (option) {
                    case 0:
                        break label;
                    case 1: {
                        System.out.println("Введите имя и номер:");
                        String name = in.next();
                        String number = in.next();
                        phoneBook.add(name, number);
                        break;
                    }
                    case 2: {
                        System.out.println("Введите имя:");
                        String name = in.next();
                        for (String number : phoneBook.findNumbers(name)) {
                            System.out.println(number);
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("Введите номер:");
                        String number = in.next();
                        for (String name : phoneBook.findNames(number)) {
                            System.out.println(name);
                        }
                        break;
                    }
                    case 4: {
                        System.out.println("Введите имя и номер:");
                        String name = in.next();
                        String number = in.next();
                        phoneBook.delete(name, number);
                        break;
                    }
                    case 5: {
                        System.out.println("Введите имя, номер и новое имя:");
                        String name = in.next();
                        String number = in.next();
                        String newName = in.next();
                        phoneBook.changeName(name, number, newName);
                        break;
                    }
                    case 6: {
                        System.out.println("Введите имя, номер и новый телефон:");
                        String name = in.next();
                        String number = in.next();
                        String newNumber = in.next();
                        phoneBook.changeNumber(name, number, newNumber);
                        break;
                    }
                    case 7:
                        for (var pair : phoneBook.getAll()) {
                            System.out
                                .println("(" + pair.getFirst() + "," + pair.getSecond() + ")");
                        }
                        break;
                    default:
                        System.out.println("Неизвестная команда");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
