package Tools;

/**
 * This enum (Type) is used in the Query object to determine which viewpoint
 * communicates with the server. At this stage of the development we have four
 * types of clients 1) Clinical 2) Receptionist 3) Medical Records Staff 4)
 * Health Service Management
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public enum Viewpoint {
	/**
	 * 1) Clinical
	 * 
	 */
	Clinical,
	/**
	 * 2) Receptionist
	 * 
	 */
	Receptionist,
	/**
	 * 3) Medical Records Staff
	 * 
	 */
	MedicalRecords,
	/**
	 * 4) Health Service Management
	 * 
	 */
	HealthService;
}
