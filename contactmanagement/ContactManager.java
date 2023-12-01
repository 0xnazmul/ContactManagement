package contactmanagement;
/**
 *
 * @author nazmul
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactManager {
    private ArrayList<Contact> contacts;
    private Scanner scanner;
    private String fileName;

    public ContactManager(String fileName) {
        this.fileName = fileName;
        contacts = loadContactsFromFile();
        scanner = new Scanner(System.in);
    }

    private ArrayList<Contact> loadContactsFromFile() {
        ArrayList<Contact> loadedContacts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    loadedContacts.add(new Contact(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading contacts from file: " + e.getMessage());
        }
        return loadedContacts;
    }

    private void saveContactsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Contact contact : contacts) {
                writer.write(contact.getName() + "," + contact.getPhoneNumber());
                writer.newLine();
            }
            System.out.println("Contacts saved to " + fileName + " successfully!");
        } catch (IOException e) {
            System.err.println("Error saving contacts to file: " + e.getMessage());
        }
    }

    public void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        Contact contact = new Contact(name, phoneNumber);
        contacts.add(contact);

        saveContactsToFile();

        System.out.println("Contact added successfully!");
    }

    public void listContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            System.out.println("Contacts:");
            for (Contact contact : contacts) {
                System.out.println("Name: " + contact.getName() + ", Phone: " + contact.getPhoneNumber());
            }
        }
    }

    public static void main(String[] args) {
        String fileName = "contacts.txt";
        ContactManager contactManager = new ContactManager(fileName);
        int choice;

        do {
            System.out.println("\n1. Add Contact");
            System.out.println("2. List Contacts");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = contactManager.scanner.nextInt();
            contactManager.scanner.nextLine(); 

            switch (choice) {
                case 1:
                    contactManager.addContact();
                    break;
                case 2:
                    contactManager.listContacts();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 3);
    }
}