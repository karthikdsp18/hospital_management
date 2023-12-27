package MainModule;

import dao.HospitalServiceImpl;
import dao.IHospitalService;

import entity.Appointment;
//import myexception.PatientNumberNotFoundException;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static final IHospitalService hospitalService = new HospitalServiceImpl();
	public static void main(String[] args) throws SQLException, ParseException {
			runHospitalMenu();
	}
	
    private static void runHospitalMenu() throws SQLException, ParseException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Hospital Management System Menu");
            System.out.println("1. View Appointment by ID");
            System.out.println("2. View Appointments for Patient");
            System.out.println("3. View Appointments for Doctor");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Update Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    viewAppointmentById();
                    break;
                case 2:
                    viewAppointmentsForPatient();
                    break;
                case 3:
                    viewAppointmentsForDoctor();
                    break;
                case 4:
                    scheduleAppointment();
                    break;
                case 5:
                    updateAppointment();
                    break;
                case 6:
                    cancelAppointment();
                    break;
                case 0:
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
    
    private static void viewAppointmentById() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Appointment ID: ");
        int appointmentId = scanner.nextInt();
        Appointment appointment = hospitalService.getAppointmentById(appointmentId);
		if (appointment != null) {
		    System.out.println(appointment);
		} else {
		    System.out.println("Appointment not found.");
		}
    }

    private static void viewAppointmentsForPatient() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        List<Appointment> appointments = hospitalService.getAppointmentForPatient(patientId);
		if (!appointments.isEmpty()) {
		    for (Appointment appointment : appointments) {
		        System.out.println(appointment);
		    }
		} else {
		    System.out.println("No appointments found for the patient.");
		}
    }

    private static void viewAppointmentsForDoctor() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();
        List<Appointment> appointments = hospitalService.getAppointmentForDoctor(doctorId);
		if (!appointments.isEmpty()) {
		    for (Appointment appointment : appointments) {
		        System.out.println(appointment);
		    }
		} else {
		    System.out.println("No appointments found for the doctor.");
		}
    }

    private static void scheduleAppointment() throws SQLException,ParseException {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter Patient ID: ");
            int patientId = scanner.nextInt();
            System.out.print("Enter Doctor ID: ");
            int doctorId = scanner.nextInt();
            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            String dateString = scanner.next();
            Date appointmentDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            System.out.print("Enter Appointment Description: ");
            String description = scanner.nextLine();

            Appointment appointment = new Appointment(0, patientId, doctorId, appointmentDate, description);
            if (hospitalService.scheduleAppointment(appointment)) {
                System.out.println("Appointment scheduled successfully.");
            } else {
                System.out.println("Failed to schedule the appointment.");
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
    }

    private static void updateAppointment() throws SQLException,ParseException {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter Appointment ID: ");
            int appointmentId = scanner.nextInt();

            // Check if the appointment exists
            Appointment existingAppointment = hospitalService.getAppointmentById(appointmentId);
            if (existingAppointment == null) {
                System.out.println("Appointment not found.");
                return;
            }

            // Display the existing appointment details
            System.out.println("Existing Appointment Details:");
            System.out.println(existingAppointment);

            // Gather updated details
            System.out.print("Enter new Appointment Date (YYYY-MM-DD): ");
            String dateString = scanner.next();
            Date newAppointmentDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            System.out.print("Enter new Appointment Description: ");
            String newDescription = scanner.nextLine();

            // Create an updated appointment object
            Appointment updatedAppointment = new Appointment(
                    existingAppointment.getAppointmentId(),
                    existingAppointment.getPatientId(),
                    existingAppointment.getDoctorId(),
                    newAppointmentDate,
                    newDescription
            );

            // Update the appointment
            if (hospitalService.updateAppointment(updatedAppointment)) {
                System.out.println("Appointment updated successfully.");
            } else {
                System.out.println("Failed to update the appointment.");
            }
        } catch(Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private static void cancelAppointment() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Appointment ID: ");
        int appointmentId = scanner.nextInt();
        if (hospitalService.cancelAppointment(appointmentId)) {
		    System.out.println("Appointment canceled successfully.");
		} else {
		    System.out.println("Failed to cancel the appointment.");
		}
    }

}
