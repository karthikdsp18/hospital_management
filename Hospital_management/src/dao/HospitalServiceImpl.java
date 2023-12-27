package dao;

import entity.Appointment;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalServiceImpl implements IHospitalService {
	
	private Connection connection;
	
	public HospitalServiceImpl(){
		this.connection = DBConnection.getConnection();
	}
	
	@Override
	public Appointment getAppointmentById(int appointmentId) {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM appointments WHERE appointmentId = ?")) {

	            preparedStatement.setInt(1, appointmentId);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	                return extractAppointmentFromResultSet(resultSet);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace(); // Handle the exception appropriately
	        }
	        return null;
	}
	@Override
    public List<Appointment> getAppointmentForPatient(int patientId) {
        List<Appointment> appointments = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM appointments WHERE patientId = ?")) {

            preparedStatement.setInt(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                appointments.add(extractAppointmentFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentForDoctor(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM appointments WHERE doctorId = ?")) {

            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                appointments.add(extractAppointmentFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return appointments;
    }

    @Override
    public boolean scheduleAppointment(Appointment appointment) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO appointments (patientId, doctorId, appointmentDate, description) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setInt(1, appointment.getPatientId());
            preparedStatement.setInt(2, appointment.getDoctorId());
            preparedStatement.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            preparedStatement.setString(4, appointment.getDescription());

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return false;
        }
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE appointments SET patientId = ?, doctorId = ?, appointmentDate = ?, description = ? WHERE appointmentId = ?")) {

            preparedStatement.setInt(1, appointment.getPatientId());
            preparedStatement.setInt(2, appointment.getDoctorId());
            preparedStatement.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            preparedStatement.setString(4, appointment.getDescription());
            preparedStatement.setInt(5, appointment.getAppointmentId());

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return false;
        }
    }

    @Override
    public boolean cancelAppointment(int appointmentId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM appointments WHERE appointmentId = ?")) {

            preparedStatement.setInt(1, appointmentId);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return false;
        }
    }

    private Appointment extractAppointmentFromResultSet(ResultSet resultSet) throws SQLException {
        // Implement the logic to extract Appointment from ResultSet
        // For example:
        int appointmentId = resultSet.getInt("appointmentId");
        int patientId = resultSet.getInt("patientId");
        int doctorId = resultSet.getInt("doctorId");
        Date appointmentDate = resultSet.getDate("appointmentDate");
        String description = resultSet.getString("description");

        return new Appointment(appointmentId, patientId, doctorId, appointmentDate, description);
    }
	

}
