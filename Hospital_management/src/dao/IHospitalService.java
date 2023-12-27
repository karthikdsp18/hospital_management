package dao;

import entity.Appointment;


import java.util.List;

public interface IHospitalService {
	
	Appointment getAppointmentById(int appointmentId);
	
	List<Appointment> getAppointmentForPatient(int patientId);
	
	List<Appointment> getAppointmentForDoctor(int doctorId);
	
	boolean scheduleAppointment(Appointment appointment);
	
	boolean updateAppointment(Appointment appointment);
	
	boolean cancelAppointment(int appointmentId);
}
